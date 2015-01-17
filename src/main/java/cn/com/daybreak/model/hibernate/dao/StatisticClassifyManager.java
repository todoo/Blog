package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

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
