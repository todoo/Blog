package cn.com.daybreak.admin.blog.backgroundmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.common.tools.SecurityUserInfoUtil;
import cn.com.daybreak.model.hibernate.dao.BackgroundMusicManager;
import cn.com.daybreak.model.hibernate.dao.UserManager;
import cn.com.daybreak.model.hibernate.entity.User;

@Controller("AdminBackgroundMusicController")
@RequestMapping("/admin/backgroundmusic")
public class BackgroundMusicController {
	@Autowired
	private BackgroundMusicManager backgroundMusicManager;
	
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value ={"","/"},method=RequestMethod.GET)
	@ResponseBody
	public ResultInfo urlid(HttpServletRequest req,HttpServletResponse resp) {
		String userName = SecurityUserInfoUtil.getUsername();
		ResultInfo userResult = userManager.getUserByUserName(userName);
		if (!userResult.isSuccess()) {
			ResultInfo result = new ResultInfo(false);
			result.setMessage(userResult.getMessage());
			return result;
		}
		return backgroundMusicManager.getRandomBackgroundMusic(((User)userResult.getData("user")).getUrlID());
	}
}
