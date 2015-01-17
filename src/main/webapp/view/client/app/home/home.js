define(['jquery','durandal/composition','durandal/system','plugins/http','plugins/dialog','knockout','tools/DateUtils','highcharts/highcharts.src','highcharts/themes/gray'],function ($,composition,system,http,dialog,ko,dateUtils) {
	return {
		user: ko.observable({nickName:''}),
		lifeStatus: ko.observable({status:'',createTime:''}),
		activityChartData: ko.observable({}),
		classifyChartData: ko.observable({}),
		
		activate: function() {
			composition.addBindingHandler('initAppHomeActivityChart',{
		        init:function(element, valueAccessor, allBindings, viewModel, bindingContext){
		        },
		        update:function(element, valueAccessor, allBindings, viewModel, bindingContext){
		        	var data = valueAccessor();
		        	$(element).highcharts({    
		        		chart: {
	        	            zoomType: 'x',
	        	            spacingRight: 20
	        	        },
	        	        title: {
	        	            text: '活跃度统计'
	        	        },
	        	        subtitle: {
	        	            text: data.subtitle
	        	        },
	        	        xAxis: {
	        	            type: 'datetime',
	        	             dateTimeLabelFormats: {
	        	 				millisecond: '%H:%M:%S.%L',
	        					second: '%H:%M:%S',
	        					minute: '%H:%M',
	        	                hour: '%H:%M',
	        	                day: '%Y/%m/%e',
	        	                week: '%Y/%m/%e',
	        	                month: '%Y/%m/%e',
	        	                year: '%Y'
	        	            },
	        	            maxZoom: 7 * 24 * 3600000, // fourteen days
	        	            title: {
	        	                text: '时间(/天)'
	        	            }
	        	           
	        	        },
	        	        yAxis: {
	        	            title: {
	        	                text: '活跃度'
	        	            }
	        	        },
	        	        tooltip: {
	        	            shared: true,
	        	            dateTimeLabelFormats: {
        	  				millisecond: '%a %Y/%m/%e',
        	 				second: '%a %Y/%m/%e',
        	 				minute: '%a %Y/%m/%e',
        	                hour: '%a %Y/%m/%e',
        	                day: '%a %Y/%m/%e',
        	                week: '%a %Y/%m/%e',
        	                month: '%a %Y/%m/%e',
        	                year: '%Y'
        	             }
	        	        },
	        	        legend: {
	        	            enabled: false
	        	        },
	        	        plotOptions: {
	        	            area: {
	        	                fillColor: {
	        	                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	        	                    stops: [
	        	                        [0, Highcharts.getOptions().colors[0]],
	        	                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	        	                    ]
	        	                },
	        	                lineWidth: 1,
	        	                marker: {
	        	                    enabled: false
	        	                },
	        	                shadow: false,
	        	                states: {
	        	                    hover: {
	        	                        lineWidth: 1
	        	                    }
	        	                },
	        	                threshold: null
	        	            }
	        	        },

	        	        series: [{
	        	            type: 'area',
	        	            name: '活跃度',
	        	            pointInterval: 24 * 3600 * 1000,
	        	            pointStart: data.pointStart,
	        	            data: data.data
	        	        }]
				    });
		        }
		    });
			
			composition.addBindingHandler('initAppHomeClassifyChart',{
		        init:function(element, valueAccessor, allBindings, viewModel, bindingContext){
		        	
		        },
		        update:function(element, valueAccessor, allBindings, viewModel, bindingContext){
		        	var data = valueAccessor();
		        	$(element).highcharts({    
		        		chart: {
		                    plotBackgroundColor: null,
		                    plotBorderWidth: null,
		                    plotShadow: false
		                },
		                title: {
		                    text: '博文分类统计'
		                },
		                tooltip: {
		            	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		                },
		                plotOptions: {
		                    pie: {
		                        allowPointSelect: true,
		                        cursor: 'pointer',
		                        dataLabels: {
		                            enabled: true,
		                            color: '#CCCCCC',
		                            connectorColor: '#CCCCCC',
		                            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                        }
		                    }
		                },
		                series: [{
		                    type: 'pie',
		                    name: '百分比',
		                    data: data.data
		                }]
				    });
		        }
		    });
			
			var self = this;
			
			http.get(ROOT_URL + "user/urlid/" + USER_URL_ID).then(function(data){
				self.user(data.data.user);
			},function(error){
				dialog.showMessage(error,'error',['close'],true);
			});
			
			http.get(ROOT_URL + "lifestatus/urlid/" + USER_URL_ID).then(function(data){
				if (data.success) {
					data.data.lifeStatus.createTime = dateUtils.timestampToDateStr(data.data.lifeStatus.createTime);
					self.lifeStatus(data.data.lifeStatus);
				} else {
					dialog.showMessage(data.message,'error',['close'],true);
				}
			},function(error){
				dialog.showMessage(error,'error',['close'],true);
			});
			
			http.get(ROOT_URL + "statistic/activity/urlid/" + USER_URL_ID).then(function(data){
				if (data.success) {
					self.activityChartData(data.data);
				} else {
					dialog.showMessage(data.message,'error',['close'],true);
				}
			},function(error){
				dialog.showMessage(error,'error',['close'],true);
			});
			
			http.get(ROOT_URL + "statistic/classify/urlid/" + USER_URL_ID).then(function(data){
				if (data.success) {
					self.classifyChartData(data.data);
				} else {
					dialog.showMessage(data.message,'error',['close'],true);
				}
			},function(error){
				dialog.showMessage(error,'error',['close'],true);
			});
		}
	};
});