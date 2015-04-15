package cn.com.daybreak.blog.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.BlogDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.Blog;
import cn.com.daybreak.blog.service.BlogManager;

@Service
public class BlogManagerImpl implements BlogManager {
	@Autowired
	private SessionFactory sf;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	private Logger logger = Logger.getLogger(BlogManagerImpl.class);
	
	@Override
	public ResultInfo getBlog(int userID) {
		logger.info("获取博客信息userID=" + userID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUserID(userID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result;
		}
		
		Blog blog = blogDao.queryByUserID(userID);
		if (null == blog) {
			logger.error("博客不存在");
			result.setSuccess(false);
			result.setMessage("博客不存在");
			
			return result;
		}
		result.addData("blog", blog);
		
		return result;
	}

}
