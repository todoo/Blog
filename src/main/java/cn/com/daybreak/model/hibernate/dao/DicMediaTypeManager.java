package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface DicMediaTypeManager {
	/**
	 * 获取媒体类型
	 * @param mediaTypeName
	 * @return
	 */
	public ResultInfo getMediaType(String mediaTypeName);
}
