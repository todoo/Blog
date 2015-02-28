package cn.com.daybreak.blog.controller.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.service.CategoryManager;

@Controller
@RequestMapping("/category/")
public class CategoryController {
	@Autowired
	private CategoryManager categoryManager;
	
	@RequestMapping(value ="/getrootcategorylist/urlid/{urlID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo getrootcategorylist(@PathVariable String urlID,HttpServletRequest req,HttpServletResponse resp) {
		
		return  categoryManager.getCategorys(urlID, 0);
	}
	
}
