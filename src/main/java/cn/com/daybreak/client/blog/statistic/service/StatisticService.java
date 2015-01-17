package cn.com.daybreak.client.blog.statistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticService {
	@Autowired
	private StatisticActivityTimerTask statisticActivityTimerTask;
	
	@Autowired
	private StatisticClassifyTimerTask statisticClassifyTimerTask;
	
	@Scheduled(cron = "0 0 2 * * ?")
    public void statisticActivityService() {
		statisticActivityTimerTask.run();
    }
	
	@Scheduled(cron = "0 10 2 * * ?")
    public void statisticClassifyService() {
		statisticClassifyTimerTask.run();
    }
}
