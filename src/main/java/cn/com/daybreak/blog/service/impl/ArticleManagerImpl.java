package cn.com.daybreak.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.Article;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.ArticleManager;

@Service
public class ArticleManagerImpl implements ArticleManager {
	@Autowired
	private SessionFactory sf;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getArticles(String urlID, int categoryID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			List<Article> articles = null;
			
			if (0 == categoryID) {
				String hql = "from User a where a.urlID=:urlID";
				Query query = session.createQuery(hql);
				query.setString("urlID", urlID);
				List<User> users = query.list();
				User user = users.size()>0?users.get(0):null;
				
				if (null == user) {
					throw new Exception();
				}
				articles = new ArrayList<Article>(user.getArticles());
			} else {
				ArticleCategory articleCategory = (ArticleCategory) session.load(ArticleCategory.class, categoryID);
				if (!articleCategory.getUser().getUrlID().equals(urlID)) {
					throw new Exception();
				}
				
				articles = new ArrayList<Article>(articleCategory.getArticles());
			}
			
			for(int i=0; i<articles.size(); ++i) {
				articles.get(i).setCategoryID(articles.get(i).getCategory().getCategoryID());
			}
			
			result.addData("articles", articles);
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取类目直接博文异常");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo addArticleByUserName(String userName, int categoryID,
			Article article) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			//设置博文所属用户
			String hql = "from User a where a.userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			List<User> users = query.list();
			if (users.size()<=0) {
				throw new Exception();
			}
			article.setUser(users.get(0));
			
			//设置博文所属类目
			ArticleCategory category = (ArticleCategory) session.load(ArticleCategory.class, categoryID);
			article.setCategory(category);
			
			//设置博文创建和更新时间
			Date now = new Date();
			article.setCreateTime(now);
			article.setUpdateTime(now);
			
			//保存
			session.save(article);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("新增博文异常");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo deleteArticleByUserNameAndArticleID(String userName,
			int articleID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			String hql = "from User a where a.userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			List<User> users = query.list();
			if (users.size()<=0) {
				throw new Exception();
			}
			
			Article article = (Article) session.load(Article.class, articleID);
			
			//检验博文用户与登陆用户是否一致
			if (!article.getUser().getUserName().equals(userName)) {
				throw new Exception();
			}
			
			session.delete(article);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("删除博文异常");
		}
		return result;
	}

	@Override
	public ResultInfo updateArticleByUserName(String userName, Article article) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			Article oldArticle = (Article) session.load(Article.class, article.getArticleID());
			if (!oldArticle.getUser().getUserName().equals(userName)) {
				//博文与登陆用户不匹配
				throw new Exception();
			}
			
			oldArticle.setArticleTitle(article.getArticleTitle());
			oldArticle.setArticleBrief(article.getArticleBrief());
			oldArticle.setArticleContent(article.getArticleContent());
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("更新博文异常");
		}
		return result;
	}

}
