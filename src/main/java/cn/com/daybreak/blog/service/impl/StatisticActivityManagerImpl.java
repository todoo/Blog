package cn.com.daybreak.blog.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import net.sf.ehcache.CacheManager;

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

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.common.tools.DateUtils;
import cn.com.daybreak.blog.model.entity.StatisticActivity;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.StatisticActivityManager;

@Service
public class StatisticActivityManagerImpl implements StatisticActivityManager {
	@Autowired
	private SessionFactory sf;
		
	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "statisticActivityServiceCache", key = "'getActivityChartData' + #urlID + T(cn.com.daybreak.blog.common.tools.DateUtils).dateToStr(#startMonth) + T(cn.com.daybreak.blog.common.tools.DateUtils).dateToStr(#endMonth)")
	public ResultInfo getActivityChartData(String urlID, Date startMonth,
			Date endMonth) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			String hql = "from User where urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users= query.list();
			
			User user = null;
			if (users.size()>0) {
				user = (User) users.get(0);
			}
			if (user != null) {
				Date now = new Date();
				//startMonth和endMonth的小时，分钟，秒钟，毫秒 都必须为0
				startMonth = DateUtils.getFirstDateOfMonth(startMonth);
				endMonth = DateUtils.getLastDateOfMonth(endMonth);
				if (startMonth.after(now)) {
					startMonth = DateUtils.getDateBegin(now);
				}
				if (endMonth.after(now)) {
					endMonth = DateUtils.getDateBegin(DateUtils.getPrevDate(now));
				}
				hql = "from StatisticActivity sa where sa.user.userID=:userID and sa.statDate>=:startDate and sa.statDate<=:endDate order by statDate";
				query = session.createQuery(hql);
				query.setInteger("userID", user.getUserID());
				query.setDate("startDate", startMonth);
				query.setDate("endDate", endMonth);
				List<StatisticActivity> statActivities = query.list();
				
				String chartSubtitle = DateUtils.dateToFormatStr(startMonth, "yyyy/MM") + "~" + DateUtils.dateToFormatStr(endMonth, "yyyy/MM");
				//需要获取UTC时间戳
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				cal.setTime(startMonth);
				long chartPointStart = startMonth.getTime() + cal.getTimeZone().getRawOffset();
				
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
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取状态信息异常");
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "statisticActivityServiceCache", allEntries = true)
	public ResultInfo statisticActivity() {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			//获取最后活跃度统计表中数据的最后日期
			String hql = "select max(statDate) from StatisticActivity";
			Query query = session.createQuery(hql);
			List<Date> statDateList = query.list();
			
			//获取最后日期之后的统计数据
			String sql = null;
			SQLQuery sqlQuery = null;
			if (0 == statDateList.size() || null == statDateList.get(0)) {
				sql = "SELECT c.userID,c.statYear, c.statMonth, c.statDay,COUNT(*) AS articleCount FROM(" +
							" SELECT b.*  FROM" +
							" (SELECT a.*,YEAR(a.updateTime) AS statYear,MONTH(a.updateTime) AS statMonth,DAY(a.updateTime) AS statDay  FROM article a) b" + 
							" UNION" +
							" SELECT b.*  FROM" +
							" (SELECT a.*,YEAR(a.createTime) AS statYear,MONTH(a.createTime) AS statMonth,DAY(a.createTime) AS statDay  FROM article a) b" + 
							" ) c GROUP BY c.userID,c.statYear, c.statMonth, c.statDay";
				sqlQuery = session.createSQLQuery(sql);
			} else {
				Date lastStatDate = statDateList.get(0);
				Date firstStatDate = DateUtils.getNextDate(lastStatDate);
				sql = "SELECT c.userID,c.statYear, c.statMonth, c.statDay,COUNT(*) AS articleCount FROM(" +
						" SELECT b.*  FROM" +
						" (SELECT a.*,YEAR(a.updateTime) AS statYear,MONTH(a.updateTime) AS statMonth,DAY(a.updateTime) AS statDay  FROM article a WHERE a.updateTime>=:firstStatDate) b" + 
						" UNION" +
						" SELECT b.*  FROM" +
						" (SELECT a.*,YEAR(a.createTime) AS statYear,MONTH(a.createTime) AS statMonth,DAY(a.createTime) AS statDay  FROM article a WHERE a.updateTime>=:firstStatDate) b" + 
						" ) c GROUP BY c.userID,c.statYear, c.statMonth, c.statDay";
				sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setDate("firstStatDate", firstStatDate);
			}
			List<Object> activities = sqlQuery.list();
			
			//批量插入到统计表里
			for(int i=0; i<activities.size(); ++i){
				User user = (User) session.get(User.class, (Integer)((Object[])(activities.get(i)))[0]);
				Integer statYear = (Integer)((Object[])(activities.get(i)))[1];
				Integer statMonth = (Integer)((Object[])(activities.get(i)))[2];
				Integer statDay = (Integer)((Object[])(activities.get(i)))[3];
				Date statDate = DateUtils.strToDate(statYear + "-" + (statMonth<10?("0"+statMonth):statMonth) + "-" + (statDay<10?("0"+statDay):statDay));
				Integer articleCount = ((BigInteger)((Object[])(activities.get(i)))[4]).intValue();
				
				StatisticActivity activity = new StatisticActivity();
				activity.setUser(user);
				activity.setStatDate(statDate);
				activity.setArticleCount(articleCount);
				
				session.save(activity);
				
				//每50个批处理，请session缓存
				if (0 == (i+1)%50) {
					session.flush();
					session.clear();
				}
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("统计活跃度数据异常");
		}
		return result;
	}
	
}
