<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
	<h1>mail-result</h1>
</div>

<div class="states" id="states">
	<div class="succes">
		<span style="color: red;">
		 <c:out value="${result}" escapeXml="false" />
	    </span>
	</div>
</div>
