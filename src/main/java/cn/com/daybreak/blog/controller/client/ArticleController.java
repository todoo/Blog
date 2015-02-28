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
import cn.com.daybreak.blog.service.ArticleManager;

@Controller
@RequestMapping("/article/")
public class ArticleController {
	@Autowired
	private ArticleManager articleManager;
	
	@RequestMapping(value ="getarticles/urlid/{urlID}/categoryid/{categoryID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo getarticles(@PathVariable String urlID,@PathVariable int categoryID,HttpServletRequest req,HttpServletResponse resp) {
		
		return articleManager.getArticles(urlID, categoryID);
	}
}
