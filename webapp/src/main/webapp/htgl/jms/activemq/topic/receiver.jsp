<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
    <h1>Topic Receiver</h1>
</div>
  <ul class="states">
     <li class="succes">
    	<h1>${textMessage }</h1>
    	<h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/index">返回主页</a></h2>
    </li>
  </ul>