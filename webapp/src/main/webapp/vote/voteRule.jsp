<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<script type="text/javascript" src="/js/jquery-1.7.1.js" ></script>
<script type="text/javascript" src="/js/layer/layer.js"></script>

<style>
p{padding:0px; margin:0px;display: inline;}
</style>

<h1 align="center">
    设置投票规则
</h1>

<div align="center">
 <form id="theform" action="">
   <input type="hidden" name="groupId" id="groupId" value="${groupId}"/>
  <span style="font-weight:bold;">控制选项被选次数:</span>
  
  <select name="id" id="itemId">
                <option value="" >请选择题目</option>
                <c:forEach var="item" items="${items}">
                  <option value="${item.id}" <c:if test="${item.id==itemId}">selected</c:if> >Q${item.index}:${item.title}</option>
                </c:forEach>
  </select>
  
  <select name="optionId" id="optionId">
                        <option value="" >请选择选项</option>
                        <c:forEach var="option" items="${options}">
                         <option value="${option.id}" <c:if test="${option.id==optionId}">selected</c:if> >
                           ${option.title }
                         </option>
                        </c:forEach>
  </select>
  
  
  &nbsp;&nbsp;
  <input type="button" name="queryRule" id="queryRule" value="查 询"/>
  &nbsp;&nbsp;
   <input type="button" name="setOptionRule" id="setOptionRule" value="设 置"/>
  
 </form>
  
</div>

<c:if test="${null != groupOptionRules}">
<c:if test="${groupOptionRules.size() > 0}">
 <div>
   <table align="center">
     <tr>
       <th>序号</th>
       <th>选项</th>
       <th>最少被选择次数</th>
       <th>最多被选择次数</th>
       <th>操作</th>
     </tr>
     <c:forEach var="rule" varStatus="status" items="${groupOptionRules}">
      <tr align="center">
       <td>${status.index + 1}</td>
       <td>${rule.title }</td>
       <td>${rule.minSelect}</td>
       <td>${rule.maxSelect}</td>
       <td><a href="<%=ctxPath%>/htgl/group/deleteOptionRule.action?id=${rule.id}&groupId=${groupId}&itemId=${itemId}&optionId=${optionId}">删除</a></td>
      </tr>
     </c:forEach>
   </table>
</div>
</c:if>

<c:if test="${groupOptionRules.size() == 0}">
<div align="center">
 无结果
</div>
</c:if>

</c:if>


<script type="text/javascript">
$(function(){
    if($('#itemId').val() == ''){
        $('#optionId').css('display','none');
    }
    
    $('#itemId').change(function() {
        var itemId = $('#itemId').val();
        debugger;
        if(itemId==''){
            var temp = "<option value=''>请选择</option>";
            $('#optionId').html(temp);
            return;
        }else{
            var param = {};
            param.id = itemId;
            param.flag=true;
            $.post('/htgl/group/getOptionsByItemId.action',param,function(data){
                var rs = $.parseJSON(data);
                var flag = rs.flag;
                if(flag){
                    var temp = "<option value=''>请选择</option>";
                    for(var i = 0 ; i< rs.o.length ;i++){
                        temp += " <option value='" + rs.o[i].id + "'>" + rs.o[i].title + "</option>";
                    }
                    $('#optionId').html(temp);
                    $('#optionId').css('display','');
                }else{
                    var temp = "<option value=''>请选择</option>";
                    $('#optionId').html(temp);
                    alert("查询有误，请重试");
                }
            });
        }
        
    });
});


$("input[name='queryRule']").click(function(){
    /* var itemId = $('#itemId').val();
    if(itemId == ''){
        alert("请选择题目");
        return;
    }
    
    var option = $('#optionId').val();
    if(option == ''){
        alert("请选择选项");
        return;
    } */
    /* var ruleOptionCount = $('#ruleOptionCount').val();
    var maxSelect = $('#maxSelect').val();
    if(ruleOptions != 'all_select'){
        if(parseInt(maxSelect) > parseInt(ruleOptionCount)){
            alert("'最多被选择的次数' 不能大于 '题目中出现的次数'");
            return;
        }
    }
    
    var minSelect = $('#minSelect').val();
    if(parseInt(minSelect) > parseInt(maxSelect)){
        alert("'最多被选择的次数' 不能小于 '最少被选择的次数'");
        return;
    }
    if(minSelect=='' || maxSelect==''){
        alert("'最多被选择的次数' 和 '最少被选择的次数' 都不能为空 ");
        return;
    } */
    var url = "/htgl/group/queryGroupOptionRules.action";
    $("#theform").attr("action",url);
    $("#theform").submit();
});

$('#setOptionRule').on('click',function(){
    var param = {};
    param.groupId = $('#groupId').val();;
    $.post('/htgl/group/voteRuleLayer.action',param,function(data){
        layer.ready(function(){
            layer.open({
                type:1,
                title:'自定义选项被选次数',
                maxmin:true,
                area:['80%','80%'],
                content:data
            })
        });
    });
})

function popup(url){
}

</script>
