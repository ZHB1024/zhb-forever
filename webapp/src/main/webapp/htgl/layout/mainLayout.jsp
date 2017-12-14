<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/all.css" />
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/jquery.main.js"></script>
</head>
<body>
    <div id="header">
        <t:insertAttribute name="header"/>
    </div>
    
    <div id="wrapper">
        <div id="content">
            <div class="c1">
                <t:insertAttribute name="title"/>
                <div class="tabs">
                      <div class="tab">
                        <article>
                            <t:insertAttribute name="main"/>
                        </article>
                      </div>
                </div>
                
            </div>
        </div>
        <t:insertAttribute name="left"/>
    </div>
    
    <div id="footer">
        <t:insertAttribute name="footer"/>
    </div>
</body>
</html>