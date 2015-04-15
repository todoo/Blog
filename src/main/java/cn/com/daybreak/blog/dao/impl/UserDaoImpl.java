package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(User user) {
		Session session = sf.getCurrentSession();
		
		//保存数据
		Serializable id = session.save(user);
		
		return id;
	}

	@Override
	public int deleteByID(Serializable id) {
		Session session = sf.getCurrentSession();
		
		String hql = "delete from User where userID = :userID";
		Query query = session.createQuery(hql);
		query.setInteger("userID", (int)id);
		return query.executeUpdate();
	}

	@Override
	public void update(User user) {
		Session session = sf.getCurrentSession();
			
		session.update(user);
	}

	@Override
	public User queryByID(Serializable id) {
		Session session  = null;
		try {
			session = sf.openSession();
			
			User user = (User) session.get(User.class, id);
				
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean isExistUserByUrlID(String urlID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where urlID = :urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			if (query.setCacheable(true).list().size() >0) {
				return true;
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean isExistUserByUserName(String userName) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where userName = :userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			if (query.setCacheable(true).list().size() >0) {
				return true;
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User queryByUrlID(String urlID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where urlID = :urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<User> users = query.setCacheable(true).list();
			if (users.size() >0) {
				return users.get(0);
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User queryByUserName(String userName) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from User where userName = :userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			List<User> users = query.setCacheable(true).list();
			if (users.size() >0) {
				return users.get(0);
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean isExistUserByUserID(int userID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			User user = (User) session.get(User.class, userID);

			if (null == user) {
				return false;
			}
			return true;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
