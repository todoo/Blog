define(['jquery','durandal/composition','plugins/http','knockout','ueditor/ueditor.all','plugins/dialog','tools/DateUtils','tools/MsgTip'],
function ($,composition,http,ko,UE,dialog,dateUtils,msgTip) {
	var viewModel = function() {
		var self = this;
		this.categories = ko.observable();
		this.articles = ko.observable();
		this.selectedArticle = ko.observable();
		this.enableSubmit = ko.observable();
		this._isSubmitting = false;
		this._ueditor = null;
		this._articleListScrollTop = 0;
		this._windowScrollTop = 0;
		
		this.fcDeleteArticle = function(data, event) {
			dialog.showMessage("确定要删除此博文吗？", "确认", [{text: "确定", value: "Yes"}, {text: "取消", value: "No"}], false).then(function(value) {
				if ("Yes" == value) {
					var deleteArticle = data;
					//将删除按钮设为不可用
					self._setArticleDeleteButtonEnableValue(data.articleID, false);
					//删除博文
					http.remove(ROOT_URL + "admin/article/delete/articleid/" + data.articleID).then(function(data) {
						if (data.success) {
							//删除成功，更新博文列表对象
							var articles = self.articles();
							for(var i=0; i<articles.length; ++i) {
								if (articles[i].articleID == deleteArticle.articleID) {
									articles.splice(i, 1);
									break;
								}
							}
							self.articles(articles);
							
							//更新类目对象的博文数目
							var categories = self.categories();
							for(var i=0; i<categories.data.length; ++i) {
								if (0 == categories.data[i].categoryID) {
									//全部 分类数目减一
									var oldArticleCount = categories.data[i].articleCount();
									categories.data[i].articleCount(oldArticleCount-1);
								} else if (categories.data[i].categoryID == deleteArticle.categoryID) {
									var oldArticleCount = categories.data[i].articleCount();
									categories.data[i].articleCount(oldArticleCount-1);
								}
							}
						} else {
							
						}
					},function(error) {
						
					});
				}
			});
		};
		
		this.fcShowArticle = function(data, event) {
			self._articleListScrollTop = $(document).scrollTop();
			self._windowScrollTop = 0;
			//编辑一篇博文
			self.selectedArticle(data);
			self.fcSetEnableSubmit();
		};
		
		this.fcHideArticle = function() {
			//关闭编辑
			self._windowScrollTop = self._articleListScrollTop;
			self.selectedArticle({articleID: -1, articleTitle: ko.observable(''), articleBrief: ko.observable(''), articleContent:'',updateTime:''});
		}
		
		this.fcSetEnableSubmit = function() {
			//根据当前状态，设置enableSubmit的值
			if (self._isSubmitting) {
				self.enableSubmit(false);
			} else {
				var article = self.selectedArticle();
				if (article.articleTitle().length<=0) {
					//标题不能为空
					self.enableSubmit(false);
				} else if (article.articleBrief().length<=0) {
					//简介不能为空
					self.enableSubmit(false);
				} else if (article.articleContent.length<=0) {
					//内容不能为空
					self.enableSubmit(false);
				} else {
					self.enableSubmit(true);
				}
			}
		};
		
		this.fcSubmitArticle = function(data, event) {
			if (!self.enableSubmit()) {
				//不满足提交条件
				return;
			}
			
			//禁用提交按钮
			self._isSubmitting = true;
			self.fcSetEnableSubmit();
			
			var header = {"Content-Type":"application/json;charset=UTF-8"};
			//提交
			http.put(ROOT_URL + "admin/article/update", self.selectedArticle(), header).then(function(data) {
				if (data.success) {
					//更新博文列表中的数据
					var articles = self.articles();
					for(var i=0; i<articles.length; ++i) {
						if (articles[i].articleID == self.selectedArticle().articleID) {
							self.articles()[i].articleTitle(self.selectedArticle().articleTitle());
							self.articles()[i].articleBrief(self.selectedArticle().articleBrief());
							self.articles()[i].articleContent = self.selectedArticle().articleContent;
						}
					}
					msgTip.showSuccessTip("保存成功", {
						width: 200,
						top: $(event.currentTarget).position().top - $(document).scrollTop() 
					});
				} else {
					
				}
				//恢复提交按钮
				self._isSubmitting = false;
				self.fcSetEnableSubmit();
			}, function(error) {
				//恢复提交按钮
				self._isSubmitting = false;
				self.fcSetEnableSubmit();
			});
		};
		
		this._init = function() {
			self.categories({data:[], selectCategoryID:-1});
			self.selectedArticle({articleID: -1, articleTitle: ko.observable(''), articleBrief: ko.observable(''), articleContent:'',updateTime:''});
			self.articles([]);
			self.enableSubmit(true);
			self._isSubmitting = false;
			
			if (self._ueditor != null) {
        		self._ueditor.destroy();
        		self._ueditor = null;
        	}
		};
		
		this._setArticleDeleteButtonEnableValue = function(articleID, value) {
			var articles = self.articles();
			for (var i=0; i<articles.length; ++i) {
				if (articles[i].articleID == articleID) {
					self.articles()[i].deleteEnable(value);
				}
			}
		};
		
		composition.addBindingHandler('ueditorUpdate',{
	        init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
	        	
	        },
	        update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {	
	        	$(document).scrollTop(self._windowScrollTop);
	        	
	        	if (self.selectedArticle().articleID < 0) {
	        		return;
	        	}
	        	
    	        self._ueditor = UE.getEditor('updateUeditorContent');
    	        
    	        try {
    	        	//非第一次时调用成功
    	        	self._ueditor.setContent(self.selectedArticle().articleContent);//编辑器家在完成后，设置内容
    	        } catch (e) {  
    	        	//第一次初始化编辑器时调用
		        	self._ueditor.addListener('ready', function() {
		        		self._ueditor.setContent(self.selectedArticle().articleContent);//编辑器家在完成后，设置内容
		        	});
    	        }
    	        
    	        self._ueditor.addListener('contentChange', function() {
	        		var pattern = new RegExp('src="(' + CONTEXT_PATH + ')[\/a-zA-Z0-9\.]*"', "g");
	        		self.selectedArticle().articleContent = self._ueditor.getContent().replace(pattern,function(substr, match) {
    	        		return substr.replace(match,"");
    	        	});
    	        	
    	            self.fcSetEnableSubmit();
    	        });
	        }
	    });
		
		this.activate = function(categoryID) {
			if (typeof categoryID == undefined || categoryID == null)
				categoryID = 0;
			self._init();
			
			//获取类目列表
			http.get(ROOT_URL + "admin/category/getrootcategorylist").then(function(data){
				if (data.success) {
					var categoriesData = data.data.categories;
					//计算总数目,并将数目转换为knockoutjs对象，用于与视图绑定
					var articleCountSum = 0;
					for (var i=0; i<categoriesData.length; ++i) {
						articleCountSum += categoriesData[i].articleCount;
						categoriesData[i].articleCount = ko.observable(categoriesData[i].articleCount);
					}
					categoriesData.unshift({"categoryID": 0, "categoryName": "全部", "articleCount": ko.observable(articleCountSum)});
					self.categories({data:categoriesData, selectCategoryID:categoryID});
				} else {
					
				}
			},function(error){
				
			});
			
			//获取类目博文列表
			http.get(ROOT_URL + "admin/article/getarticles/categoryid/" + categoryID).then(function(data){
				if (data.success) {
					var articlesData = data.data.articles;
					for(var i=0; i<articlesData.length; ++i){
						articlesData[i].createTime = dateUtils.timestampToDateStr(articlesData[i].createTime); 
						articlesData[i].updateTime = dateUtils.timestampToDateStr(articlesData[i].updateTime); 
						//将博文的标题、简介设为knockoujs变量，用于更新
						articlesData[i].articleTitle = ko.observable(articlesData[i].articleTitle);
						articlesData[i].articleBrief = ko.observable(articlesData[i].articleBrief);
						//添加操作按钮是否可以变量
						articlesData[i].deleteEnable = ko.observable(true);
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