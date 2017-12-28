<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
    <h1>index</h1>
</div>
  <ul class="states">
     <li class="succes">
    	<h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/toQueueProducer">去发Queue消息</a></h2>
    	<h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/receiveQueueMes">从Queue中取一个消息</a></h2>
    	<br>
    	<h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/toTopicProducer">去发Topic消息</a></h2>
    	<h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/receiveTopicMes">从Topic中取一个消息</a></h2>
    </li>
  </ul>
