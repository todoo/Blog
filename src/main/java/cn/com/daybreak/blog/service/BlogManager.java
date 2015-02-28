package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface BlogManager {
	/**
	 * 获取博客信息
	 * @param userID
	 * @return
	 */
	public ResultInfo getBlog(int userID);
}
