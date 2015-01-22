package cn.com.daybreak.model.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.ArticleManager;
import cn.com.daybreak.model.hibernate.entity.Article;
import cn.com.daybreak.model.hibernate.entity.ArticleCategory;
import cn.com.daybreak.model.hibernate.entity.User;

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

}
