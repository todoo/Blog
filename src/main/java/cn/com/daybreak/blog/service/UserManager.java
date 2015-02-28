package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

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
	
	/**
	 * 通过userName获取用户信息
	 * @param userName
	 * @return
	 */
	public ResultInfo getUserByUserName(String userName);
}
