function dataSearch() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	console.log(startDate + "," + endDate)
	getDate(startDate, endDate);
}
function getDate(startDate, endDate) {
	var data = [];
	var data1 = [];
	var data2 = [];
	var data3 = [];
	var data4 = [];
	var data5 = [];
	var data6 = [];
	$.ajax({
		 url : "http://10.90.7.47:8880/dataflow/data/statistics?startDate="
		 + startDate + "&endDate=" + endDate,
		 url : "http://127.0.0.1:8080/dataflow/data/statistics?startDate="
		 + startDate + "&endDate=" + endDate,
		// url: filepath,
		// force to handle it as text
		type : 'GET',
		dataType : 'json',
		error : function() {
			alert("error");
		},
		success : function(content) {
			console.log(content);
			data = $.parseJSON(content["原始抓取数量"]);
			data1 = $.parseJSON(content["排重后数量"]);
			data2 = $.parseJSON(content["进入泛编数量"]);
			data3 = $.parseJSON(content["泛编下线数量"]);
			data4 = $.parseJSON(content["泛编出口数量"]);
			data5 = $.parseJSON(content["推荐数据更新量"]);
			data6 = $.parseJSON(content["可推荐量"]);
			// data3 = content["推荐数据更新量"];
			myChart.setOption({
				title : {
					show : true,
					text : '内容入库数据统计折线图',
					// subtext : '内容入库数据',
					right : 200,
					// borderColor : 'red',
					// borderWidth : 1,
					textStyle : {
						fontSize : 20
					}
				},
				legend : {
					left : 50,
					data : [ '原始抓取数量', '排重后数量', '进入泛编数量', '泛编下线数量', '泛编出口数量',
							'推荐数据更新量', '可推荐量' ],
				// textStyle : {
				// fontSize : 15
				// }
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : true
						},
						// restore : {
						// show : true
						// },
						// dataZoom : {
						// show : true
						// },
						saveAsImage : {
							show : true
						},
						magicType : {
							type : [ 'line', 'bar' ]
						}
					}
				},

				tooltip : {
					trigger : 'axis',
					// formatter : function(params) {
					// params = params[0];
					// return params.value[0] + ' : '
					// + params.value[1];
					// },
					axisPointer : {
						type : 'cross'
					}
				},

				xAxis : [ {
					type : 'time',
					splitLine : {
						show : false
					}
				}, ],
				yAxis : {
					type : 'value',
					boundaryGap : [ 0, '10%' ],
					splitLine : {
						show : false
					}
				},
				series : [ {
					name : '原始抓取数量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data
				}, {
					name : '排重后数量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data1
				}, {
					name : '进入泛编数量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data2
				}, {
					name : '泛编下线数量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data3
				}, {
					name : '泛编出口数量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data4
				}, {
					name : '推荐数据更新量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data5
				}, {
					name : '可推荐量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					data : data6
				} ]
			});
		}
	})

}
