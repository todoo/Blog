package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.DicMediaTypeDao;
import cn.com.daybreak.blog.model.entity.DicMediaType;
import cn.com.daybreak.blog.model.entity.User;

@Repository
public class DicMediaTypeDaoImpl implements DicMediaTypeDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(DicMediaType entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(DicMediaType entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public DicMediaType queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DicMediaType queryByMediaTypeName(String mediaTypeName) {
		Session session = null;
		try {
			session = sf.openSession();
			
			String hql = "from DicMediaType where mediaTypeName=:mediaTypeName";
			Query query = session.createQuery(hql);
			query.setString("mediaTypeName", mediaTypeName);
			List<DicMediaType> mediaTypes = query.setCacheable(true).list();
			
			if (mediaTypes.size()<=0) {
				return null;
			}
			return mediaTypes.get(0);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
