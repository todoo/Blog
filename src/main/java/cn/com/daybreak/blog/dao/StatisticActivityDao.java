package cn.com.daybreak.blog.dao;

import java.util.Date;
import java.util.List;

import cn.com.daybreak.blog.model.entity.StatisticActivity;

public interface StatisticActivityDao extends BaseDao<StatisticActivity> {
	/**
	 * 获取指定时间范围内的统计数据
	 * @param urlID
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public List<StatisticActivity> queryListByUrlIDAndMonth(String urlID, Date startDate,
			Date endDate);
	
	/**
	 * 生成还未统计的到当前时间截止的统计数据
	 * @return
	 */
	public List<Object> createStatInfo();
}
