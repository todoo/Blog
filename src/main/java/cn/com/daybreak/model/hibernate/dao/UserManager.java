package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface UserManager {
	/**
	 * 获取用户信息
	 * @param userID
	 * @return
	 */
	public ResultInfo getUser(int userID);
	
	/**
	 * 通过urlID获取用户信息
	 * @param urlID
	 * @return
	 */
	public ResultInfo getUser(String urlID);
}
