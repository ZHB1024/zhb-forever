<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>spider</h1>
  </div>
  <ul class="states">
     <li class="succes">
     <div align="center">
     <form id="theform" action="<%=ctxPath %>/htgl/spiderController/spider">
      <input type=text name="url" placeholder="请输入访问地址" value="${url}"/>
      <br>
      <input type=text name="keys" placeholder="请输入参数" value="${keys}"/>
      <br>
      <input type="button" value="查 询" name="spider1"/>
      &nbsp;
      <input type="button" value="查 询" name="spider2"/>
      </form>
      
       <table>
        <tr>
          <td>响应值</td>
         </tr>
          <tr>
           <td><c:out value="${content}"/></td>
           </tr>
         </table>
      </div>
     </li>
  </ul>
  
<script type="text/javascript">
$("input[name='spider1']").click(function(){
	var url = '<%=ctxPath %>/htgl/spiderController/spider1';
	$("#theform").attr("action",url);
	$("#theform").submit();
});
$("input[name='spider2']").click(function(){
	var url = '<%=ctxPath %>/htgl/spiderController/spider2';
	$("#theform").attr("action",url);
	$("#theform").submit();
});
</script> 
