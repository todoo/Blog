package cn.com.daybreak.model.hibernate.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.CategoryManager;
import cn.com.daybreak.model.hibernate.entity.ArticleCategory;

@Service
public class CategoryManagerImpl implements CategoryManager{
	@Autowired
	private SessionFactory sf;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getCategorys(String urlID, int parentID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			List<ArticleCategory> articleCategories = null;
			
			String sql = "select * from ArticleCategory a,User b where a.parentID=:parentID and b.urlID=:urlID and a.userID=b.userID";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setString("urlID", urlID);
			sqlQuery.setInteger("parentID", parentID);
			
			articleCategories = sqlQuery.addEntity(ArticleCategory.class).list();
			
			result.addData("categories", articleCategories);

			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取类目列表异常");
		}
		return result;
	}

}
