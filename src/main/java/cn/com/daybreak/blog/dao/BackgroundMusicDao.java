package cn.com.daybreak.blog.dao;

import java.util.List;

import cn.com.daybreak.blog.dao.BaseDao;
import cn.com.daybreak.blog.model.entity.BackgroundMusic;

public interface BackgroundMusicDao extends BaseDao<BackgroundMusic> {
	/**
	 * 获取所有用户的背景音乐
	 * @return
	 */
	public List<BackgroundMusic> queryAllByUrlID(String urlID);
}
