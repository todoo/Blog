package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.User;

public interface UserManager {
	/**
	 * 获取用户信息
	 * @param userID
	 * @return
	 * @throws Exception 
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
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public ResultInfo addUser(User user);
	
}
