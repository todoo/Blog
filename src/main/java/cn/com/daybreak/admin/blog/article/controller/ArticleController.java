package cn.com.daybreak.admin.blog.article.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.common.tools.SecurityUserInfoUtil;
import cn.com.daybreak.model.hibernate.dao.ArticleManager;
import cn.com.daybreak.model.hibernate.entity.Article;

@Controller("AdminArticleController")
@RequestMapping("/admin/article")
public class ArticleController {
	@Autowired
	private ArticleManager articleManager;
	
	@RequestMapping(value ="/add",method=RequestMethod.POST)
	@ResponseBody
	public ResultInfo getarticles(HttpServletRequest req,HttpServletResponse resp, @RequestBody String postData){
		String userName = SecurityUserInfoUtil.getUsername();
		JSONObject articleJson = JSONObject.fromObject(postData);
		int categoryID = articleJson.getInt("categoryID");
		Article article = new Article();
		article.setArticleTitle(articleJson.getString("articleTitle"));
		article.setArticleBrief(articleJson.getString("articleBrief").replaceAll("\\n", "<br/>"));
		article.setArticleContent(articleJson.getString("articleContent"));

		return articleManager.addArticleByUserName(userName, categoryID, article);
	}
}
