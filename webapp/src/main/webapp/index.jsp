<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.8.0.js"></script>


<div align="center">
<select name="ajaxName">
     <option value=''>--分类--</option>
</select>
<input type="button" value="确 定" id="ajaxId">
</div>

<script type="text/javascript">
$("#ajaxId").click(function(){
	var url = "/testController/ajaxTest";
    var result = "<option value='' selected>--分类--</option>";
    $.post(url,function(data){
        var rs = $.parseJSON(data),
            o = rs.o;
        for(i in o){
            result += "<option value='"+o[i]+"' title='"+o[i]+"'>"+o[i]+"</option>";
        }
        $("select[name=ajaxName]").html(result);
    });
}); 

/* $("#ajaxId").click(function(){
	var url = "/testController/ajaxResponseBody";
    var result = "<option value='' selected>--分类--</option>";
    $.post(url,function(data){
    	console.log(data)
    	var value = data.name;
        var o = $.parseJSON(data);
        var ddd = o.name;
    });
}); */


function getCategory(){
    var url = "/htgl/library/getCatetory.action";
    var result = "<option value='' selected>--分类--</option>";
    $.ajaxSettings.async = false;
    $.post(url,function(data){
        var rs = $.parseJSON(data),
            o = rs.o,
            tit = "";
        for(i in o){

            if(o[i].title.length > 20){
                tit = o[i].title.substring(0,20) + "...";
            }else{
                tit = o[i].title;
            }
            result += "<option value='"+o[i].id+"' title='"+o[i].title+"'>"+tit+"</option>";
        }
    });
    $.ajaxSettings.async = true;
    return result;
}
</script>