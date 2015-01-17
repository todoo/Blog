package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface BlogManager {
	/**
	 * 获取博客信息
	 * @param userID
	 * @return
	 */
	public ResultInfo getBlog(int userID);
}
