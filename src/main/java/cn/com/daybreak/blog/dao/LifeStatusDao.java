package cn.com.daybreak.blog.dao;

import cn.com.daybreak.blog.model.entity.LifeStatus;

public interface LifeStatusDao extends BaseDao<LifeStatus> {
	/**
	 * 获取最新状态
	 * @param userID
	 * @return
	 */
	public LifeStatus queryLastestByUserID(int userID);
	
	/**
	 * 获取最新状态
	 * @param urlID
	 * @return
	 */
	public LifeStatus queryLastestByUrlID(String urlID);
}
