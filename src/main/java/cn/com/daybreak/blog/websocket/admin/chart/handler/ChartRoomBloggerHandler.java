package cn.com.daybreak.blog.websocket.admin.chart.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import cn.com.daybreak.blog.common.tools.UrlUtils;
import cn.com.daybreak.blog.websocket.bean.ChartMessage;
import cn.com.daybreak.blog.websocket.bean.ChartRoom;
import cn.com.daybreak.blog.websocket.bean.ChartRoomClient;
import cn.com.daybreak.blog.websocket.bean.ChartRoomList;

public class ChartRoomBloggerHandler extends TextWebSocketHandler{
	@Autowired
	private ChartRoomList chartRooms;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		//接收到客户端消息时调用
		//获取连接的唯一session id
		String sessionID = session.getId();
		//获取聊天室博主urlID
		String urlID = UrlUtils.getPathParamValue(session.getUri().getPath(), "urlid");	
		//获取发送消息的客户端
		ChartRoomClient chartRoomClient = chartRooms.getChartRoomClient(urlID, sessionID);
		if (null == chartRoomClient) {
			return ;
		}
		//获取发送者的昵称和ip
		String nickName = chartRoomClient.getNickName();
		String clientIp = chartRoomClient.getIp();

		System.out.println("handleTextMessage: " + session.getId() + "-" + nickName + "-" + message.getPayload());
		
		//向所有该聊天室中的客户端群发收到的消息		
		ChartRoom chartRoom = chartRooms.getChartRoom(urlID);
		List<ChartRoomClient> chartRoomClients = chartRoom.getChartRoomClients();
		
		//创建消息对象
		ChartMessage chartMessage = new ChartMessage();
		chartMessage.setMsgType(ChartMessage.MSG_TYPE_CHART);
		chartMessage.setCreateTime(new Date());
		chartMessage.setNickName(nickName);
		chartMessage.setIp(clientIp);
		chartMessage.setContent(message.getPayload());
		chartMessage.setBlogger(sessionID.equals(chartRoom.getSessionID()));
		chartMessage.setSessionID(sessionID);
		
		sendMessage(chartRoomClients, chartMessage);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// 与客户端完成连接后调用
		//获取连接的唯一session id
		String sessionID = session.getId();
		//获取昵称
		String nickName = UrlUtils.getPathParamValue(session.getUri().getPath(), "nickname");
		
		System.out.println("afterConnectionEstablished: " + sessionID + "-" + nickName);
		
		//获取客户端ip地址
		String clientIp = session.getRemoteAddress().getAddress().getHostAddress();
		
		//获取博主urlID
		String urlID = UrlUtils.getPathParamValue(session.getUri().getPath(), "urlid");
		
		//将已连接的socket客户端保存
		ChartRoomClient client = new ChartRoomClient();
		client.setNickName(nickName);
		client.setIp(clientIp);
		client.setSession(session);
		
		//判断此博主的聊天室是否已经存在
		if (chartRooms.isExistBloggerChartRoom(urlID)) {
			//聊天室已存在
			//保存客户端信息到聊天室中
			chartRooms.addChartClientToRoom(urlID, client);
			//设置聊天室博主sessionID
			chartRooms.getChartRoom(urlID).setSessionID(sessionID);
		} else {
			//聊天室不存在
			//新建聊天室
			ChartRoom chartRoom = new ChartRoom();
			chartRoom.setUrlID(urlID);
			chartRoom.setSessionID(sessionID);
			//将客户端添加到聊天室
			chartRoom.addChartRoomClient(client);
			//将聊天室添加到聊天室列表
			chartRooms.addChartRoom(chartRoom);
		}
		
		
		ChartRoom chartRoom = chartRooms.getChartRoom(urlID);
		List<ChartRoomClient> chartRoomClients = chartRoom.getChartRoomClients();
		//向所有已连接的客户端推送在线人数消息
		int clientCount = chartRoom.getChartRoomClientSize();
		//创建在线人数消息对象
		ChartMessage chartMessage = new ChartMessage();
		chartMessage.setMsgType(ChartMessage.MSG_TYPE_CLIENT_COUNT);
		chartMessage.setClientCount(clientCount);
		
		sendMessage(chartRoomClients, chartMessage);
		
		//向所有已连接的客户端推送博主上线状态消息
		//创建博主上线状态消息对象
		chartMessage = new ChartMessage();
		chartMessage.setMsgType(ChartMessage.MSG_TYPE_BLOGGER_STATUS);
		chartMessage.setBloggerStatus(1);
		sendMessage(chartRoomClients, chartMessage);		
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		// 消息传输出错时调用
		System.out.println("handleTransportError");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		// 一个客户端连接断开时关闭
		System.out.println("afterConnectionClosed");
		
		//获取连接的唯一session id
		String sessionID = session.getId();
		
		//获取博主urlID
		String urlID = UrlUtils.getPathParamValue(session.getUri().getPath(), "urlid");
		
		//从对应的聊天室删除该客户端
		chartRooms.removeChartClientFormRoom(urlID, sessionID);
		
		//向所有已连接的客户端推送在线人数消息
		ChartRoom chartRoom = chartRooms.getChartRoom(urlID);
		if (chartRoom != null) {
			List<ChartRoomClient> chartRoomClients = chartRoom.getChartRoomClients();
			int clientCount = chartRoom.getChartRoomClientSize();
			//创建消息对象
			ChartMessage chartMessage = new ChartMessage();
			chartMessage.setMsgType(ChartMessage.MSG_TYPE_CLIENT_COUNT);
			chartMessage.setClientCount(clientCount);
			
			sendMessage(chartRoomClients, chartMessage);
			
			//向所有已连接的客户端推送博主离线状态消息
			//创建博主上线状态消息对象
			chartMessage = new ChartMessage();
			chartMessage.setMsgType(ChartMessage.MSG_TYPE_BLOGGER_STATUS);
			chartMessage.setBloggerStatus(0);
			sendMessage(chartRoomClients, chartMessage);
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void sendMessage(List<ChartRoomClient> chartRoomClients, ChartMessage chartMessage) {
		TextMessage textMessage = null;
		try {
			textMessage = new TextMessage((JSONObject.fromObject(chartMessage).toString()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i=0; i<chartRoomClients.size(); ++i) {
			WebSocketSession clientSession = chartRoomClients.get(i).getSession();
			try {
				clientSession.sendMessage(textMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
