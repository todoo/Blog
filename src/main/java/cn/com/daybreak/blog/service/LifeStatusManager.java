package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface LifeStatusManager {
	/**
	 * 获取最新的用户状态
	 * @param userID
	 * @return
	 */
	public ResultInfo getLastestLifeStatus(int userID); 
	
	/**
	 * 获取最新的用户状态
	 * @param urlID
	 * @return
	 */
	public ResultInfo getLastestLifeStatus(String urlID); 
}
