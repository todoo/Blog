package cn.com.daybreak.blog.controller.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.UserManager;

@Controller
public class IndexController {
	@Autowired
	private UserManager userManager;
	@RequestMapping(value ="/",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp) {
		return new ModelAndView("redirect:/index/daybreak");
	}
	
	@RequestMapping(value ="/index/{urlID}",method=RequestMethod.GET)
	public ModelAndView index(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("client/index");
		
		ResultInfo result = userManager.getUser(urlID);
		if (result.isSuccess()) {
			User user = (User) result.getData("user");
			user.setPassword(null);
			mv.addObject("user",user);
			mv.addObject("blog",user.getBlog());
		} else {
			mv.addObject("user",null);
			mv.addObject("blog",null);
		}
		
		return mv; 
	}
}
