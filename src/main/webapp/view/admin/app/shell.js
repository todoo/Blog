define(['plugins/router','plugins/http','knockout','jquery','webSocketChart','bootstrap'],function (router,http,ko,$,webSocketChart) {
	return {
		backgroundMusicUrl: ko.observable(),
		router: router,
		activate: function() {
			router.map([{route:'', hash:'#', title:'Home', moduleId:'home/home', nav:true},
			            {route:'article/add', hash:'#article/add', title:'Add', moduleId:'article/add', nav:true},
			            {route:'article/update(/category/:categoryID)', hash:'#article/update', title:'Update', moduleId:'article/update', nav:true}]).buildNavigationModel();
			router.on("router:route:activating", function(viewModel, routerItem, router) {
				var moduleId = routerItem.config.moduleId;
				if (moduleId.substr(0,7) == "article") {
					$(".article-dropdown-navbar").addClass("active");
				} else {
					$(".article-dropdown-navbar").removeClass("active");
				}
			}, router);
			router.activate();
			
			var self = this;
			http.get(ROOT_URL + "admin/backgroundmusic").then(function(data) {
				if (data.success) {
					self.backgroundMusicUrl(MEDIA_ROOT_URL + data.data.musicPath);
				}
				
			}, function(error) {
				
			});
			
			webSocketChart.init({sockjsAddr: CHART_ROOM_WEBSOCKET_ADDR});
		}
	};
	
});