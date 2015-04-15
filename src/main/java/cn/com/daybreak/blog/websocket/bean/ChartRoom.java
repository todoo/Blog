package cn.com.daybreak.blog.websocket.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChartRoom {
	private String urlID;//博主urlID
	private String sessionID;//博主sessionID
	private Map<String,ChartRoomClient> clients = new HashMap<String,ChartRoomClient>();
	
	public ChartRoom() {
		this.urlID = null;
		this.sessionID = null;//博主不在聊天室时为null
	}
	
	public String getUrlID() {
		return urlID;
	}
	
	public void setUrlID(String urlID) {
		this.urlID = urlID;
	}
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	/**
	 * 添加客户端
	 * @param client
	 */
	public void addChartRoomClient(ChartRoomClient client) {
		this.clients.put(client.getSession().getId(), client);
	}
	
	/**
	 * 删除一个客户端，若为博主则将sessionID设为null
	 * @param sessionID
	 * @return
	 */
	public boolean removeChartRoomClient(String sessionID) {
		if (sessionID.equals(this.sessionID)) {
			//博主退出聊天室
			this.sessionID = null;
		}
		
		if (null == this.clients.remove(sessionID)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 获取聊天室客户端个数
	 * @return
	 */
	public int getChartRoomClientSize() {
		return this.clients.size();
	}
	
	/**
	 * 获取指定的客户端
	 * @param sessionID
	 * @return
	 */
	public ChartRoomClient getChartRoomClient(String sessionID) {
		return this.clients.get(sessionID);
	}
	
	/**
	 * 获取所有客户端列表
	 * @return
	 */
	public List<ChartRoomClient> getChartRoomClients() {
		return new ArrayList<ChartRoomClient>(this.clients.values());
	}
	
	public boolean isBloggerOnline() {
		if (this.sessionID != null) 
			return true;
		return false;
	}
}
