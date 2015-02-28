package cn.com.daybreak.blog.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.BlogManager;

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
