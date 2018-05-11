<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<script src="/js/echarts.js"></script>
<script type="text/javascript" src="/js/jquery-1.7.1.js" ></script>

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

<!-- 投票票数 -->
<c:if test="${resultVO != null}">
  <table align="center" border="0" width="500">
    
      <tr>
        <td>
          ${resultVO.title}
        </td>
        <td>
             票数
        </td>
      </tr>
      
      <c:forEach var="vo" items="${resultVO.resultVos}">
        <tr>
          <td>
            ${vo.title}
          </td>
          <td>
            ${vo.count}
          </td>
        </tr>
      </c:forEach>
    
  </table>
</c:if>

<!-- 统计图 -->
<div id="bar" style="height: 400px"></div>
<div id="line" style="height: 400px"></div>
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

//获取饼状图Option
var getPieOption = function(titleText,titleSubtext,legendData,seriesName,seriesData){
    option=new Object();
    
    //title
    title = new Object();
    title.text=titleText;
    title.subtext=titleSubtext;
    title.x='center';
    option.title = title;
    
    //tooltip
    tooltip =new Object();
    tooltip.trigger = 'item';
    tooltip.formatter = '{a} <br/>{b}({c}票)/({d}%)';
    option.tooltip = tooltip;
    
    //legend
    legendObject = new Object();
    legendObject.orient = 'vertical';
    legendObject.x  = 'left';
    legendObject.data = legendData;
    option.legend = legendObject;
    
    //toolbox
    toolboxObject = new Object();
    toolboxObject.show = true;
    
    feature = new Object();
    mark = new Object();
    mark.show = true;
    feature.mark = mark;
    
    dataView = new Object();
    dataView.show = true;
    dataView.readOnly = false;
    feature.dataView = dataView;
    
    magicType = new Object();
    magicType.show = true;
    var mgicTypeType = new Array('pie', 'funnel');
    magicType.type = mgicTypeType;
    magicTypeOption = new Object();
    magicTypeOptionFunnel = new Object();
    magicTypeOptionFunnel.x = '25%';
    magicTypeOptionFunnel.width = '50%';
    magicTypeOptionFunnel.funnelAlign = 'left';
    magicTypeOptionFunnel.max = 1548;
    magicTypeOption.funnel = magicTypeOptionFunnel;
    magicType.option = magicTypeOption;
    feature.magicType = magicType;
    
    restore = new Object();
    restore.show = true;
    feature.restore = restore;
    
    saveAsImage = new Object();
    saveAsImage.show = true;
    feature.saveAsImage = saveAsImage;
    
    toolboxObject.feature = feature;
    
    option.toolbox = toolboxObject;
    
    //calculable
    option.calculable = true;
    
    //series
    var seriesArray = new Array();
    seriesObject = new Object();
    seriesObject.name = '投票占比情况';
    seriesObject.type = 'pie';
    seriesObject.radius = '55%';
    var center = new Array('50%', '60%');
    seriesObject.center = center;
    seriesObject.data = seriesData;
    
    seriesArray[0]=seriesObject;
    
    option.series=seriesArray;
    
    return option;
}

//获取折线、柱状图Option
var getOption = function(legend,xAxisType,xAxisData,seriesName,seriesType,seriesData){
    option=new Object();
    
    //tooltip
    tooltip = new Object();
    tooltip.show = true;
    option.tooltip = tooltip;
    
    //legend
    legendObje = new Object();
    var legendArray = new Array();
    legendArray[0] = legend;
    legendObje.data = legendArray;
    option.legend = legendObje;
    
    //xAxis
    var xAxisArray = new Array();
    xAxisValueObject = new Object();
    xAxisValueObject.type = xAxisType;
    xAxisValueObject.data = xAxisData;
    xAxisArray[0] = xAxisValueObject;
    option.xAxis = xAxisArray;
    
    //yAxis
    var yAxisArray = new Array();
    yAxisValueObject = new Object();
    yAxisValueObject.type = 'value';
    yAxisArray[0] = yAxisValueObject;
    option.yAxis = yAxisArray;
    
    //series
    var seriesArray = new Array();
    seriesObject = new Object();
    seriesObject.name = seriesName;
    seriesObject.type = seriesType;
    seriesObject.data = seriesData;
    seriesArray[0] = seriesObject;
    option.series = seriesArray;
    
    return option;
}

if(${resultVOJson} != null){
    var barChart = echarts.init(document.getElementById('bar')); 
    var lineChart = echarts.init(document.getElementById('line')); 
    var pieChart = echarts.init(document.getElementById('pie'));
    
    var nameArray = new Array();
    var valueArray = new Array();
    var pieValueArray = new Array();
    
    var result = ${resultVOJson};
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
    var barOption = getOption(title,'category',nameArray,title,'bar',valueArray);
    var lineOption = getOption(title,'category',nameArray,title,'line',valueArray);
    var pieOption = getPieOption(title,'投票情况',nameArray,title,pieValueArray);

    barChart.setOption(barOption); 
    lineChart.setOption(lineOption); 
    pieChart.setOption(pieOption);
}

</script>
