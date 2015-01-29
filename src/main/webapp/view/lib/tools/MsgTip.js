define(['jquery'], function($) {
	var msgTip = function() {
		var self = this;
		this._suceessHtml = '<div class="well msg-tip-success" style="position: fixed; width: 200px; top: 50px; right: -200px; margin: 1px 1px 0 0; background-color: #5cb85c; border: 1px solid #5cb85c; z-index: 999; color: white; text-align: center;">' +
								'<i class="icon-ok"></i>%success' + 
							'</div>';
		/**
		 *显示成功提示
		 *msg String 要显示的文本
		 *opt:
		 *	autoClose boolean 是否自动关闭 默认值true
		 *	millisecond int 自动关闭的时间 默认值1000
		 *	width int 宽度
		 */
		this.showSuccessTip = function(msg, opt) {
			if (typeof msg == 'undefined' || (typeof msg != 'string' && typeof msg != 'number')) {
				return false;
			}
			if (typeof msg == 'number') {
				msg += "";
			}
			opt.autoClose = (typeof opt.autoClose == 'undefined' || opt.autoClose) ? true : false;
			opt.millisecond = (typeof opt.millisecond == 'number' && opt.millisecond>0) ? opt.millisecond : 1000; 
			opt.width = (typeof opt.width == 'number' && opt.width>0) ? opt.width : 200;
			opt.top = (typeof opt.top == 'number') ? opt.top : 50;
			
			//将html添加到文档中
			var isMsgTipSuccessExist = $(".msg-tip-success").length>0;
			if (!isMsgTipSuccessExist) {
				var html = self._suceessHtml.replace("%success",msg);
				$("body").append(html);
			} else {
				$(".msg-tip-success").html('<i class="icon-ok"></i>' + msg);
			}
			
			//设置样式
			$(".msg-tip-success").css({
				width: opt.width + "px",
				right: -opt.width - 1 + "px",
				top: opt.top + "px"
			});
			
			$(".msg-tip-success").animate({ 
				right: 0
			  }, 500, function() {
				  if (opt.autoClose) {
					  setTimeout(function() {
						  $(".msg-tip-success").animate({
							  right: -opt.width - 1 + "px"
							  }, 500);
					  }, opt.millisecond);
				  }
			  });
		};
	};
	
	return new msgTip();
});