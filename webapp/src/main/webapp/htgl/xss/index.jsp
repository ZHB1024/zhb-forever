<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>account</h1>
    <!-- <p>个人信息</p> -->
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form id="theform" action="" method="POST" >  
		 First Name: <input type="text" name="first_name">  
		 <br />  
		 Last Name: <input type="text" name="last_name" value="${last_name}"/>  
		 <input type="button" name="save" value="确定" />  
		 <a href="javascript:void(0)" onclick="checkDraftSend();return false;" title="存草稿">
		  存草稿
         </a>
	   </form>  
     </div>
  </div>
 
 <script type="text/javascript">
 
 $("input[name='save']").click(function(){
	 var url ='<%=ctxPath %>/htgl/xssController/testXSS';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 });
 
 function checkDraftSend(){
	 var url ='<%=ctxPath %>/htgl/xssController/testXSS';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 }
 
</script>
 