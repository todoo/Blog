package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.common.tools.DateUtils;
import cn.com.daybreak.blog.dao.StatisticActivityDao;
import cn.com.daybreak.blog.model.entity.StatisticActivity;

@Repository
public class StatisticActivityDaoImpl implements StatisticActivityDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(StatisticActivity statisticActivity) {
		Session session = sf.getCurrentSession();
		//保存数据
		Serializable id = session.save(statisticActivity);
		
		return id;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(StatisticActivity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public StatisticActivity queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticActivity> queryListByUrlIDAndMonth(String urlID,
			Date startDate, Date endDate) {
		Session session  = null;
		try {
			session = sf.openSession();
			
			String hql = "from StatisticActivity sa where sa.user.urlID=:urlID and sa.statDate>=:startDate and sa.statDate<=:endDate order by statDate";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			query.setDate("startDate", startDate);
			query.setDate("endDate", endDate);
			List<StatisticActivity> statActivities = query.setCacheable(true).list();
				
			return statActivities;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> createStatInfo() {
		Session session  = null;
		try {
			session = sf.openSession();
			//获取最后活跃度统计表中数据的最后日期
			String hql = "select max(statDate) from StatisticActivity";
			Query query = session.createQuery(hql);
			List<Date> statDateList = query.list();
			
			//获取最后日期之后的统计数据
			String sql = null;
			SQLQuery sqlQuery = null;
			if (0 == statDateList.size() || null == statDateList.get(0)) {
				sql = "SELECT c.userID,c.statYear, c.statMonth, c.statDay,COUNT(*) AS articleCount FROM(" +
							" SELECT b.*  FROM" +
							" (SELECT a.*,YEAR(a.updateTime) AS statYear,MONTH(a.updateTime) AS statMonth,DAY(a.updateTime) AS statDay  FROM article a) b" + 
							" UNION" +
							" SELECT b.*  FROM" +
							" (SELECT a.*,YEAR(a.createTime) AS statYear,MONTH(a.createTime) AS statMonth,DAY(a.createTime) AS statDay  FROM article a) b" + 
							" ) c GROUP BY c.userID,c.statYear, c.statMonth, c.statDay";
				sqlQuery = session.createSQLQuery(sql);
			} else {
				Date lastStatDate = statDateList.get(0);
				Date firstStatDate = DateUtils.getNextDate(lastStatDate);
				sql = "SELECT c.userID,c.statYear, c.statMonth, c.statDay,COUNT(*) AS articleCount FROM(" +
						" SELECT b.*  FROM" +
						" (SELECT a.*,YEAR(a.updateTime) AS statYear,MONTH(a.updateTime) AS statMonth,DAY(a.updateTime) AS statDay  FROM article a WHERE a.updateTime>=:firstStatDate) b" + 
						" UNION" +
						" SELECT b.*  FROM" +
						" (SELECT a.*,YEAR(a.createTime) AS statYear,MONTH(a.createTime) AS statMonth,DAY(a.createTime) AS statDay  FROM article a WHERE a.updateTime>=:firstStatDate) b" + 
						" ) c GROUP BY c.userID,c.statYear, c.statMonth, c.statDay";
				sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setDate("firstStatDate", firstStatDate);
			}
			List<Object> activities = sqlQuery.list();
			
			return activities;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
