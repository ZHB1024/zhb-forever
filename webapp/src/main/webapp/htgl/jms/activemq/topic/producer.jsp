<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
    <h1>JMS-Topic-Producer</h1>
</div>
  <ul class="states">
     <li class="succes">
    	<form action="<%=ctxPath%>/htgl/jmsActiveMQController/sendTopicMes" method="post">
        MessageText:<textarea name="message"></textarea>
        <input type="submit" value="提交" />
    </form>
    <h2><a href="<%=ctxPath%>/htgl/jmsActiveMQController/index">返回主页</a></h2>
    </li>
  </ul>