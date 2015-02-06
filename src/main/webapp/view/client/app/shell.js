define(['plugins/router','plugins/http','knockout','jquery','webSocketChart','bootstrap'],function (router,http,ko,$,webSocketChart) {
	return {
		backgroundMusicUrl: ko.observable(),
		router: router,
		activate: function(){
			router.map([{route:'',hash:'#',title:'Home',moduleId:'home/home',nav:true},
			            {route:'articles(/category/:categoryID)',hash:'#articles',title:'Article',moduleId:'articles/articles',nav:true}]).buildNavigationModel();

			router.activate();
			
			var self = this;
			http.get(ROOT_URL + "backgroundmusic/urlid/" + USER_URL_ID).then(function(data){
				if (data.success) {
					self.backgroundMusicUrl(MEDIA_ROOT_URL + data.data.musicPath);
				}
				
			},function(error){
				
			});
			
			webSocketChart.init({sockjsAddr: CHART_ROOM_WEBSOCKET_ADDR});
		}
	};
	
});