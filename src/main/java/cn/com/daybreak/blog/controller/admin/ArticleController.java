package cn.com.daybreak.blog.controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.common.tools.SecurityUserInfoUtil;
import cn.com.daybreak.blog.model.entity.Article;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.ArticleManager;
import cn.com.daybreak.blog.service.UserManager;

@Controller("AdminArticleController")
@RequestMapping("/admin/article")
public class ArticleController {
	@Autowired
	private ArticleManager articleManager;
	
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value ="/add",method=RequestMethod.POST)
	@ResponseBody
	public ResultInfo addarticle(HttpServletRequest req, HttpServletResponse resp, @RequestBody String postData) {
		String userName = SecurityUserInfoUtil.getUsername();
		JSONObject articleJson = JSONObject.fromObject(postData);
		int categoryID = articleJson.getInt("categoryID");
		Article article = new Article();
		article.setArticleTitle(articleJson.getString("articleTitle"));
		article.setArticleBrief(articleJson.getString("articleBrief").replaceAll("\\n", "<br/>"));
		article.setArticleContent(articleJson.getString("articleContent"));

		return articleManager.addArticleByUserName(userName, categoryID, article);
	}
	
	@RequestMapping(value ="/getarticles/categoryid/{categoryID}",method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo getarticles(@PathVariable int categoryID, HttpServletRequest req, HttpServletResponse resp) {
		String userName = SecurityUserInfoUtil.getUsername();
		ResultInfo userResult = userManager.getUserByUserName(userName);
		if (!userResult.isSuccess()) {
			ResultInfo result = new ResultInfo(false);
			result.setMessage(userResult.getMessage());
			return result;
		}

		return articleManager.getArticles(((User)userResult.getData("user")).getUrlID(), categoryID);
	}
	
	@RequestMapping(value ="/delete/articleid/{articleID}",method=RequestMethod.DELETE)
	@ResponseBody
	public ResultInfo deletearticle(@PathVariable int articleID, HttpServletRequest req, HttpServletResponse resp) {
		String userName = SecurityUserInfoUtil.getUsername();
		
		return articleManager.deleteArticleByUserNameAndArticleID(userName, articleID);
	}
	
	@RequestMapping(value ="/update",method=RequestMethod.PUT)
	@ResponseBody
	public ResultInfo updatearticle(HttpServletRequest req, HttpServletResponse resp, @RequestBody String putData) {
		String userName = SecurityUserInfoUtil.getUsername();
		JSONObject articleJson = JSONObject.fromObject(putData);
		Article article = new Article();
		article.setArticleID(articleJson.getInt("articleID"));
		article.setArticleTitle(articleJson.getString("articleTitle"));
		article.setArticleBrief(articleJson.getString("articleBrief").replaceAll("\\n", "<br/>"));
		article.setArticleContent(articleJson.getString("articleContent"));

		return articleManager.updateArticleByUserName(userName, article);
	}
}
