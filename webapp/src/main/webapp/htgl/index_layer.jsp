<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.forever.zhb.pojo.UserInfoData"%>
<%@page import="com.forever.zhb.util.WebAppUtil"%>
<%
    String ctxPath = request.getContextPath();
    UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
    request.setAttribute("user", userInfo);
%>

<html>

<body>
<div class="wrap-container clearfix">
    <div class="column-content-detail">
        <div align="center" style="font-size: 30px;background: #dfedc0;">
            welcome ${user.realName}
        </div>
    </div>
</div>
</body>

</html>