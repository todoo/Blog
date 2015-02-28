package cn.com.daybreak.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/")
public class LoginController {
	
	@RequestMapping(value ="login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/login");
		return mv;
	}
}
