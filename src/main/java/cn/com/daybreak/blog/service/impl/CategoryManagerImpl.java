package cn.com.daybreak.blog.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.CategoryDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.service.CategoryManager;

@Service
public class CategoryManagerImpl implements CategoryManager{
	private Logger logger = Logger.getLogger(CategoryManagerImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public ResultInfo getCategorys(String urlID, int parentID) {
		logger.info("获取一个类目的所有子类目urlID=" + urlID + "&parentID=" + parentID);
		
		ResultInfo result = new ResultInfo(true);
		
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			return result;
		}
		
		//获取子类目列表
		List<ArticleCategory> articleCategories = categoryDao.queryListByUrlIDAndParentID(urlID, parentID);
		
		//获取每个类目中，当前类目及其子类目共包含博文的数目
		for (int i=0; i<articleCategories.size(); ++i) {
			articleCategories.get(i).setArticleCount(categoryDao.queryArticleCountByCategoryID(articleCategories.get(i).getCategoryID()));
		}
		
		result.addData("categories", articleCategories);
		
		return result;
	}
}
