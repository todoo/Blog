package cn.com.daybreak.blog.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.DicMediaTypeDao;
import cn.com.daybreak.blog.model.entity.DicMediaType;
import cn.com.daybreak.blog.service.DicMediaTypeManager;

@Service
public class DicMediaTypeManagerImpl implements DicMediaTypeManager {
	@Autowired
	private SessionFactory sf;

	@Autowired
	private DicMediaTypeDao mediaTypeDao;
	
	private Logger logger = Logger.getLogger(DicMediaTypeManagerImpl.class);
	
	@Override
	public ResultInfo getMediaType(String mediaTypeName) {
		logger.info("获取媒体类型mediaTypeName=" + mediaTypeName);
		
		ResultInfo result = new ResultInfo(true);
		DicMediaType mediaType = mediaTypeDao.queryByMediaTypeName(mediaTypeName);
		if (null == mediaType) {
			result.setSuccess(false);
			result.setMessage("不存在对应媒体信息");
			return result;
		}
		
		result.addData("mediaType", mediaType);
		
		return result;
	}
}
