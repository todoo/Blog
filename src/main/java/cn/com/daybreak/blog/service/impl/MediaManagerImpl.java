package cn.com.daybreak.blog.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.Media;
import cn.com.daybreak.blog.service.MediaManager;

@Service
public class MediaManagerImpl implements MediaManager {
	@Autowired
	private SessionFactory sf;

	@Override
	public ResultInfo addMedia(Media media) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			session.save(media);
			
			session.getTransaction().commit();
			
			result.addData("media", media);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("新增媒体异常");
		}
		return result;
	}
}
