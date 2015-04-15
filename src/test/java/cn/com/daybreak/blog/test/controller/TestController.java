package cn.com.daybreak.blog.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.UserManager;

@Controller
@RequestMapping("/test/")
public class TestController {
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value ="users/{userID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo query(@PathVariable int userID,HttpServletRequest req,HttpServletResponse resp) throws Exception {
		ResultInfo result = userManager.getUser(userID);
		return result;
	}
	
	@RequestMapping(value ="users",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo add(HttpServletRequest req,HttpServletResponse resp) {
		User user = new User();
		user.setUrlID("DayBreasak");
		user.setNickName("asdasd");
		user.setPassword("123sw$s");
		user.setUserName("asdasads.cim");
		ResultInfo result = null;
		try {
			result = userManager.addUser(user);
		} catch (RuntimeException e) {
			result = new ResultInfo(false);
			result.setMessage("新增用户失败");
		}
		return result;
	}
}
