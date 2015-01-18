define(['jquery','durandal/composition','plugins/http','knockout','ueditor/ueditor.config','ueditor/ueditor.all'],
function ($,composition,http,ko,UEDITOR_CONFIG,UE) {
	var viewModel = function() {
		var self = this;
		this.ueditor = null;
		
		this.activate = function() {
			composition.addBindingHandler('ueditorInit',{
    	        init:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        	self.ueditor = UE.getEditor('ueditorContent');
    	        },
    	        update:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        }
    	    });
		};
	};
	return new viewModel();
});