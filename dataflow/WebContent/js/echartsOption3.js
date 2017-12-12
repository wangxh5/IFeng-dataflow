function search() {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	console.log(startTime + "," + endTime)
	getDate(startTime,endTime);
}
function getDate(startTime, endTime) {
	var data = [];
	$
			.ajax({
				url : "http://127.0.0.1:8080/ServletDemo/demo/LocRecognizeServlet?startTime="
						+ startTime + "&endTime=" + endTime,

				// url: filepath,
				// force to handle it as text
				type : 'GET',
				dataType : 'text',
				error : function erryFunction() {
					alert("error");
				},
				success : function(content) {
					data = $.parseJSON(content);
					console.log(data);

					myChart.setOption({
						title : {
							text : '地域识别耗时统计折线图'
						},
						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								params = params[0]
								return params.value[0] + ' : '
										+ params.value[1];
							},
							axisPointer : {
								animation : false
							}
						},
						xAxis : {
							type : 'value',
							boundaryGap : [ 0, 0 ],
							splitLine : {
								show : false
							}
						},
						yAxis : {
							type : 'value',
							boundaryGap : [ 0, '10%' ],
							splitLine : {
								show : false
							}
						},
						series : [ {
							name : '地域识别时间统计折线图',
							type : 'line',
							showSymbol : false,
							hoverAnimation : false,
							data : data
						} ]
					});
				}
			});
}
