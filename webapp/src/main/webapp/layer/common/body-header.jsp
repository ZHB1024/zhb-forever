<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.forever.zhb.pojo.UserInfoData"%>
<%@page import="com.forever.zhb.util.WebAppUtil"%>
<%
    String ctxPath = request.getContextPath();
    UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
    request.setAttribute("user", userInfo);
%>

<!--头部-->
<div class="main-layout-header">
    <div class="menu-btn" id="hideBtn">
        <a href="javascript:;">
            <span class="iconfont">&#xe60e;</span>
        </a>
    </div>
    <ul class="layui-nav" lay-filter="rightNav">
        <li class="layui-nav-item"><a href="javascript:;" data-url="email.html" data-id='4' data-text="邮件系统"><i class="iconfont">&#xe603;</i></a></li>
        <li class="layui-nav-item">
            <a href="<%=ctxPath%>/htgl/account/toAccount?userId=${user.id}" >${user.realName}</a>
        </li>
        <li class="layui-nav-item"><a href="<%=ctxPath%>/htgl/account/toExit">退出</a></li>
    </ul>
</div>