package cn.com.daybreak.blog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.LifeStatusDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.LifeStatus;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.LifeStatusManager;

@Service
public class LifeStatusManagerImpl implements LifeStatusManager {
	@Autowired
	private LifeStatusDao lifeStatusDao;
	
	@Autowired
	private UserDao userDao;
	
	private Logger logger = Logger.getLogger(LifeStatusManagerImpl.class);
	
	@Override
	public ResultInfo getLastestLifeStatus(int userID) {
		logger.info("获取最新状态userID=" + userID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUserID(userID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			return result;
		}
		
		//获取最新状态
		LifeStatus status = lifeStatusDao.queryLastestByUserID(userID);
		result.addData("lifeStatus", status);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getLastestLifeStatus(String urlID) {
		logger.info("获取最新状态urlID=" + urlID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			return result;
		}
		
		//获取最新状态
		LifeStatus lifeStatus = lifeStatusDao.queryLastestByUrlID(urlID);	
		result.addData("lifeStatus", lifeStatus);
	
		return result;
	}

}
