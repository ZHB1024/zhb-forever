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
	function addUser(){
		var form = document.forms[0];
		form.action="<%=conPath%>/htgl/user?action=addUser";
		form.method="post";
		form.submit();
	}
</script>
</head>
<body>
   <p align="center">
      <h1>添加用户</h1>
     <form name="userForm" action="<%=conPath%>/htgl/user?action=addUser" method="post">
		姓名：<input type="text" name="userName">
		年龄：<input type="text" name="age">
		<input type="submit" value="添加">
	 </form>
   </p>
</body>
</html>