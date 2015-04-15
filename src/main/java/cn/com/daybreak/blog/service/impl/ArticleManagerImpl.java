package cn.com.daybreak.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.ArticleDao;
import cn.com.daybreak.blog.dao.CategoryDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.Article;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.ArticleManager;

@Service
public class ArticleManagerImpl implements ArticleManager {
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	private Logger logger = Logger.getLogger(ArticleManagerImpl.class);
	
	@Override
	public ResultInfo getArticles(String urlID, int categoryID) {
		logger.info("获取博文 urlID=" + urlID + "&categoryID=" + categoryID);
		
		ResultInfo result = new ResultInfo(true);
		
		List<Article> articles = null;
		
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result;
		}
		
		if (0 == categoryID) {
			//如果categoryID=0，获取用户的所有博文
			articles = articleDao.getArticlesByUrlID(urlID);
		} else {
			//categoryID!=0,检测此人是否存在给类目
			if (!categoryDao.isExistCategoryByUrlIDAndCategoryID(urlID, categoryID)) {
				logger.error("类目不存在");
				result.setSuccess(false);
				result.setMessage("类目不存在");
				
				return result;
			}
			
			//获取类目博文
			articles = articleDao.getArticlesByCategoryID(categoryID);
		}
		
		if (null == articles) {
			logger.error("获取博文数据异常");
			result.setSuccess(false);
			result.setMessage("获取博文数据异常");
			
			return result;
		}
		
		//将categoryID传到前台,给实体类中非hibernate映射字段categoryID赋值
		for(int i=0; i<articles.size(); ++i) {
			articles.get(i).setCategoryID(articles.get(i).getCategory().getCategoryID());
		}
		
		result.setData("articles", articles);
		return result;
	}
	
	@Transactional
	@Override
	public ResultInfo addArticleByUserName(String userName, int categoryID,
			Article article) {
		logger.info("新增博文 userName=" + userName + "&categoryID=" + categoryID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUserName(userName)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result;
		}
		
		//检测类目是否存在
		if (!categoryDao.isExistCategoryByUserNameAndCategoryID(userName, categoryID)) {
			logger.error("类目不存在");
			result.setSuccess(false);
			result.setMessage("类目不存在");
			
			return result;
		}
		
		//新增博文
		//设置博文所属用户
		User user = userDao.queryByUserName(userName);
		article.setUser(user);
		
		//设置博文所属类目
		ArticleCategory category = categoryDao.queryByID(categoryID);
		article.setCategory(category);
		
		//设置博文创建和更新时间
		Date now = new Date();
		article.setCreateTime(now);
		article.setUpdateTime(now);
		
		//保存博文
		articleDao.add(article);
		logger.info("新增博文成功");
		
		return result;
	}

	@Transactional
	@Override
	public ResultInfo deleteArticleByUserNameAndArticleID(String userName,
			int articleID) {
		logger.info("删除博文 userName=" + userName + "&articleID=" + articleID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUserName(userName)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result;
		}
		
		//检测用户是否存在该类目
		if (!articleDao.isExistArticleByUserNameAndArticleID(userName, articleID)) {
			logger.error("博文不存在");
			result.setSuccess(false);
			result.setMessage("博文不存在");
			
			return result;
		}
		
		//删除博文
		int count = articleDao.deleteByID(articleID);
		if (0 == count) {
			logger.error("博文不存在");
			result.setSuccess(false);
			result.setMessage("博文不存在");
			
			return result;
		} 
		
		return result;
	}

	@Transactional
	@Override
	public ResultInfo updateArticleByUserName(String userName, Article article) {
		logger.info("更新博文 userName=" + userName + "&articleID=" + article.getArticleID());
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUserName(userName)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			
			return result;
		}
		
		//检测用户是否存在该类目
		if (!articleDao.isExistArticleByUserNameAndArticleID(userName, article.getArticleID())) {
			logger.error("博文不存在");
			result.setSuccess(false);
			result.setMessage("博文不存在");
			
			return result;
		}
		
		//更新博文
		articleDao.update(article);
		
		return result;
	}

	@Override
	public Article getArticleByArticleID(int articleID) {
		return articleDao.queryByID(articleID);
	}

}
