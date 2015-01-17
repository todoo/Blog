package cn.com.daybreak.admin.blog.index.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

	@RequestMapping(value ="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/index");
		
		return mv;
	}
}
