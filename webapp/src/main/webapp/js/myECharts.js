
//柱状、折线  单题统计
var option = {
    title : {
        text: '单题统计',
        x:'left'
    },
    legend: {
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
        formatter: function (params) {
             var res = '';
             res += params[0].name + ':' + params[0].value;
             return res;
        }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category'
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            type:'bar',
            itemStyle : { 
                normal: {
                    label : {position: 'top',show:true}
                }
            },
           /* markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },*/
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }
    ]
};

//饼状图，单题统计
var pieOption = {
    title : {
        text: '统计',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}票 ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left'
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
            name:'投票占比情况',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%']
        }
    ]
};

//柱状、折线, 交叉统计
var crossBarOption = {
    title : {
        text: '交叉统计'
        //subtext: '纯属虚构'
    },
    legend: {
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
        formatter: function (params) {
             var res = '';
             res += '<strong>' + params[0].name + '</strong>';
             for (var i = 0, l = params.length; i < l; i++) {
                 res += '<br/>' + params[i].seriesName + ' : ' + params[i].value ;
             }
             return res;
        }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar','stack','tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category'
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ]
  };