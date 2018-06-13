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

    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/static/admin/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/static/admin/css/admin.css"/>



    <%--<script src="<%=ctxPath%>/layer/layui/layui.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=ctxPath%>/layer/js/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=ctxPath%>/layer/js/main.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/layer/css/admin.css"/>--%>


</head>

<body>
<div class="main-layout" id='main-layout'>

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
                    <li class="layui-this welcome">后台主页</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show" style="background: #f5f5f5;">
                        <!--1-->
                        <%--<iframe src="<t:insertAttribute name="main"/>" width="100%" height="100%" name="iframe" scrolling="auto" class="iframe" framborder="0"></iframe>--%>
                        <!--1end-->

                        <%--具体页面--%>
                        <t:insertAttribute name="main"/>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--遮罩-->
    <div class="main-mask">

    </div>
</div>
<script type="text/javascript">
    var scope={
        link:'./welcome.html'
    }
</script>

</body>
</html>
