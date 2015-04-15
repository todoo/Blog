package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.BlogDao;
import cn.com.daybreak.blog.model.entity.Blog;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class BlogDaoImpl implements BlogDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(Blog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Blog entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Blog queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blog queryByUserID(int userID) {
		Session session = null;
		try {
			session = sf.openSession();
			
			User user = (User) session.get(User.class, userID);

			if (null == user) {
				return null;
			}
			return user.getBlog();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
