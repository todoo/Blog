package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.entity.Media;

public interface MediaManager {
	/**
	 * 新增媒体
	 * @param media
	 * @return
	 */
	public ResultInfo addMedia(Media media);
}
