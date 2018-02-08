<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
<link href="/test/css/my.css" rel="stylesheet" media="screen">
<link href="/test/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=ctxPath%>/test/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/test/js/bootstrap.min.js"></script>
</head>
<body>
    <div id="header">
        <t:insertAttribute name="header"/>
    </div>
    
    <div id="body">
        <div id="left">
        	<t:insertAttribute name="left"/>
        </div>
        <div id="right">
         	<div id="title">
         		<t:insertAttribute name="title"/>
         	</div>
            <div id="content">
           		<t:insertAttribute name="main"/>
         	</div>
        </div>
    </div>
    
    <div id="footer">
        <t:insertAttribute name="footer"/>
    </div>

<script type="text/javascript" >
    $("#body").css('minHeight',$(window).height()-$("#header").height()-$("#footer").height());
    /* $("#tabs").css('minHeight',$("#body").height()-$("#title").height()); */
</script>
</body>
</html>