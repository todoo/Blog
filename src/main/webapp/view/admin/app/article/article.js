define(['jquery','durandal/composition','plugins/http','knockout','ueditor/ueditor.all'],
function ($,composition,http,ko,UE) {
	var viewModel = function() {
		var self = this;
		this.ueditor = null;
		
		this.activate = function() {
			composition.addBindingHandler('ueditorInit',{
    	        init:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        	if (self.ueditor != null) {
    	        		self.ueditor.destroy();
    	        	}
        	        self.ueditor = UE.getEditor('ueditorContent');
    	        },
    	        update:function(element, valueAccessor, allBindings, viewModel, bindingContext){
    	        }
    	    });
		};
	};
	return new viewModel();
});