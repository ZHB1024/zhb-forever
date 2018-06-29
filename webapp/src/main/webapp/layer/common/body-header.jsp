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
    <ul class="layui-nav">
        <li class="layui-nav-item">
            <a href="<%=ctxPath%>/htgl/account/toAccount?userId=${user.id}">
                <img class="image" src="<%=ctxPath%>/images/my/me.jpg" alt="image description" width="50" height="50" />
            </a>
        </li>
        <li class="layui-nav-item">
            <a href="<%=ctxPath%>/htgl/account/toAccount?userId=${user.id}" style="background: #444c63">${user.realName}</a>
        </li>
        <li class="layui-nav-item"><a href="<%=ctxPath%>/htgl/account/toExit" style="background: #444c63">退出</a></li>
    </ul>
</div>