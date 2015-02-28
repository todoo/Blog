package cn.com.daybreak.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.service.CategoryManager;

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
			
			for (int i=0; i<articleCategories.size(); ++i) {
				articleCategories.get(i).setArticleCount(getArticleCountByCategory(articleCategories.get(i)));
			}
			
			result.addData("categories", articleCategories);

			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取类目列表异常");
		}
		return result;
	}
	
	/**
	 * 获取某一类目及其子类目的所有博文数目，必须在session开启范围里调用
	 * @param category
	 * @return
	 */
	private int getArticleCountByCategory(ArticleCategory category) {
		if (category.getSubCategories().size()<=0) {
			return category.getArticles().size();
		}
		int sum = category.getArticles().size();
		List<ArticleCategory> subCategories = new ArrayList<ArticleCategory>(category.getSubCategories());
		for(int i=0; i<subCategories.size(); ++i) {
			sum += getArticleCountByCategory(subCategories.get(i));
		}
		
		return sum;
	}

}
