package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.Media;

public interface MediaManager {
	/**
	 * 新增媒体
	 * @param media
	 * @return
	 */
	public ResultInfo addMedia(Media media);
}
