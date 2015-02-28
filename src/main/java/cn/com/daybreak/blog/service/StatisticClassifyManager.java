package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface StatisticClassifyManager {
	/**
	 * 对所有用户的博文分类进行统计
	 * @return
	 */
	public ResultInfo statisticClassify();
	
	/**
	 * 获取博文分类图表统计数据
	 * @param urlID
	 * @return
	 */
	public ResultInfo getClassifyChartData(String urlID);
}
