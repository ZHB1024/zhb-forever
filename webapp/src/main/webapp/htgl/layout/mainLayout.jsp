<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/my.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/bf.css" /> --%>
<link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/bootstrap.min.js"></script>
</head>
<body>
    <div id="header">
        <t:insertAttribute name="header"/>
    </div>
    
    <div id="body">
        <div id="content">
            <div class="c1" id="c1">
                <div id="title"><t:insertAttribute name="title"/></div>
                <div class="tabs" id="contentMain">
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

<script type="text/javascript" >
    $("#body").css('minHeight',$(window).height()-$("#header").height()-$("#footer").height());
    $("#tabs").css('minHeight',$("#body").height()-$("#title").height());
</script>
</body>
</html>