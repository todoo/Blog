package cn.com.daybreak.blog.dao;

import cn.com.daybreak.blog.model.entity.User;

public interface UserDao extends BaseDao<User>{
	/**
	 * urlID是否存在
	 * @param urlID
	 * @return
	 */
	public boolean isExistUserByUrlID(String urlID);
	
	/**
	 * userName是否存在
	 * @param userName
	 * @return
	 */
	public boolean isExistUserByUserName(String userName);
	
	/**
	 * userID用户是否存在
	 * @param userID
	 * @return
	 */
	public boolean isExistUserByUserID(int userID);
	
	/**
	 * 根据urlID查询用户
	 * @param urlID
	 * @return
	 */
	public User queryByUrlID(String urlID);
	
	/**
	 * 根据userName查用户
	 * @param userName
	 * @return
	 */
	public User queryByUserName(String userName);
}
