package cn.com.daybreak.client.blog.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.UserManager;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value ="urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo urlid(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {
		
		return userManager.getUser(urlID);
	}
}
