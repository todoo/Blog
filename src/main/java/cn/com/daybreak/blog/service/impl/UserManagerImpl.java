package cn.com.daybreak.blog.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.UserManager;

@Service
public class UserManagerImpl implements UserManager {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public ResultInfo getUser(int userID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			User user = (User) session.get(User.class, userID);
			
			session.getTransaction().commit();
			
			if (user != null) {
				result.addData("user", user);
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取用户信息异常");
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getUser(String urlID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			String hql = "from User where urlID=?";
			Query query = session.createQuery(hql);
			query.setString(0, urlID);
			List<User> users = query.list();
			if (users.size()>0) {
				result.addData("user", users.get(0));
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取用户信息异常");
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getUserByUserName(String userName) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			String hql = "from User where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			List<User> users = query.list();
			if (users.size()>0) {
				result.addData("user", users.get(0));
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取用户信息异常");
		}
		
		return result;
	}

}
