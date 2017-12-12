		function load(urlxx, type) {
			var data = [];
			var filepath;
			var title;
			if(type=='week') {
				filepath = "../data/userRetention/json_clickPull_avg_" + urlxx;
				urlxx = urlxx.substr(4,2);
				title = '2017年' + urlxx +'月分周新增用户点击下拉次数与次日留存率'
			}
			else if(type=='day') {
				filepath = "../data/userRetention/json_clickPull_" + urlxx ;
				var date = $.datepicker.parseDate( "yymmdd", urlxx );
				title = date.getFullYear() + '年' + (date.getMonth() +1) + '月' + date.getDate() + '日新增用户点击下拉次数与次日留存率';
			}
			
			$.ajax({
				url : filepath,
				//url: filepath, 
				//force to handle it as text
				dataType : "text",
				success : function(content) {

					data = $.parseJSON(content);
					//data = data.map(function (item) {
					//  return [item[1], item[0], item[2] || '-'];
					//});
					myChart.setOption({
						title : {
							text : title,
							left : 'center'
						},
						tooltip : {
							position : 'bottom',
							formatter : function(obj) {
								var value = obj.value;
								if(value[0] > 15)
            	            	value[0] = '15+';
	            	            if(value[1] > 10)
	            	            	value[1] = '11+';
	            	            return value[0] + '次下拉, ' + value[1] + '次点击<br>留存用户:' + value[3] + '<br>新增用户:' + value[4] +  '<br>留存率:' + value[2] + '%' ;
	            	        }
						},
						animation : false,
						grid : {
							height : '80%',
							y : '10%'
						},
						xAxis : {
							type : 'category',
							data : data.drawDown,
							position : 'top',
							splitArea : {
								show : true
							},
							axisLabel : {
								formatter : '{value} 次下拉'
							},
						},
						yAxis : {
							type : 'category',
							data : data.click,
							splitArea : {
								show : true
							},
							inverse : true,
							axisLabel : {
								formatter : '{value} 次点击'
							},
						},

						visualMap : {
							min : 0,
							max : 100,
							calculable : true,
							orient : 'vertical',
							left : '3%',
							bottom : 'center',
							inRange : {
								color : [ '#ff5983', 'rgba(193,25,78,0.5)',
										'#7a023c' ],
							}
						},
						series : [ {
							name : '留存率',
							type : 'heatmap',
							data : data.data,
							label : {
								normal : {
									formatter : [ '{c[2]} ' ],
									show : true
								}
							},
							itemStyle : {
								emphasis : {
									shadowBlur : 10,
									shadowColor : 'rgba(0, 0, 0, 0.5)'
								}
							}
						} ]
					});
				}
			});
		};