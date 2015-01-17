define(function(){
	var dateUtils = {
		dateToDateStr: function(date) {
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
		},
		timestampToDateStr: function(timestamp) {
			var date = new Date();
			date.setTime(timestamp);
			
			return this.dateToDateStr(date);
		}
	}
	
	return dateUtils;
});