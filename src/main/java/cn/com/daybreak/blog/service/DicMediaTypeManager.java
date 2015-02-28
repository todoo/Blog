package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface DicMediaTypeManager {
	/**
	 * 获取媒体类型
	 * @param mediaTypeName
	 * @return
	 */
	public ResultInfo getMediaType(String mediaTypeName);
}
