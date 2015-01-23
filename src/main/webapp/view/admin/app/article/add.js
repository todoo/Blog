define(['jquery','durandal/composition','plugins/http','knockout','ueditor/ueditor.all','plugins/dialog'],
function ($,composition,http,ko,UE,dialog) {
	var viewModel = function() {
		var self = this;
		this.ueditor = null;
		this.categories = ko.observable({data:[],selectCategoryID:ko.observable(-1)});
		this.article = ko.observable({articleTitle:ko.observable(''),articleBrief:ko.observable(''),articleContent:''});
		this._isSubmitting = false; //是否正在提交表单
		this.enableSubmit = ko.observable(false);//保存按钮是否可用
		this.selectCategory = function(data,event) {
			self.categories().selectCategoryID(data.categoryID);
			self.setEnableSubmit();
		};
		this.setEnableSubmit = function() {
			//根据当前状态，设置enableSubmit的值
			if (self._isSubmitting) {
				self.enableSubmit(false);
			} else {
				var article = self.article();
				if (article.articleTitle().length<=0) {
					//标题不能为空
					self.enableSubmit(false);
				} else if (article.articleBrief().length<=0) {
					//简介不能为空
					self.enableSubmit(false);
				} else if (article.articleContent.length<=0) {
					//内容不能为空
					self.enableSubmit(false);
				} else if (self.categories().selectCategoryID()<=0) {
					//所属类目不能为空
					self.enableSubmit(false);
				} else {
					self.enableSubmit(true);
				}
			}
		};
		this._resetForm = function() {
			//重置新增博文的表单值
			self.categories().selectCategoryID(-1);
			self.article().articleTitle('');
			self.article().articleBrief('');
			self.ueditor.setContent("");
			self.setEnableSubmit();
		};
		this.submitArticle = function() {
			//提交博文
			if (!self.enableSubmit()) {
				//不满足提交条件
				return;
			}
			var article = {};
			article.categoryID = self.categories().selectCategoryID();
			article.articleTitle = self.article().articleTitle();
			article.articleBrief = self.article().articleBrief();
			article.articleContent = self.article().articleContent;
			var header = {"Content-Type":"application/json;charset=UTF-8"};
			http.post(ROOT_URL + "admin/article/add",article,header).then(function(data){
				if (data.success) {
					self._resetForm();
				} else {
					dialog.showMessage("添加失败", "错误", ["关闭"], true);
				}
			},function(error){
				
			});
		};
		
		composition.addBindingHandler('ueditorInit',{
	        init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
    	        self.ueditor = UE.getEditor('ueditorContent');
    	        self.ueditor.addListener('contentChange', function(editor) {
	        		var pattern = new RegExp('src="(' + CONTEXT_PATH + ')[\/a-zA-Z0-9\.]*"', "g");
	        		self.article().articleContent = self.ueditor.getContent().replace(pattern,function(substr, match) {
    	        		return substr.replace(match,"");
    	        	});
    	        	
    	            self.setEnableSubmit();
    	        });
	        },
	        update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
	        }
	    });
		
		this._init = function() {
			if (self.ueditor != null) {
        		self.ueditor.destroy();
        	}
			self.ueditor = null;
			self.categories({data:[],selectCategoryID:ko.observable(-1)});
			self.article = ko.observable({articleTitle:ko.observable(''),articleBrief:ko.observable(''),articleContent:''});
			self._isSubmitting = false;
			self.dis
		};
		
		this.activate = function() {
			self._init();
			//获取类目列表
			http.get(ROOT_URL + "admin/category/getrootcategorylist").then(function(data){
				if (data.success) {
					var categoriesData = data.data.categories;
					self.categories({data:categoriesData,selectCategoryID:ko.observable(-1)});
				} else {
					
				}
			},function(error){
				
			});
		};
	};
	return new viewModel();
});