package cn.com.daybreak.blog.dao;

import java.util.List;

import cn.com.daybreak.blog.model.entity.StatisticClassify;

public interface StatisticClassifyDao extends BaseDao<StatisticClassify> {
	/**
	 * 清空统计表
	 * @return
	 */
	public int deleteAll();
	
	/**
	 * 生成统计数据
	 */
	public void addStatInfo();
	
	/**
	 * 获取分类统计数据
	 * @param urlID
	 * @return
	 */
	public List<StatisticClassify> queryListByUrlID(String urlID);
}
