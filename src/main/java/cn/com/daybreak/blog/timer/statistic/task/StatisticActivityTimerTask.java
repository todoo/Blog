package cn.com.daybreak.blog.timer.statistic.task;

import java.util.Date;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.service.StatisticActivityManager;

@Component
public class StatisticActivityTimerTask extends TimerTask {
	@Autowired
	private StatisticActivityManager activityManager;
	@Override
	public void run() {
		ResultInfo result = activityManager.statisticActivity();
		if (!result.isSuccess()) {
			System.out.println(new Date() + ": 更新活跃度统计数据失败 : " + result.getMessage());
		} else {
			System.out.println(new Date() + ": 更新活跃度统计数据成功");
		}
	}

}
