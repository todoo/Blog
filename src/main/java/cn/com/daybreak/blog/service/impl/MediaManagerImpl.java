package cn.com.daybreak.blog.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.MediaDao;
import cn.com.daybreak.blog.model.entity.Media;
import cn.com.daybreak.blog.service.MediaManager;

@Service
public class MediaManagerImpl implements MediaManager {
	@Autowired
	private MediaDao mediaDao;
	
	private Logger logger = Logger.getLogger(UserManagerImpl.class);
	
	@Transactional
	@Override
	public ResultInfo addMedia(Media media) {
		logger.info("新增媒体media=" + media.toString());
		
		ResultInfo result = new ResultInfo(true);
		
		//新增媒体
		mediaDao.add(media);
		result.addData("media", media);
		
		return result;
	}
}
