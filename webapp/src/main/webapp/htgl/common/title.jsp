<%@page import="com.forever.zhb.utils.WebAppUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.forever.zhb.pojo.UserInfoData"%>
<%
  String ctxPath = request.getContextPath();
  UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
  request.setAttribute("user", userInfo);
%> 

<div class="controls" id="controls">
    <nav class="links" id="links">
      <ul>
        <li><a href="#" class="ico1">Messages <span class="num">26</span></a></li>
        <li><a href="#" class="ico2">Alerts <span class="num">5</span></a></li>
        <li><a href="#" class="ico3">Documents <span class="num">3</span></a></li>
      </ul>
    </nav>
    <div class="profile-box" id="profile-box">
      <span class="profile" id="profile">
        <a href="<%=ctxPath%>/htgl/account/toAccount" class="section">
          <img class="image" src="<%=ctxPath%>/images/my/me.jpg" alt="image description" width="26" height="26" />
          <span class="text-box">
            Welcome
            <strong class="name">${user.realName}</strong>
          </span>
        </a>
        <!-- <a href="#" class="opener">opener</a> -->
      </span>
      <a href="<%=ctxPath%>/htgl/account/toExit" class="btn-on" id="btn-on">On</a>
    </div>
</div>