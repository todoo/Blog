package cn.com.daybreak.blog.timer.statistic.task;

import java.util.Date;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.service.StatisticClassifyManager;

@Component
public class StatisticClassifyTimerTask extends TimerTask {
	@Autowired
	private StatisticClassifyManager classifyManager;
	@Override
	public void run() {
		ResultInfo result = classifyManager.statisticClassify();
		if (!result.isSuccess()) {
			System.out.println(new Date() + ": 更新博文分类统计数据失败 : " + result.getMessage());
		} else {
			System.out.println(new Date() + ": 更新博文分类统计数据成功");
		}
	}

}
