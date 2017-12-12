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
	$.ajax({
		// url : "http://127.0.0.1:8080/dataflow/expo?startDate=" + startDate
		// + "&endDate=" + endDate,
		url : "http://10.90.7.47:8880/dataflow/expo?startDate=" + startDate
				+ "&endDate=" + endDate,

		type : 'GET',
		dataType : 'json',
		error : function() {
			alert("error");
		},
		success : function(content) {
			data = $.parseJSON(content["当日数据曝光比"]);
			data1 = $.parseJSON(content["当日曝光数据比"]);
			data2 = $.parseJSON(content["总体曝光比"]);
			myChart.setOption({
				title : {
					show : true,
					text : '曝光比率折线图',
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
					data : [ '更新曝光量/当日新增推荐量', '更新曝光量/曝光总数', '曝光总数/可推荐量' ],
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
						restore : {
							show : true
						},
						dataZoom : {
							show : true
						},
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
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel : {
						show : true,
						interval : 'auto',
						formatter : '{value} %'
					},
					show : true
				} ],
				series : [ {
					name : '更新曝光量/当日新增推荐量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					itemStyle : {
						normal : {
							label : {
								show : true,
								position : 'top',
								formatter : '{c}%'
							}
						}
					},
					data : data
				}, {
					name : '更新曝光量/曝光总数',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					itemStyle : {
						normal : {
							label : {
								show : true,
								position : 'top',
								formatter : '{c}%'
							}
						}
					},
					data : data1
				}, {
					name : '曝光总数/可推荐量',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					itemStyle : {
						normal : {
							label : {
								show : true,
								position : 'top',
								formatter : '{c}%'
							}
						}
					},
					data : data2
				} ]
			});
		}
	})

}
