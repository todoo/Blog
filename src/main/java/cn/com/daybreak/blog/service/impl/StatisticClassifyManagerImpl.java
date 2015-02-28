package cn.com.daybreak.blog.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.ArticleCategory;
import cn.com.daybreak.blog.model.entity.StatisticClassify;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.StatisticClassifyManager;

@Service
public class StatisticClassifyManagerImpl implements StatisticClassifyManager {
	@Autowired
	private SessionFactory sf;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo statisticClassify() {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			//删除旧的统计数据
			String hql = "delete StatisticClassify";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			
			//获取第一层类目
			hql = "from ArticleCategory a where a.parentCategory.categoryID=0 order by a.user.userID,a.categoryID";
			query = session.createQuery(hql);
			List<ArticleCategory> rootCategories = query.list();
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
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("统计博文分类数据异常");
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

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getClassifyChartData(String urlID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			String hql = "from User where urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users= query.list();
			
			User user = null;
			if (users.size()>0) {
				user = (User) users.get(0);
			}
			
			if (user != null) {
				List<StatisticClassify> statClassifies = new ArrayList<StatisticClassify>(user.getStatClassifies());
				List<List<Object>> chartData = new ArrayList<List<Object>>();
				for (int i=0; i<statClassifies.size(); ++i) {
					List<Object> cateData = new ArrayList<Object>();
					cateData.add(statClassifies.get(i).getCategory().getCategoryName());
					cateData.add(statClassifies.get(i).getPercent());
					chartData.add(cateData);
				}
				result.addData("data", chartData);
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
						
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取博文分类数据异常");
		}
		return result;
	}

}
