define(['plugins/router','plugins/http','knockout','jquery','bootstrap'],function (router,http,ko,$) {
	return {
		backgroundMusicUrl: ko.observable(),
		router: router,
		activate: function(){
			router.map([{route:'',hash:'#',title:'Home',moduleId:'home/home',nav:true},
			            {route:'article',hash:'#article',title:'Article',moduleId:'article/article',nav:true}]).buildNavigationModel();

			router.activate();
			
			var self = this;
			http.get(ROOT_URL + "admin/backgroundmusic").then(function(data){
				if (data.success) {
					self.backgroundMusicUrl(MEDIA_ROOT_URL + data.data.musicPath);
				}
				
			},function(error){
				
			});
		}
	};
	
});