package cn.com.daybreak.client.blog.statistic.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.StatisticActivityManager;
import cn.com.daybreak.model.hibernate.dao.StatisticClassifyManager;

@Controller
@RequestMapping("/statistic/")
public class StatisticController {
	@Autowired 
	private StatisticActivityManager statActivityManager;
	
	@Autowired 
	private StatisticClassifyManager classifyManager;
	
	@RequestMapping(value ="activity/urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo activityUrlid(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {
		Date endMonth = new Date();
		Calendar cal = Calendar.getInstance();
    	cal.setTime(endMonth);
    	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-2);
    	Date startMonth = cal.getTime();
		return statActivityManager.getActivityChartData(urlID,startMonth,endMonth);
	}
	
	@RequestMapping(value ="classify/urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo classifyUrlid(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {

		return classifyManager.getClassifyChartData(urlID);
	}
}
