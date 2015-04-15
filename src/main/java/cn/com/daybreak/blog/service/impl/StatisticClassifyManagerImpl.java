package cn.com.daybreak.blog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.CategoryDao;
import cn.com.daybreak.blog.dao.StatisticClassifyDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.StatisticClassify;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.StatisticClassifyManager;

@Service
public class StatisticClassifyManagerImpl implements StatisticClassifyManager {
	@Autowired
	private StatisticClassifyDao statisticClassifyDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserDao userDao;
	
	private Logger logger = Logger.getLogger(StatisticClassifyManagerImpl.class);
	
	@Transactional
	@Override
	@CacheEvict(value = "statisticClassifyServiceCache", allEntries = true)
	public ResultInfo statisticClassify() {
		logger.info("统计分类数据");
		
		ResultInfo result = new ResultInfo(true);
		
		//删除旧的统计数据
		statisticClassifyDao.deleteAll();
		
		//统计用户博文分类数据
		statisticClassifyDao.addStatInfo();

		return result;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "statisticClassifyServiceCache", key = "'getClassifyChartData' + #urlID")
	public ResultInfo getClassifyChartData(String urlID) {
		logger.info("获取统计分类数据urlID=" + urlID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.info("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result; 
		}
		
		//获取统计数据
		List<StatisticClassify> statClassifies = statisticClassifyDao.queryListByUrlID(urlID);
		
		List<List<Object>> chartData = new ArrayList<List<Object>>();
		for (int i=0; i<statClassifies.size(); ++i) {
			List<Object> cateData = new ArrayList<Object>();
			cateData.add(statClassifies.get(i).getCategory().getCategoryName());
			cateData.add(statClassifies.get(i).getPercent());
			chartData.add(cateData);
		}
		result.addData("data", chartData);
		
		return result;
	}

}
