package cn.com.daybreak.blog.websocket.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChartRoomList {
	private List<ChartRoom> chartRooms = new ArrayList<ChartRoom>();
	
	/**
	 * 判断某个博主的聊天室是否已经建立
	 * @param urlID
	 * @return
	 */
	public boolean isExistBloggerChartRoom(String urlID) {
		for(int i=0; i<chartRooms.size(); ++i) {
			if (chartRooms.get(i).getUrlID().equals(urlID)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 添加客户端到指定的聊天室
	 * @param urlID
	 * @param client
	 */
	public boolean addChartClientToRoom(String urlID, ChartRoomClient client) {
		//获取博主urlID对应的聊天室
		ChartRoom room = null;
		for(int i=0; i<chartRooms.size(); ++i) {
			if (chartRooms.get(i).getUrlID().equals(urlID)) {
				room = chartRooms.get(i);
				break;
			}
		}
		if (null == room) {
			return false;
		}
		
		room.addChartRoomClient(client);
		return true;
	}
	
	/**
	 * 从指定的聊天室删除一个客户端，当聊天室客户端个数为0时，删除该聊天室
	 * @param urlID
	 * @param sessionID
	 */
	public boolean removeChartClientFormRoom(String urlID, String sessionID) {
		//获取博主urlID对应的聊天室
		ChartRoom room = null;
		int index = -1;
		for(int i=0; i<chartRooms.size(); ++i) {
			if (chartRooms.get(i).getUrlID().equals(urlID)) {
				room = chartRooms.get(i);
				index = i;
				break;
			}
		}
		if (null == room) {
			return false;
		}
		
		boolean result = room.removeChartRoomClient(sessionID);
		if (result) {
			if (room.getChartRoomClientSize() <=0) {
				//聊天室客户端个数为0时，删除该聊天室
				this.chartRooms.remove(index);
			}
		}
		
		return result;
	}
	
	/**
	 * 添加一个聊天室
	 * @param chartRoom
	 */
	public void addChartRoom(ChartRoom chartRoom) {
		chartRooms.add(chartRoom);
	}
	
	/**
	 * 获取指定聊天室的指定sessionID的客户端
	 * @param urlID
	 * @param sessionID
	 * @return
	 */
	public ChartRoomClient getChartRoomClient(String urlID, String sessionID) {
		//获取博主urlID对应的聊天室
		ChartRoom room = null;
		for(int i=0; i<chartRooms.size(); ++i) {
			if (chartRooms.get(i).getUrlID().equals(urlID)) {
				room = chartRooms.get(i);
				break;
			}
		}
		if (null == room) {
			return null;
		}
		
		return room.getChartRoomClient(sessionID);
	}
	
	/**
	 * 获取指定的聊天室
	 * @param urlID
	 * @return
	 */
	public ChartRoom getChartRoom(String urlID) {
		//获取博主urlID对应的聊天室
		ChartRoom room = null;
		for(int i=0; i<chartRooms.size(); ++i) {
			if (chartRooms.get(i).getUrlID().equals(urlID)) {
				room = chartRooms.get(i);
				break;
			}
		}
		if (null == room) {
			return null;
		}
		return room;
	}
}
