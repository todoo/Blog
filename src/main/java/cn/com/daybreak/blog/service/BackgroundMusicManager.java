package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface BackgroundMusicManager {
	/**
	 * 随机获取一首用户的背景音乐
	 * @param urlID
	 * @return
	 */
	public ResultInfo getRandomBackgroundMusic(String urlID);
}
