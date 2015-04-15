package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.ArticleDao;
import cn.com.daybreak.blog.model.entity.Article;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class ArticleDaoImpl implements ArticleDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(Article article) {
		Session session = sf.getCurrentSession();
		
		//保存数据
		Serializable id = session.save(article);
		
		return id;
	}

	@Override
	public int deleteByID(Serializable id) {
		Session session = sf.getCurrentSession();
		Article article = (Article) session.get(Article.class, id);
		
		if (null == article)
			return 0;
		
		session.delete(article);
		
		return 1;
	}

	@Override
	public void update(Article article) {
		Session session = sf.getCurrentSession();
		
		session.update(article);
	}

	@Override
	public Article queryByID(Serializable id) {
		Session session = null;
		try {
			session = sf.openSession();
			
			Article article = (Article) session.get(Article.class, id);
			
 			return article;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticlesByUrlID(String urlID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where urlID = :urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users = query.setCacheable(true).list();
			
 			if (users.size() >0) {
				return new ArrayList<Article>(users.get(0).getArticles());
			}
 			
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	@Override
	public List<Article> getArticlesByCategoryID(int categoryID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			ArticleCategory category = (ArticleCategory) session.get(ArticleCategory.class, categoryID);
			if (null == category) 
				return null;
			
			return new ArrayList<Article>(category.getArticles());
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean isExistArticleByUserNameAndArticleID(String userName,
			int articleID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			Article article = (Article) session.get(Article.class, articleID);
			if (null == article)
				return false;
			
			User user = article.getUser();
			if (user.getUserName().equals(userName))
				return true;
			else 
				return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
