package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.StatisticClassifyDao;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.StatisticClassify;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class StatisticClassifyDaoImpl implements StatisticClassifyDao{
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(StatisticClassify entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(StatisticClassify entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StatisticClassify queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAll() {
		Session session = sf.getCurrentSession();
		
		String hql = "delete StatisticClassify";
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addStatInfo() {
		Session session = sf.getCurrentSession();
		//获取第一层类目
		String hql = "from ArticleCategory a where a.parentCategory.categoryID=0 order by a.user.userID,a.categoryID";
		Query query = session.createQuery(hql);
		List<ArticleCategory> rootCategories = query.setCacheable(true).list();
		for(int i=0; i<rootCategories.size(); ) {
			User user = rootCategories.get(i).getUser();
			int sumArticleCount = user.getArticles().size();//用户的总博文数目
			if (sumArticleCount <= 0) {
				break;
			}
			for(int j=i; j<rootCategories.size() && rootCategories.get(j).getUser().getUrlID()==user.getUrlID(); ++j) {
				int cateArticleCount = getArticleCountByCategory(rootCategories.get(j));//用户某一类目博文总数
				float percent = (float)cateArticleCount/sumArticleCount;
				if (percent>0) {
					StatisticClassify statClassify = new StatisticClassify();
					statClassify.setUser(user);
					statClassify.setCategory(rootCategories.get(j));
					statClassify.setPercent(percent);
					session.save(statClassify);
				}
				++i;
			}
		}
		
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

	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticClassify> queryListByUrlID(String urlID) {
		Session session  = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users= query.setCacheable(true).list();
			if (users.size() == 0) {
				return null;
			}
			return new ArrayList<StatisticClassify>(users.get(0).getStatClassifies());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
