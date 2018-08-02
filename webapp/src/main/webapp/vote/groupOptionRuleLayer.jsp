<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<style>
#selectedOptions div{display: inline;}
</style>

<div align="center">
  <span style="font-weight:bold;">自定义某些选项被选次数:</span>
  
  <select id="itemIdRule">
                <option value="" >请选择题目</option>
                <c:forEach var="item" items="${items}">
                  <option value="${item.id}">Q${item.index}:${item.title}</option>
                </c:forEach>
  </select>
  
  <div id="options">
  </div>
  
  <div id="selectedOptions">
  </div>
  
  <div>
    <span> 最少被选择的次数 </span>
    <select name="minSelect" id="minSelect" required="required">
                <option value="" >请选择选项</option>
    </select>
    <span>，最多被选择的次数 </span>
    <select name="maxSelect" id="maxSelect" required="required">
                <option value="" >请选择选项</option>
    </select>
  </div>
  
  
  <input type="button" name="setOptionRuleSave" id="setOptionRuleSave" value="保 存"/>
  
</div>



<script type="text/javascript">
var optionIds =[]; //全局数组；
$('#itemIdRule').on('change',function() {
    var itemId = $('#itemIdRule').val();
    if(itemId==''){
        return;
    }else{
        var options=$('#itemIdRule option:selected'); 
        var itemIndex = options.text().split(":")[0];
        var param = {};
        param.id = itemId;
        param.flag=false;
        $.post('/htgl/group/getOptionsByItemId.action',param,function(data){
            var rs = $.parseJSON(data);
            var flag = rs.flag;
            if(flag){
                var temp = "";
                for(var i = 0 ; i< rs.o.length ;i++){
                    temp += " <span itemIndex='" + itemIndex + "' title='" + rs.o[i].id + "' onclick='selectedOption(this);'>" + rs.o[i].title + "</span>";
                }
                $('#options').html(temp);
            }else{
                return;
            }
        });
    } 
    
});


function selectedOption(doc){
  var optionid = doc.getAttribute("title");
  var optiontitle = doc.getAttribute("itemIndex") + ":" + doc.innerHTML;
  for(var s=optionIds.length;s--;){
      if(optionIds[s] == optionid){
          alert("不能重复添加");
          return;
      }
  }
  optionIds.push(optionid);
  setMinMaxSelect();
  
  var $a = document.createElement('a');
  $a.href='javascript:void(0);';
  $a.title='删除';
  $a.innerHTML ='<img src="/images/rule/del_icon.gif" alt="删除"  />';
  $a.onclick=function(){
      delOption(optionid);
  }
  
  var $label = document.createElement('label');
  $label.innerHTML = optiontitle;
  
  var $div = document.createElement('div');
  $div.appendChild($label);
  $div.appendChild($a);
 
  //$div.className='bert_item';
  $div.id = optionid + "_selected";
  document.getElementById("selectedOptions").appendChild($div);
}


function delOption(optionid){
    for(var s=optionIds.length;s--;){
        if(optionIds[s] == optionid){
            optionIds.splice(s,1);
        }
    }
    $("#"+ optionid + "_selected").remove();
    setMinMaxSelect();
}

$("#setOptionRuleSave").click(function(){
    if(optionIds.length <=0){
        alert("请选择选项");
        return;
    }
    var maxSelect = $('#maxSelect').val();
     var minSelect = $('#minSelect').val();
    if(minSelect=='' || maxSelect==''){
        alert("'最多被选择的次数' 和 '最少被选择的次数' 都不能为空 ");
        return;
    } 
    
    if(parseInt(maxSelect) > optionIds.length){
        alert("'最多被选择的次数' 不能大于 '选项数'");
        return;
    }
    
   
    if(parseInt(minSelect) > parseInt(maxSelect)){
        alert("'最多被选择的次数' 不能小于 '最少被选择的次数'");
        return;
    }
    
    var param = {};
    param.groupId = $("#groupId").val();
    param.minSelect = minSelect;
    param.maxSelect = maxSelect;
    var optionIdsTemp = '';
    for(var s=0;s<optionIds.length;s++){
        optionIdsTemp += optionIds[s] + ",";
    }
    param.optionIds = optionIdsTemp;
    $.post('/htgl/group/saveOrUpGroupOptionRule.action',param,function(data){
        var rs = $.parseJSON(data);
        var flag = rs.flag;
        if(flag){
            layer.closeAll();
            window.location.reload();
        }else{
            alert(rs.o);
        }
    });
});

function setMinMaxSelect(){
    var length = optionIds.length;
    var min = "<option value=''>请选择</option>";
    min += "<option value='0'>0</option>";
    var max = "<option value=''>请选择</option>";
    var temp ='';
    for(var i=1;i<=length;i++){
        temp += "<option value='"+ i + "'>" + i +"</option>";
    }
    
    $("#minSelect").html(min+temp);
    $("#maxSelect").html(max+temp);
    
    
}

</script>
