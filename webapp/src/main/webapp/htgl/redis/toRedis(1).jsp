<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String conPath = request.getContextPath();
System.out.println(conPath);
%>
<html>
<head>
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function addRedis(){
		var form = document.forms[0];
		form.action="<%=conPath%>/htgl/redis?action=addRedis";
		form.method="post";
		form.submit();
	}
</script>
</head>
<body>
     <div align="center">
      <div align="center">
       <h1>添加List</h1>
      </div>
      <div align="center">
       <form name="userForm" action="<%=conPath%>/htgl/redis?action=addRedis" method="post">
		countries：<input type="text" name="country">
		<input type="submit" value="添加">
	   </form>
      </div>
      
	 </div>
</body>
</html>