<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<script src="/js/echarts.js"></script>
<script type="text/javascript" src="/js/jquery-1.7.1.js" ></script>
<script type="text/javascript" src="/js/myECharts.js" ></script>

<h1 align="center">
    ${groupData.title}
</h1>

<!-- 查询条件 -->
<form id="theform" action="<%=ctxPath%>/htgl/result/queryItemResult.action" method="post">
  <input type="hidden" name="groupId" value="${groupData.id}"/>
  <table align="center">
     <tr>
            <td>
              <select name="statisticsType" id="statisType">
                <option value="" >请选择统计类型</option>
                <c:forEach var="item" items="${statisticsTypes}">
                 <option value="${item.index}" <c:if test="${item.index==statisticsType}">selected</c:if> >${item.name}</option>
                </c:forEach>
              </select>
            </td>
            
            <td id="customStatis" >
              <select name="statisticsOption">
                <option value="" >请选择定制化统计项</option>
                <c:forEach var="item" items="${statisticsOptions}">
                 <option value="${item.index}" <c:if test="${item.index==statisticsOption}">selected</c:if> >${item.name}</option>
                </c:forEach>
              </select>
            </td>
            <td id="singleStatis" >
              <select name="itemId">
                <option value="" >请选择题目</option>
                <c:forEach var="item" items="${items}">
                 <option value="${item.id}" <c:if test="${item.id==itemId}">selected</c:if> >${item.title}</option>
                </c:forEach>
              </select>
            </td>
            <td id="singleStatis2" >
              <select name="itemId2">
                <option value="" >请选择题目</option>
                <c:forEach var="item" items="${items}">
                 <option value="${item.id}" <c:if test="${item.id==itemId2}">selected</c:if> >${item.title}</option>
                </c:forEach>
              </select>
            </td>
            <td><input type="submit" value="查 询"/></td>
    </tr>
  </table>
</form>

<!-- 定制化、单题统计结果 -->
<c:if test="${singleOrcustomResVO != null}">
  <table align="center" border="0" width="500">
      <tr>
            <td>
             选项
            </td>
            <td>
             票数
            </td>
        </tr>
      <c:forEach var="row" items="${singleOrcustomResVO.resultVos}">
        <tr>
            <td>
             ${row.title}
            </td>
            <td>
             ${row.count}
            </td>
        </tr>
      </c:forEach>
  </table>
</c:if>

<!-- 交叉统计结果 -->
<c:if test="${crossResultVO != null}">
  <table align="center" border="0" width="500">
      
        <tr>
          <c:forEach var="tit" items="${crossResultVO.titleVos}">
           <td>
             ${tit.title}
            </td>
          </c:forEach>
        </tr>
      
      
      <c:forEach var="row" items="${crossResultVO.resultVos}">
        <tr>
          <td>
             ${row.title}
            </td>
          <c:forEach var="col" items="${row.resultVos}">
            <td>
             ${col.count}
            </td>
          </c:forEach>
        </tr>
      </c:forEach>
  </table>
</c:if>


<br>
<br>
<br>


<!-- 统计图 -->
<div id="bar" style="height: 400px"></div>
<div id="pie" style="height: 400px"></div>

<script type="text/javascript">
$(function(){
    if($('#statisType').val() == ''){
        $('#customStatis').css('display','none');
        $('#singleStatis').css('display','none');
        $('#singleStatis2').css('display','none');
    }else{
        if($('#statisType').val() == '1'){
            $('#customStatis').css('display','');
            $('#singleStatis').css('display','none');
            $('#singleStatis2').css('display','none');
        }else if($('#statisType').val() == '2'){
            $('#customStatis').css('display','none');
            $('#singleStatis').css('display','');
            $('#singleStatis2').css('display','none');
        }else if($('#statisType').val() == '3'){
            $('#customStatis').css('display','none');
            $('#singleStatis').css('display','');
            $('#singleStatis2').css('display','');
        }
    }
    
    
    $('#statisType').change(function() {
        var staType = $('#statisType').val();
        if(staType == ''){
            $('#customStatis').css('display','none');
            $('#singleStatis').css('display','none');
            $('#singleStatis2').css('display','none');
        }else{
            if(staType == '1'){
                $('#customStatis').css('display','');
                $('#singleStatis').css('display','none');
                $('#singleStatis2').css('display','none');
            }else if(staType == '2'){
                $('#customStatis').css('display','none');
                $('#singleStatis').css('display','');
                $('#singleStatis2').css('display','none');
            }else if(staType == '3'){
                $('#customStatis').css('display','none');
                $('#singleStatis').css('display','');
                $('#singleStatis2').css('display','');
            }
        }
    });
});


//定制化、单个题目统计
if(${singleOrcustomJson} != null){
    var barChart = echarts.init(document.getElementById('bar')); 
    var pieChart = echarts.init(document.getElementById('pie'));
    
    var nameArray = new Array();
    var valueArray = new Array();
    var pieValueArray = new Array();
    
    var result = ${singleOrcustomJson};
    var resultVos = result.keyValues;
    for(var i=1; i <= result.count;i++){
        var temp = resultVos[i];
        nameArray[i-1]=temp.name;
        valueArray[i-1]=temp.value;
        
        tempObject = new Object();
        tempObject.name=temp.name;
        tempObject.value=temp.value;
        pieValueArray[i-1]=tempObject;
    }
    var title = result.title;

//柱状、折线图
    var legendArray = new Array();
    legendArray[0] = title;
    option.legend.data = legendArray;
    option.xAxis[0].data = nameArray;
    option.series[0].name = title;
    option.series[0].type = 'bar';
    option.series[0].data = valueArray;
    barChart.setOption(option); 
//饼状图
    pieOption.title.text = title;
    pieOption.legend.data = nameArray;
    pieOption.series[0].type = 'pie';
    pieOption.series[0].name = '投票占比情况';
    pieOption.series[0].data = pieValueArray;
    pieChart.setOption(pieOption);
}

//交叉统计
if(${crossJson} != null){
    var barChart = echarts.init(document.getElementById('bar')); 
    
    var result = ${crossJson};
    var rowTitle = result.rowTitle;
    var rowTitleValueTemp = rowTitle.rowTitleValue;
    var nameArray = new Array();
    for(var i=0;i < rowTitle.rowTitleCount;i++){
        nameArray[i] = rowTitleValueTemp[i];
    }
    
    var rows = result.rows;
    var rowValues = rows.rowValue;
    var seriesArray = new Array();
    var rowTitles = new Array();
    for(var i=0; i < rows.rowCount;i++){
        var rowvalue = rowValues[i];
        rowTitles[i]=rowvalue.rowTtitle;
        rowObject = new Object();
        rowObject.name = rowvalue.rowTtitle;
        rowObject.type = 'bar';
        
        var dataArray = new Array();
        var dataTemp = rowvalue.rowContent;
        for(var j=0;j < rowvalue.rowContentCount;j++){
            dataArray[j]=dataTemp[j];
        }
        rowObject.data = dataArray;
        
        // markPoint
       /*  markPoint = new Object();
        var markPointDataArray = new Array();
        temp01 = new Object();
        temp01.type = 'max';
        temp01.name = '最大值';
        markPointDataArray[0] = temp01;
        temp02 = new Object();
        temp02.type = 'min';
        temp02.name = '最小值';
        markPointDataArray[1] = temp02;
        markPoint.data = markPointDataArray;
        rowObject.markPoint = markPoint; */
        
        // markLine
        markLine = new Object();
        var markLineDataArray = new Array();
        temp03 = new Object();
        temp03.type = 'average';
        temp03.name = '平均值';
        markLineDataArray[0] = temp03;
        markLine.data = markLineDataArray;
        rowObject.markLine = markLine;
        
        labelObj = new Object();
        labelObj.show = true;
        labelObj.position = 'top';
        normalObj = new Object();
        normalObj.label = labelObj;
        itemStyleObj = new Object();
        itemStyleObj.normal = normalObj;
        rowObject.itemStyle = itemStyleObj;
        
        seriesArray[i] = rowObject;
    }
//交叉统计，折线、柱状图    
    crossBarOption.legend.data = rowTitles;
    crossBarOption.xAxis[0].data = nameArray;
    crossBarOption.series = seriesArray;
    barChart.setOption(crossBarOption); 
    
   
}

</script>
