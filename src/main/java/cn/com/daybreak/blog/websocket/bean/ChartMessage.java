package cn.com.daybreak.blog.websocket.bean;

import java.util.Date;

public class ChartMessage {
	public final static String MSG_TYPE_CHART = "chart";//聊天消息
	public final static String MSG_TYPE_CLIENT_COUNT = "client_count";//在线人数消息
	public final static String MSG_TYPE_BLOGGER_STATUS = "blogger_status";//博主状态改变消息
	private Date createTime;
	private String nickName;
	private String ip;
	private boolean isBlogger;//是否是博主
	private String content;
	private String sessionID;//发送者sessionID
	private String msgType;//消息类型
	private int clientCount;//客户端数目
	private int bloggerStatus;
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public boolean isBlogger() {
		return isBlogger;
	}
	
	public void setBlogger(boolean isBlogger) {
		this.isBlogger = isBlogger;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public int getClientCount() {
		return clientCount;
	}

	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}

	public int getBloggerStatus() {
		return bloggerStatus;
	}

	public void setBloggerStatus(int bloggerStatus) {
		this.bloggerStatus = bloggerStatus;
	}
	
}
