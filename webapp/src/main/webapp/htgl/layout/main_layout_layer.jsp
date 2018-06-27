<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
    String ctxPath = request.getContextPath();
%>
<html>
<head>
    <title>清风舞明月</title>

    <script type="text/javascript" src="<%=ctxPath%>/js/jquery-3.3.1.js"></script>
    <script src="<%=ctxPath%>/layer/static/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=ctxPath%>/layer/static/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=ctxPath%>/layer/static/admin/js/main.js" type="text/javascript" charset="utf-8"></script>

    <%--弹出层--%>
    <%--<script type="text/javascript" src="<%=ctxPath%>/js/layer/layer.js"></script>--%>

    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/static/admin/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/static/admin/css/admin.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/static/admin/css/my.css"/>

</head>
<body>
    <div id="header">
        <t:insertAttribute name="header"/>
    </div>

    <div class="main-layout" id="body">
        <!--左侧菜单-->
        <t:insertAttribute name="left"/>
        <!--右侧内容-->
        <div class="main-layout-container">
            <!--头部-->
            <t:insertAttribute name="body-header"/>

            <!--主体内容-->
            <div class="main-layout-body">
                <!--tab 切换-->
                <div class="layui-tab layui-tab-brief main-layout-tab" lay-filter="tab" lay-allowClose="true">
                    <ul class="layui-tab-title">
                        <li class="layui-this welcome" style="font-size: 20px;">welcome</li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="layui-tab-item layui-show" style="background: #f5f5f5;">
                            <t:insertAttribute name="main"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="footer">
        <t:insertAttribute name="footer"/>
    </div>

    <script type="text/javascript" >
        $("#body").css('minHeight',$(window).height()-$("#header").height()-$("#footer").height());
    </script>

</body>
</html>
