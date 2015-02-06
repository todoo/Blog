package cn.com.daybreak.admin.blog.index.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.common.tools.SecurityUserInfoUtil;
import cn.com.daybreak.model.hibernate.dao.UserManager;
import cn.com.daybreak.model.hibernate.entity.User;

@Controller("AdminIndexController")
@RequestMapping("/admin")
public class IndexController {
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value ="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/index");
		
		String userName = SecurityUserInfoUtil.getUsername();
		ResultInfo userResult = userManager.getUserByUserName(userName);
		if (userResult.isSuccess()) {
			mv.addObject("user", (User)userResult.getData("user"));
		}
		
		return mv;
	}
}
