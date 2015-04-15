package cn.com.daybreak.blog.service.impl;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.BackgroundMusicDao;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.BackgroundMusic;
import cn.com.daybreak.blog.service.BackgroundMusicManager;

@Service
public class BackgroundMusicManagerImpl implements BackgroundMusicManager {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BackgroundMusicDao backgroundMusicDao;
	
	private Logger logger = Logger.getLogger(BackgroundMusicManagerImpl.class);
	
	@Transactional
	@Override
	public ResultInfo getRandomBackgroundMusic(String urlID) {
		logger.info("随机获取用户背景音乐urlID=" + urlID);
		
		ResultInfo result = new ResultInfo(true);
			
		//检测用户是否存在
		if (!userDao.isExistUserByUrlID(urlID)) {
			logger.error("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
			return result;
		}
		
		//获取背景音乐列表
		List<BackgroundMusic> bgMusics = backgroundMusicDao.queryAllByUrlID(urlID);
		
		//随机获取一个背景音乐
		int size = bgMusics.size();
		BackgroundMusic bgMusic = null;
		if (size>0) {
			Random random = new Random(System.currentTimeMillis());
			int index = random.nextInt(size);
			bgMusic = bgMusics.get(index);
		}
		
		if (bgMusic != null) {
			result.addData("musicName", bgMusic.getBgMusicName());
			result.addData("musicPath", bgMusic.getMedia().getMediaPath());
		} else {
			logger.error("用户没有设置背景音乐");
			result.setSuccess(false);
			result.setMessage("用户没有设置背景音乐");
		}
			
		return result;
	}

}
