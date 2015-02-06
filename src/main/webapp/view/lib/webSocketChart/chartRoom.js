define(['jquery','sockjs'], function($){
	var chartRoom = function() {
		var self = this;
		this._opt = {};
		this._html = '<div style="display:none;" class="clearfix chart-room">'+
				        '<div class="text-center chart-room-float-btn">聊<br/>天<br/>室</div>'+
				        '<div class="panel panel-success chart-room-float-dialog">'+
				        	'<div class="panel-heading">'+
				    			'<div>'+
				    				'<span style="margin: 0 5px 0 0;" class="badge chart-room-client-status-disconnect" id="chartRoomBloggerStatusBadge">博主状态</span><span style="margin: 0 5px 0 0;" id="chartRoomBloggerStatus">离线</span>'+
				    				'<span style="margin: 0 5px 0 0;" class="badge chart-room-client-status-disconnect" id="chartRoomClientCountBadge">在线人数</span><span style="margin: 0 5px 0 0;" id="chartRoomClientCount">0</span>'+
				    			'</div>'+
				    			'<div>'+
				    				'<span style="margin: 0 5px 0 0;" class="badge chart-room-client-status-disconnect" id="chartRoomClientStatusBadge">我的状态</span><span style="margin: 0 5px 0 0;" id="chartRoomClientStatus" >未连接</span>'+
				    			'</div>'+
				  			'</div>'+
						  	'<div class="panel-body" style="padding:1px;">'+
						  		'<div class="form-control chart-room-float-dialog-content" id="chartRoomContent"></div>'+
						  	'</div>'+
						  	'<div class="panel-footer" style="padding:1px 1px">'+
							  	'<div style="margin-bottom:1px;" class="input-group">'+
								  '<span class="input-group-addon">昵称</span>'+
								  '<input type="text" class="form-control" maxlength="10" id="chartRoomClientNickName"/>'+
								  '<span style="color:white;" class="input-group-addon btn btn-primary" disabled id="chartRoomConnect">连接</span>'+
								  '<span style="color:white;display:none;" class="input-group-addon btn btn-primary" id="chartRoomDisConnect">断开</span>'+
								'</div>'+
						  		'<div class="input-group">'+
						  			'<textarea rows="2" cols=""  style="resize:none"  class="form-control" id="chartRoomClientMessage" maxlength="100"></textarea>'+
								  	'<span style="color:white;" class="input-group-addon btn btn-primary" disabled id="chartRoomClientSend">发送</span>'+
								'</div>'+
						  	'</div>'+
						'</div>'+
					'</div>';
		this._chartRoomShowFlag = false;
		this._webSocketClient = null;

		/**
		 * 初始化聊天室
		 * opt = {
		 * 		width: int 聊天框宽度 300
		 * 		floatBtnWidth: int 侧边显示或隐藏的按钮宽度 50
		 * 		bottom: int 距浏览器底端的距离 0
		 * 		right: int 距浏览器右端的距离 0
		 * 		sockjsAddr: string sockjs连接WebSocket地址，连接时会自动在后面加参数/nickname/{nickName}
		 * 		sockjsOnOpen: function websocket生命周期函数onopen
		 * 		sockjsOnMessage: function websocket生命周期函数onmessage
		 * 		sockjsOnClose: function websocket生命周期函数onclose
		 * }
		 */
		this.init = function(opt) {
			self._opt = {
					width: 300,
					floatBtnWidth: 50,
					bottom: 0,
					right: 0,
					sockjsAddr: "",
					sockjsOnOpen: self._onopen,
					sockjsOnMessage: self._onmessage,
					sockjsOnClose: self._onClose
			}
			if (typeof opt == 'undefined' ) {
				return false;
			} else {
				if (typeof opt.width == 'number') {
					self._opt.width = opt.width;
				}
				if (typeof opt.floatBtnWidth == 'number') {
					self._opt.floatBtnWidth = opt.floatBtnWidth;
				}
				if (typeof opt.bottom == 'number') {
					self._opt.bottom = opt.bottom;
				}
				if (typeof opt.right == 'number') {
					self._opt.right = opt.right;
				}
				if (typeof opt.sockjsAddr != 'string') {
					return false;
				} else {
					self._opt.sockjsAddr = opt.sockjsAddr;
				}
			}
			
			self._webSocketClient = null;
			//检测浏览器是否支持websocket
			if (typeof WebSocket == 'undefined') {
				return false;
			}
			
			//将html加入文档，并初始化样式
			$("body").append(self._html);
			$(".chart-room").css({
				width: self._opt.width + self._opt.floatBtnWidth + "px",
				right: -self._opt.width - 5 + "px",
				bottom: self._opt.bottom + "px",
				display: "table"
			});
			$(".chart-room-float-btn").css({
				width: self._opt.floatBtnWidth + "px",
			});
			$(".chart-room-float-dialog").css({
				width: self._opt.width + "px",
			});
			
			//设置事件
			self._bindUI();
			
			return true;
		};
		
		this._bindUI = function() {
			//聊天框的出现与隐藏
			self._chartRoomShowFlag = false;
			$(".chart-room-float-btn").click(function() {
				if (self._chartRoomShowFlag) {
					//隐藏聊天框
					$(".chart-room-float-btn").attr("disabled", "disabled");
					$(".chart-room").animate({ 
						right: -self._opt.width - 5 + "px"
					  }, 500 , function() {
						  self._chartRoomShowFlag = false;
						  $(".chart-room-float-btn").removeAttr("disabled");
					  });
				} else {
					//显示聊天框
					$(".chart-room-float-btn").attr("disabled", "disabled");
					$(".chart-room").animate({ 
						right: 0
					  }, 500 , function() {
						  self._chartRoomShowFlag = true;
						  $(".chart-room-float-btn").removeAttr("disabled");
					  });
				}
			});
			
			//昵称输入事件，控制连接按钮
			$("#chartRoomClientNickName").keyup(function(event) {
				if ($.trim($(this).val()).length>0) {
					$("#chartRoomConnect").removeAttr("disabled");
				} else {
					$("#chartRoomConnect").attr("disabled", "disabled");
				}
			});
			
			//连接websocket
			$("#chartRoomConnect").click(function(event) {
				if (null == self._webSocketClient) {
					var nickName = $.trim($("#chartRoomClientNickName").val());
	    			if (nickName.length <= 0) {
	    				return ;
	    			} 
	    			$("#chartRoomClientStatusBadge").removeClass("chart-room-client-status-disconnect").addClass("chart-room-client-status-connectting");
	    			$("#chartRoomClientStatus").html("正在连接中...");
	    			self._webSocketClient = new SockJS(self._opt.sockjsAddr + "/nickname/" + nickName);
	    			self._webSocketClient.onopen = self._opt.sockjsOnOpen;
	    			self._webSocketClient.onmessage = self._opt.sockjsOnMessage;
	    			self._webSocketClient.onclose = self._opt.sockjsOnClose;
				}
			});
			
			//断开websocket
			$("#chartRoomDisConnect").click(function(event) {
				if (self._webSocketClient != null) {
					self._webSocketClient.close();
					self._webSocketClient = null;
					//昵称输入启用
					$("#chartRoomClientNickName").removeAttr("disabled");
					//显示连接,隐藏断开按钮
					$("#chartRoomConnect").css("display", "");
					$("#chartRoomDisConnect").css("display", "none");
					//修改用户状态
					$("#chartRoomClientStatusBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
					$("#chartRoomClientStatus").html("未连接");
					$("#chartRoomClientCountBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
					$("#chartRoomBloggerStatusBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
					$("#chartRoomBloggerStatus").html("离线");
				}
			});
			
			//消息输入框输入，控制发送按钮
			$("#chartRoomClientMessage").keyup(function(event) {
				if ($.trim($(this).val()).length>0) {
					$("#chartRoomClientSend").removeAttr("disabled");
				} else {
					$("#chartRoomClientSend").attr("disabled", "disabled");
				}
			});
			
			//发送消息
			$("#chartRoomClientSend").click(function(event) {
				if (self._webSocketClient != null) {
					var message = $.trim($("#chartRoomClientMessage").val());
	    			if (message.length <= 0) {
	    				return ;
	    			} 
	    			try {
		    			var isSendSuccess = self._webSocketClient.send(message);
        				if (!isSendSuccess) {
        					alert("发送失败");
        				} else {
        					$("#chartRoomClientMessage").val("");
        					$("#chartRoomClientMessage").trigger("keyup");
        				}
        			} catch (e) {
        				alert("发送失败");
        			}
				}
			});
		};
		
		this._onopen = function() {
			console.log("onopen");
			//websocket连接成功
			//昵称输入禁用
			$("#chartRoomClientNickName").attr("disabled", "disabled");
			//隐藏连接,显示断开按钮
			$("#chartRoomConnect").css("display", "none");
			$("#chartRoomDisConnect").css("display", "");
			//修改用户状态
			$("#chartRoomClientStatusBadge").removeClass("chart-room-client-status-connectting").addClass("chart-room-client-status-connectted");
			$("#chartRoomClientStatus").html("已连接");
			$("#chartRoomClientCountBadge").removeClass("chart-room-client-status-disconnect").addClass("chart-room-client-status-connectted");
		};
		
		this._onmessage = function(e) {
			console.log("onmessage" + e.data);
			var messageObj = $.parseJSON(e.data);
			switch(messageObj.msgType) {
			case "chart":
				var chartContent = $("#chartRoomContent").html();
				if (chartContent.length > 0) {
					chartContent += "\n";
				}
		
				chartContent += self._createChartRoomContentHtml(e.data);
				$("#chartRoomContent").html(chartContent);
				$('#chartRoomContent').scrollTop( $('#chartRoomContent')[0].scrollHeight );
				break;
			case "client_count":
				//设置在线人数消息
				$("#chartRoomClientCount").html(messageObj.clientCount);
				break;
			case "blogger_status":
				//设置博主状态
				if (messageObj.bloggerStatus == 0) {
					$("#chartRoomBloggerStatusBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
					$("#chartRoomBloggerStatus").html("离线");
				} else {
					$("#chartRoomBloggerStatusBadge").removeClass("chart-room-client-status-disconnect").addClass("chart-room-client-status-connectted");
					$("#chartRoomBloggerStatus").html("在线");
				}
			}
		};
		
		this._onclose = function() {
			console.log("onclose");
			self._webSocketClient = null;
			//昵称输入启用
			$("#chartRoomClientNickName").removeAttr("disabled");
			//显示连接,隐藏断开按钮
			$("#chartRoomConnect").css("display", "");
			$("#chartRoomDisConnect").css("display", "none");
			//修改用户状态
			$("#chartRoomClientStatusBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
			$("#chartRoomClientStatus").html("未连接");
			$("#chartRoomClientCountBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
			$("#chartRoomBloggerStatusBadge").removeClass("chart-room-client-status-connectted").addClass("chart-room-client-status-disconnect");
			$("#chartRoomBloggerStatus").html("离线");
		};
		
		this._timestampToDateStr = function(timestamp) {
			var date = new Date();
			date.setTime(timestamp);
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			if (month<10) {
				month = "0" + month;
			}
			var day = date.getDate();
			if (day<10) {
				day = "0" + day;
			}
			
			return year + "-" + month + "-" + day; 
		}
		
		this._createChartRoomContentHtml = function(dataJson) {
			var messageObj = $.parseJSON(dataJson);
			var url = self._webSocketClient._transport.url;
			var paths = url.split("/");
			var sessionID = paths[paths.length-2];
			var messageHtml = null;
			if (messageObj.blogger) {
				//博主发送的消息
				messageHtml = '<div class="panel panel-danger">'+
									'<div style="padding:5px;" class="panel-heading">'+
										'<h3 class="panel-title "><i class="icon-user text-danger"></i>&nbsp;&nbsp;<span class="text-danger">' + messageObj.nickName + '(' + messageObj.ip + ')</span></h3>'+
										'<h3 class="panel-title"><i class="icon-time text-danger"></i><small>&nbsp;&nbsp;<span class="text-danger">' + self._timestampToDateStr(messageObj.createTime.time) + ' ' + messageObj.createTime.hours + ':' + messageObj.createTime.minutes + ':' + messageObj.createTime.seconds + '</span></small></h3>'+
									'</div>'+
									'<div style="padding:5px;" class="panel-body text-danger">'+
										'<p>' + messageObj.content.replace(/[\r\n]/g,"<br/>").replace(/\s/g,"&nbsp;") + '</p>'+ 
									'</div>'+
								'</div>';		
			} else if (sessionID == messageObj.sessionID) {
				//本人发送的消息
				messageHtml = '<div class="panel panel-primary">'+
								'<div style="padding:5px;" class="panel-heading">'+
									'<h3 class="panel-title "><i class="icon-user"></i>&nbsp;&nbsp;<span>' + messageObj.nickName + '(' + messageObj.ip + ')</span></h3>'+
									'<h3 class="panel-title"><i class="icon-time"></i><small>&nbsp;&nbsp;<span style="color:white;">' + self._timestampToDateStr(messageObj.createTime.time) + ' ' + messageObj.createTime.hours + ':' + messageObj.createTime.minutes + ':' + messageObj.createTime.seconds + '</span></small></h3>'+
								'</div>'+
								'<div style="padding:5px;" class="panel-body text-primary">'+
									'<p>' + messageObj.content.replace(/[\r\n]/g,"<br/>").replace(/\s/g,"&nbsp;") + '</p>'+ 
								'</div>'+
							'</div>';	
			} else {
				//本人发送的消息
				messageHtml = '<div class="panel panel-default">'+
								'<div style="padding:5px;" class="panel-heading">'+
									'<h3 class="panel-title "><i class="icon-user text-muted"></i>&nbsp;&nbsp;<span class="text-muted">' + messageObj.nickName + '(' + messageObj.ip + ')</span></h3>'+
									'<h3 class="panel-title"><i class="icon-time text-muted"></i><small>&nbsp;&nbsp;<span class="text-muted">' + self._timestampToDateStr(messageObj.createTime.time) + ' ' + messageObj.createTime.hours + ':' + messageObj.createTime.minutes + ':' + messageObj.createTime.seconds + '</span></small></h3>'+
								'</div>'+
								'<div style="padding:5px;" class="panel-body text-muted">'+
									'<p>' + messageObj.content.replace(/[\r\n]/g,"<br/>").replace(/\s/g,"&nbsp;") + '</p>'+ 
								'</div>'+
							'</div>';	
			}
			return messageHtml;
		}
	};
	
	return new chartRoom();
});