package cn.com.daybreak.admin.blog.ueditor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.common.tools.MediaUtils;
import cn.com.daybreak.common.tools.SecurityUserInfoUtil;
import cn.com.daybreak.model.hibernate.dao.DicMediaTypeManager;
import cn.com.daybreak.model.hibernate.dao.MediaManager;
import cn.com.daybreak.model.hibernate.dao.UserManager;
import cn.com.daybreak.model.hibernate.entity.DicMediaType;
import cn.com.daybreak.model.hibernate.entity.Media;
import cn.com.daybreak.model.hibernate.entity.User;

@Controller("AdminUEditorController")
@RequestMapping("/admin/ueditor")
public class UEditorController {
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private DicMediaTypeManager dicMediaTypeManager;
	
	@Autowired
	private MediaManager mediaManager;
	
	@RequestMapping(value ="/controller",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> config(HttpServletRequest req,HttpServletResponse resp){
		String action = ServletRequestUtils.getStringParameter(req, "action", null);
		if (action == null) {
			return null;
		}
		Map<String,Object> result = new HashMap<String,Object>();
		//result.put("serverUrl", "");
		//result.put("imageUrl", "http://127.0.0.1:8181/blog/admin/ueditor/uploadimage");
		result.put("imageActionName", "uploadimage");
		result.put("imageFieldName", "upfile");
		result.put("imageMaxSize", 2048000);
		result.put("imageUrlPrefix", "");
		String[] imageType = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
		result.put("imageAllowFiles", imageType);
		
		result.put("fileActionName", "uploadfile");
		result.put("fileFieldName", "upfile");
		result.put("fileMaxSize", 51200000);
		String[] fileType = {".png", ".jpg", ".jpeg", ".gif", ".bmp",
			    ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
			    ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
			    ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
			    ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"};
		result.put("fileAllowFiles", fileType);
		
		return result;
	}
	
	@RequestMapping(value ="/controller",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadimage(HttpServletRequest req, HttpServletResponse resp, @RequestParam(value="upfile", required=true)MultipartFile media){
		String userName = SecurityUserInfoUtil.getUsername();
		String action = ServletRequestUtils.getStringParameter(req, "action", null);
		if (action == null) {
			return null;
		}
	
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {			
			InputStream in = media.getInputStream();
			
			//获取登陆用户的资源文件夹根路径
			ResultInfo userResult = userManager.getUserByUserName(userName);
			if (!userResult.isSuccess()) {
				throw new Exception();
			}
			User user = (User) userResult.getData("user");
			String path = "user/" + user.getUserID() + "/";
			
			//获取媒体对象的唯一名称
			String orignalMediaName = media.getOriginalFilename();
			String mediaName = MediaUtils.getUniqueMediaName() + orignalMediaName.substring(orignalMediaName.lastIndexOf('.'));
			
			//将媒体对象流写入服务器磁盘
			if (action.equalsIgnoreCase("uploadimage")) {
				path += "image/";
				if (!MediaUtils.writeMediaToLocalPath(in, path, mediaName)) {
					throw new Exception();
				}
			}
			
			//将媒体对象保存入媒体表中
			Media mediaObject = new Media();
			mediaObject.setUser(user);
			
			ResultInfo mediaTypeResult = dicMediaTypeManager.getMediaType(DicMediaType.MEDIA_TYPE_NAME_IMAGE);
			if (!mediaTypeResult.isSuccess()) {
				throw new Exception();
			}
			mediaObject.setMediaType((DicMediaType)mediaTypeResult.getData("mediaType"));
			mediaObject.setMediaPath(path + mediaName);
			
			ResultInfo mediaResult = mediaManager.addMedia(mediaObject);
			if (!mediaResult.isSuccess()) {
				throw new Exception();
			}
			
			//按照UEditor规范，返回json
			//获取服务器根路径
			String contextPath = req.getContextPath();
			String rootUrl = contextPath;
			
			if (action.equalsIgnoreCase("uploadimage")) {
				result.put("state", "SUCCESS");
				result.put("url", rootUrl + "/resources/" + path + mediaName);
				result.put("title", orignalMediaName);
				result.put("original", orignalMediaName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("state", "上传异常,请重试");
		}
		
		return result;
	}
}
