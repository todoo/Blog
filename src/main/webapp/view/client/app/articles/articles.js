    define(['jquery','durandal/composition','plugins/router', 'knockout', 'plugins/http', 'tools/DateUtils','ueditor/ueditor.all','ueditor/ueditor.config','ueditor/ueditor.parse'], 
    		function($,composition,router, ko, http, dateUtils,UE,UEDITOR_CONFIG,uParse) {
    	
    	var viewModel = function(){
    		var self = this;
    		this.categoties = ko.observable();
    		this.articles = ko.observable();
    		this.selectedArticle = ko.observable();
    		this.articleListScrollTop = 0;
    		this.windowScrollTop = 0;
    		this.showArticle = function(data,event){
    			self.articleListScrollTop = $(document).scrollTop();
    			self.windowScrollTop = 0;
    			self.selectedArticle(data);
    		};
    		this.hideArticle = function() {
    			self.windowScrollTop = self.articleListScrollTop;
    			self.selectedArticle({articleID:-1,articleTitle:'',articleBrief:'',articleContent:'',updateTime:''});
    		};
    		
        	composition.addBindingHandler('articleContent',{
    	        init:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        },
    	        update:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        	var data = valueAccessor();
    	        	$(element).html(data);
    	        	try {
    	        		if(typeof data != undefined && data != null && data != "")
    	        			uParse('#articleContent', {rootPath: ROOT_URL+"view/lib/ueditor/"});
    	        	} catch (e) {
    	        		
    	        	}
    	        	$(document).scrollTop(self.windowScrollTop);
//    	        	$(document.documentElement).animate({
//    	                scrollTop: self.windowScrollTop
//    	            },500);
    	        }
    	    });
    		
    		this.init = function(){
    			this.categoties({data:[],activeCategoryID:0});
    			this.articles([]);
    			this.selectedArticle({articleID:-1,articleTitle:'',articleBrief:'',articleContent:'',updateTime:''});    		    		
    		}
    		
    		this.activate = function(categoryID){
    			if (typeof categoryID == undefined || categoryID==null)
    				categoryID = 0;
    			this.init();
    			
    			http.get(ROOT_URL + "category/getrootcategorylist/urlid/" + USER_URL_ID).then(function(data){
    				if (data.success) {
    					var categoriesData = data.data.categories;
    					categoriesData.unshift({"categoryID":0,"categoryName":"全部"});
    					self.categoties({data:categoriesData,activeCategoryID:categoryID});
    				} else {
    					
    				}
    			},function(error){
    				
    			});
    			http.get(ROOT_URL + "article/getarticles/urlid/" + USER_URL_ID + "/categoryid/" + categoryID).then(function(data){
    				if (data.success) {
    					var articlesData = data.data.articles;
    					for(var i=0; i<articlesData.length; ++i){
    						articlesData[i].createTime = dateUtils.timestampToDateStr(articlesData[i].createTime); 
    						articlesData[i].updateTime = dateUtils.timestampToDateStr(articlesData[i].updateTime); 
    					}
    					self.articles(articlesData);
    				} else {
    					
    				}
    			},function(error){
    				
    			});
    		};
    	};
    	
    	return new viewModel();
    });