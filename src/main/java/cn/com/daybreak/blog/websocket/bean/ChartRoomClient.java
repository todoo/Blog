package cn.com.daybreak.blog.websocket.bean;

import org.springframework.web.socket.WebSocketSession;

public class ChartRoomClient {
	private String nickName;
	private String ip;
	private WebSocketSession session;
	
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
	
	public WebSocketSession getSession() {
		return session;
	}
	
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
}
