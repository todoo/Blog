package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface BackgroundMusicManager {
	/**
	 * 随机获取一首用户的背景音乐
	 * @param urlID
	 * @return
	 */
	public ResultInfo getRandomBackgroundMusic(String urlID);
}
