<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<script src="/js/echarts.js"></script>

<body>

	<div id="bar" style="height: 400px"></div>
	<div id="line" style="height: 400px"></div>
	<div id="pie" style="height: 400px"></div>


</body>

<script type="text/javascript">

var pieChart = echarts.init(document.getElementById('pie')); 
 var lineChart = echarts.init(document.getElementById('line')); 
 
 var pieOption = {
			title : {
		        text: '某站点用户访问来源',
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'访问来源',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ]
		        }
		    ]
	};


	var lineOption = {
		    tooltip: {
		        show: true
		    },
		    legend: {
		        data:['销量']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            "name":"销量",
		            "type":"line",
		            "data":[5, 20, 40, 10, 10, 20]
		        }
		    ]
		};

	pieChart.setOption(pieOption); 
	lineChart.setOption(lineOption); 
</script>

