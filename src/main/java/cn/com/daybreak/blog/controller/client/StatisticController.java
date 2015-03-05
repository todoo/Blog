package cn.com.daybreak.blog.controller.client;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.service.StatisticActivityManager;
import cn.com.daybreak.blog.service.StatisticClassifyManager;

@Controller
@RequestMapping("/statistic/")
public class StatisticController {
	@Autowired 
	private StatisticActivityManager statActivityManager;
	
	@Autowired 
	private StatisticClassifyManager classifyManager;
	
	@Autowired
	private EhCacheCacheManager cacheManager; 
	
	@RequestMapping(value ="activity/urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo activityUrlid(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {
		Date endMonth = new Date();
		Calendar cal = Calendar.getInstance();
    	cal.setTime(endMonth);
    	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-2);
    	Date startMonth = cal.getTime();
    	
//    	CacheManager manager = cacheManager.getCacheManager();
//    	String[] cacheNames = manager.getCacheNames();
//    	System.out.println("+++++++++++++++caches+++++++++++++++++++++");
//    	for (int i=0; i<cacheNames.length; ++i) {
//    		System.out.println("-----cacheName : " + cacheNames[i]);
//    		Cache cache = manager.getCache(cacheNames[i]);
//    		List<Object> keys = cache.getKeys();
//    		for (Object key : keys) {
//    			System.out.println(key + " : " + cache.get(key).getObjectValue());
//    		}
//    	}
		return statActivityManager.getActivityChartData(urlID,startMonth,endMonth);
	}
	
	@RequestMapping(value ="classify/urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo classifyUrlid(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {

		return classifyManager.getClassifyChartData(urlID);
	}
}
