package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.CategoryDao;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(ArticleCategory entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(ArticleCategory entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArticleCategory queryByID(Serializable id) {
		Session session  = null;
		try {
			session = sf.openSession();
			
			ArticleCategory article = (ArticleCategory) session.get(ArticleCategory.class, id);
				
			return article;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean isExistCategoryByUrlIDAndCategoryID(String urlID,
			int categoryID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			ArticleCategory category = (ArticleCategory) session.get(ArticleCategory.class, categoryID);
			if (null == category) 
				return false;
			
			User user = category.getUser();
			if (!urlID.equals(user.getUrlID()))
				return false;
			
			return true;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean isExistCategoryByUserNameAndCategoryID(String userName,
			int categoryID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			ArticleCategory category = (ArticleCategory) session.get(ArticleCategory.class, categoryID);
			if (null == category) 
				return false;
			
			User user = category.getUser();
			if (!userName.equals(user.getUserName()))
				return false;
			
			return true;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleCategory> queryListByUrlIDAndParentID(String urlID,
			int parentID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			List<ArticleCategory> articleCategories = null;
			
			String sql = "select * from ArticleCategory a,User b where a.parentID=:parentID and b.urlID=:urlID and a.userID=b.userID";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setString("urlID", urlID);
			sqlQuery.setInteger("parentID", parentID);
			
			articleCategories = sqlQuery.addEntity(ArticleCategory.class).setCacheable(true).list();
			
			return articleCategories;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public int queryArticleCountByCategoryID(int categoryID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			ArticleCategory category = (ArticleCategory) session.get(ArticleCategory.class, categoryID);
			if (category.getSubCategories().size()<=0) {
				return category.getArticles().size();
			}
			int sum = category.getArticles().size();
			List<ArticleCategory> subCategories = new ArrayList<ArticleCategory>(category.getSubCategories());
			for(int i=0; i<subCategories.size(); ++i) {
				sum += queryArticleCountByCategoryID(subCategories.get(i).getCategoryID());
			}
			
			return sum;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleCategory> queryRoot() {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from ArticleCategory a where a.parentCategory.categoryID=0 order by a.user.userID,a.categoryID";
			Query query = session.createQuery(hql);
			List<ArticleCategory> rootCategories = query.setCacheable(true).list();
			
			return rootCategories;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
