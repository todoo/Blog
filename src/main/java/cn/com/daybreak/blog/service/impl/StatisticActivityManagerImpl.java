package cn.com.daybreak.blog.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.common.tools.DateUtils;
import cn.com.daybreak.blog.dao.StatisticActivityDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.StatisticActivity;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.StatisticActivityManager;

@Service
public class StatisticActivityManagerImpl implements StatisticActivityManager {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StatisticActivityDao statisticActivityDao;
	
	private Logger logger = Logger.getLogger(StatisticActivityManagerImpl.class);
		
	@Override
	@Cacheable(value = "statisticActivityServiceCache", key = "'getActivityChartData' + #urlID + T(cn.com.daybreak.blog.common.tools.DateUtils).dateToStr(#startMonth) + T(cn.com.daybreak.blog.common.tools.DateUtils).dateToStr(#endMonth)")
	public ResultInfo getActivityChartData(String urlID, Date startMonth,
			Date endMonth) {
		logger.info("获取活跃度统计数据urlID=" + urlID + "&startMonth=" + startMonth.toString() + "&endMonth=" + endMonth.toString());
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.info("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			return result;
		}
		
		Date now = new Date();
		//开始时间设置为月份的第一天开始时间
		startMonth = DateUtils.getFirstDateOfMonth(startMonth);
		if (startMonth.after(now)) {
			//时间在当前时间之后，设置为当前时间
			startMonth = DateUtils.getDateBegin(now);
		}
		//结束时间设置为月份最后一天，即下个月份的第一天00：00：00.0
		endMonth = DateUtils.getLastDateOfMonth(endMonth);
		if (endMonth.after(now)) {
			//结束时间在当前时间之后，设置为当前时间的前一天，当前这一天还没有统计
			endMonth = DateUtils.getDateBegin(DateUtils.getPrevDate(now));
		}
		
		List<StatisticActivity> statActivities = statisticActivityDao.queryListByUrlIDAndMonth(urlID, startMonth, endMonth);
		
		//图表标题
		String chartSubtitle = DateUtils.dateToFormatStr(startMonth, "yyyy/MM") + "~" + DateUtils.dateToFormatStr(endMonth, "yyyy/MM");
		//需要获取UTC时间戳
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(startMonth);
		long chartPointStart = startMonth.getTime() + cal.getTimeZone().getRawOffset();
		
		//图表数据，将统计表中没有的补为0
		List<Integer> chartData = new ArrayList<Integer>();
		for(Date date=startMonth; date.compareTo(startMonth)>=0 && date.compareTo(endMonth)<=0; date = DateUtils.getNextDate(date)){
			int i;
			for(i=0; i<statActivities.size(); ++i){
				if (statActivities.get(i).getStatDate().compareTo(date) == 0) {
					break;
				}
			}
			if (i<statActivities.size()) {
				chartData.add(statActivities.get(i).getArticleCount());
			} else {
				chartData.add(0);
			}
		}
		
		result.addData("subtitle", chartSubtitle);
		result.addData("pointStart", chartPointStart);
		result.addData("data", chartData);
		
		return result;
	}

	@Transactional
	@Override
	@CacheEvict(value = "statisticActivityServiceCache", allEntries = true)
	public ResultInfo statisticActivity() {
		logger.info("还未统计的到当前时间截止的统计数据");
		
		ResultInfo result = new ResultInfo(true);
		
		List<Object> activities = statisticActivityDao.createStatInfo();
		
		//插入到统计表里
		for(int i=0; i<activities.size(); ++i){
			User user = userDao.queryByID((Integer)((Object[])(activities.get(i)))[0]);
			Integer statYear = (Integer)((Object[])(activities.get(i)))[1];
			Integer statMonth = (Integer)((Object[])(activities.get(i)))[2];
			Integer statDay = (Integer)((Object[])(activities.get(i)))[3];
			Date statDate = DateUtils.strToDate(statYear + "-" + (statMonth<10?("0"+statMonth):statMonth) + "-" + (statDay<10?("0"+statDay):statDay));
			Integer articleCount = ((BigInteger)((Object[])(activities.get(i)))[4]).intValue();
			
			StatisticActivity activity = new StatisticActivity();
			activity.setUser(user);
			activity.setStatDate(statDate);
			activity.setArticleCount(articleCount);
			
			statisticActivityDao.add(activity);
		}
		return result;
	}
	
}
