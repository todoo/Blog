package cn.com.daybreak.blog.dao;

import cn.com.daybreak.blog.model.entity.Blog;

public interface BlogDao extends BaseDao<Blog> {
	/**
	 * 获取博客信息
	 * @param userID
	 * @return
	 */
	public Blog queryByUserID(int userID);
}
