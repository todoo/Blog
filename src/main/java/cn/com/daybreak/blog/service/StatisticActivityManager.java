package cn.com.daybreak.blog.service;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface StatisticActivityManager {
	/**
	 * 获取从 startMonth到endMonth的活跃度图表统计数据
	 * @param urlID
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public ResultInfo getActivityChartData(String urlID, Date startMonth, Date endMonth);
	
	/**
	 * 对所有用户的日活跃度进行统计
	 * @return
	 */
	public ResultInfo statisticActivity();
}
