package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.MediaDao;
import cn.com.daybreak.blog.model.entity.Media;

@Repository
public class MediaDaoImpl implements MediaDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(Media media) {
		Session session = sf.getCurrentSession();
		
		//保存数据
		Serializable id = session.save(media);
		
		return id;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Media entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Media queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
