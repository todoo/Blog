package cn.com.daybreak.blog.dao;

import cn.com.daybreak.blog.model.entity.DicMediaType;

public interface DicMediaTypeDao extends BaseDao<DicMediaType> {
	/**
	 * 获取媒体类型
	 * @param mediaTypeName
	 * @return
	 */
	public DicMediaType queryByMediaTypeName(String mediaTypeName);
}
