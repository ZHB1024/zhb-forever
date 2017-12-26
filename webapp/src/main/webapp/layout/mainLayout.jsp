<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<html>
<head>
<title>LOVE</title>
</head>
<body>
    <%-- <div id="header">
        <t:insertAttribute name="header"/>
    </div> --%>
    <div id="content">
        <t:insertAttribute name="main"/>
    </div>
    <%-- <div id="footer">
        <t:insertAttribute name="footer"/>
    </div> --%>
</body>
</html>