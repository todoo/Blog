package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.LifeStatusDao;
import cn.com.daybreak.blog.model.entity.LifeStatus;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class LifeStatusDaoImpl implements LifeStatusDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(LifeStatus entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(LifeStatus entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public LifeStatus queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LifeStatus queryLastestByUserID(int userID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			User user = (User) session.get(User.class, userID);
			if (user != null) {
				List<LifeStatus> statuses = new ArrayList<LifeStatus>(user.getStatuses());
				if (statuses.size()>0) {
					return statuses.get(0);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public LifeStatus queryLastestByUrlID(String urlID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users = query.setCacheable(true).list();
			if (users.size() == 0) {
				return null;
			}
			
			User user = users.get(0);
			List<LifeStatus> statuses = new ArrayList<LifeStatus>(user.getStatuses());
			if (statuses.size()>0) {
				return statuses.get(0);
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
