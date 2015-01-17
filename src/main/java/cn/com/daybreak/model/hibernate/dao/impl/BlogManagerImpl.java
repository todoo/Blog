package cn.com.daybreak.model.hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.BlogManager;
import cn.com.daybreak.model.hibernate.entity.User;

@Service
public class BlogManagerImpl implements BlogManager {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public ResultInfo getBlog(int userID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			User user = (User) session.get(User.class, userID);
			
			session.getTransaction().commit();
			
			if (user != null)
				result.addData("blog", user.getBlog());
			else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取博客信息异常");
		}
		return result;
	}

}
