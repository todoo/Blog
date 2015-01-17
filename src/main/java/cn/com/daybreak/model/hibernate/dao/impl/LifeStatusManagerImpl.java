package cn.com.daybreak.model.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.LifeStatusManager;
import cn.com.daybreak.model.hibernate.entity.LifeStatus;
import cn.com.daybreak.model.hibernate.entity.User;

@Service
public class LifeStatusManagerImpl implements LifeStatusManager {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public ResultInfo getLastestLifeStatus(int userID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			User user = (User) session.get(User.class, userID);
			if (user != null) {
				List<LifeStatus> statuses = new ArrayList<LifeStatus>(user.getStatuses());
				if (statuses.size()>0) {
					result.addData("lifeStatus", statuses.get(0));
				} else {
					result.addData("lifeStatus", null);
				}
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取状态信息异常");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getLastestLifeStatus(String urlID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			
			String hql = "from User where urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			Iterator<User> users= query.iterate();
			
			User user = null;
			if (users.hasNext()) {
				user = (User) users.next();
			}
			if (user != null) {
				List<LifeStatus> statuses = new ArrayList<LifeStatus>(user.getStatuses());
				LifeStatus status = statuses.size()==0?null:statuses.get(0);
				result.addData("lifeStatus", status);
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取状态信息异常");
		}
		
		return result;
	}

}
