package cn.com.daybreak.blog.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.daybreak.blog.dao.BackgroundMusicDao;
import cn.com.daybreak.blog.model.entity.BackgroundMusic;

@Repository
public class BackgroundMusicDaoImpl implements BackgroundMusicDao {
	@Autowired
	private SessionFactory sf;
	
	@Override
	public Serializable add(BackgroundMusic entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(BackgroundMusic entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public BackgroundMusic queryByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BackgroundMusic> queryAllByUrlID(String urlID) {
		Session session = sf.getCurrentSession();
		String hql = "from BackgroundMusic a where a.user.urlID=:urlID";
		Query query = session.createQuery(hql);
		query.setString("urlID", urlID);
		List<BackgroundMusic> bgMusics = query.setCacheable(true).list();
			
		return bgMusics;
	}

}
