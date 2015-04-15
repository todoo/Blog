package cn.com.daybreak.blog.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.daybreak.blog.common.bean.FormErrorInfo;
import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.dao.UserDao;
import cn.com.daybreak.blog.model.entity.User;
import cn.com.daybreak.blog.service.UserManager;

@Service
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDao userDao;
	
	private Logger logger = Logger.getLogger(UserManagerImpl.class);
	
	@Override
	public ResultInfo getUser(int userID) {
		ResultInfo result = new ResultInfo(true);
		try {
			User user = userDao.queryByID(userID);
			if (user != null) {
				result.addData("user", user);
			} else {
				result.setSuccess(false);
				result.setMessage("用户不存在");
			}
			
			return result;
		} catch (Exception e) {
			logger.error("获取用户信息异常", e);
			result.setSuccess(false);
			result.setMessage("获取用户信息异常");
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getUser(String urlID) {
		logger.info("查询用户urlID=" + urlID);
		
		ResultInfo result = new ResultInfo(true);
		if (!userDao.isExistUserByUrlID(urlID)) {
			//用户不存在
			logger.info("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
		} else {
			User user = userDao.queryByUrlID(urlID);
			if (null == user) {
				logger.info("获取用户信息异常");
				result.setSuccess(false);
				result.setMessage("获取用户信息异常");
			} else {
				logger.info("获取用户信息成功");
				result.addData("user", user);
			}
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getUserByUserName(String userName) {
		logger.info("查询用户userName=" + userName);
		
		ResultInfo result = new ResultInfo(true);
		if (!userDao.isExistUserByUserName(userName)) {
			//用户不存在
			logger.info("用户不存在");
			result.setSuccess(false);
			result.setMessage("用户不存在");
		} else {
			User user = userDao.queryByUserName(userName);
			if (null == user) {
				logger.info("获取用户信息异常");
				result.setSuccess(false);
				result.setMessage("获取用户信息异常");
			} else {
				logger.info("获取用户信息成功");
				result.addData("user", user);
			}
		}
		
		return result;
	}

	@Transactional
	@Override
	public ResultInfo addUser(User user) {
		logger.info("新增用户" + user.toString());
		ResultInfo result = new ResultInfo(true);
		
		logger.info("用户信息验证");
		//urlID验证
		if (null == user.getUrlID()) {
			logger.error("urlID字段不能为null");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("urlID", "urlID不能为空"));
		} else if (user.getUrlID().length() == 0 || user.getUrlID().length() >100) {
			logger.error("urlID字段长度不能为0，且不能超过100");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("urlID", "urlID长度不能为0，且不能超过100"));
		} else if (!Pattern.matches("^[a-zA-Z0-9][a-zA-Z0-9]*[a-zA-Z0-9]$", user.getUrlID())) {
			//urlID只能包含数字或字符
			logger.error("urlID字段只能包含字母或数字");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("urlID", "urlID只能包含字母或数字"));
		} else {
			//检测urlID是否已经存在
			if (userDao.isExistUserByUrlID(user.getUrlID())) {
				logger.error("urlID已经存在");
				result.setSuccess(false);
				result.getErrors().add(new FormErrorInfo("urlID", "urlID已经存在"));
			}
		}
		
		//userName验证
		if (null == user.getUserName()) {
			logger.error("userName字段不能为null");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("userName", "用户名不能为空"));
			return null;
		} else if (user.getUserName().length() == 0 || user.getUserName().length() > 100) {
			logger.error("userName字段长度不能为0,且不能超过100");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("userName", "用户名长度不能为0,且不能超过100"));
		} else if (!Pattern.matches("^[a-zA-Z0-9_][a-zA-Z\\.0-9_]*@?[a-zA-Z\\.0-9_]*[a-zA-Z0-9_]$", user.getUserName())) {
			//userName只能包含数字、字符、下划线、.、_
			logger.error("userName只能包含数字、字符、下划线、.、_");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("userName", "用户名只能包含数字、字符、下划线、.、"));
		} else {
			//检测用户名是否存在
			if (userDao.isExistUserByUserName(user.getUserName())) {
				logger.error("userName已经存在");
				result.setSuccess(false);
				result.getErrors().add(new FormErrorInfo("userName", "用户名已经存在"));
			}
		}
		
		//password验证
		if (null == user.getPassword()) {
			logger.error("password字段不能为null");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("password", "密码不能为空"));
		} else if (user.getPassword().length() == 0 || user.getPassword().length() > 100) {
			logger.error("password字段长度不能为0，且不能超过100");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("password", "密码长度不能为0，且不能超过100"));
		} else if (!Pattern.matches("^[a-zA-Z0-9_][a-zA-Z0-9_]*[a-zA-Z0-9_]$", user.getPassword())) {
			//密码只能包含字母、数字、下划线
			logger.error("password字段只能包含字母、数字、下划线");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("password", "密码只能包含字母、数字、下划线"));
		}
		
		//昵称验证
		if (null != user.getNickName() && user.getNickName().length() > 20) {
			logger.error("nickName字段长度不能超过20");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("nickName", "昵称长度不能超过20"));
		} else if (null != user.getNickName() && !Pattern.matches("^[a-zA-Z0-9_][a-zA-Z0-9_]*[a-zA-Z0-9_]$", user.getNickName())) {
			//昵称只能包含字母、数字、下划线
			logger.error("nickName字段只能包含字母、数字、下划线");
			result.setSuccess(false);
			result.getErrors().add(new FormErrorInfo("nickName", "昵称只能包含字母、数字、下划线"));
		}
		
		//密码加密
		String passwordEncryptStr = this.passwordEncrypt(user.getPassword() + "{" + user.getUserName() + "}");
		if (null == passwordEncryptStr) {
			logger.error("用户密码加密异常");
			result.setSuccess(false);
			result.setMessage("新增用户信息异常");
		}
		user.setPassword(passwordEncryptStr);
		
		if (result.isSuccess()) {
			logger.info("用户信息验证通过，新增用户");
			userDao.add(user);
		}
		
		return result;
	}
	
	private String passwordEncrypt(String src) {
		//md5加密
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
    	byte[] bytes = md5.digest(src.getBytes());
    	StringBuffer dst = new StringBuffer();
    	for (byte b : bytes) {
    		int intValue = (int)b & 0xff;
    		String hexStr = Integer.toHexString(intValue);
    		if (hexStr.length()<2) {
    			hexStr = "0" + hexStr;
    		}
    		dst.append(hexStr);
    	}
    	
    	return dst.toString();
	}

}
