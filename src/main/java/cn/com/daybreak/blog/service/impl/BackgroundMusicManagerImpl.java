package cn.com.daybreak.blog.service.impl;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.BackgroundMusic;
import cn.com.daybreak.blog.service.BackgroundMusicManager;

@Service
public class BackgroundMusicManagerImpl implements BackgroundMusicManager {
	@Autowired
	private SessionFactory sf;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getRandomBackgroundMusic(String urlID) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			
			session.beginTransaction();
			String hql = "from BackgroundMusic a where a.user.urlID=:urlID";
			Query query = session.createQuery(hql);
			query.setString("urlID", urlID);
			List<BackgroundMusic> bgMusics = query.setCacheable(true).list();
			
			//随机获取一个背景音乐
			int size = bgMusics.size();
			BackgroundMusic bgMusic = null;
			if (size>0) {
				Random random = new Random(System.currentTimeMillis());
				int index = random.nextInt(size);
				bgMusic = bgMusics.get(index);
			}
			
			if (bgMusic!=null) {
				result.addData("musicName", bgMusic.getBgMusicName());
				result.addData("musicPath", bgMusic.getMedia().getMediaPath());
			} else {
				result.setSuccess(false);
				result.setMessage("用户没有设置背景音乐");
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取随机背景音乐异常");
		}
		return result;
	}

}
