<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctxPath = request.getContextPath();
%>
<html>
<body>
<div class="layui-tab page-content-wrap">
    <ul class="layui-tab-title">
        <li class="layui-this">新增用户</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form"  style="width: 90%;padding-top: 20px;" action="<%=ctxPath%>/htgl/account/addUser" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input " >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">真实姓名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="realName" required  lay-verify="required" placeholder="请输入真实姓名" autocomplete="off" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性别：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="sex" value="男" title="男" checked=""/>
                        <input type="radio" name="sex" value="女" title="女" />
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">手机号：</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone" required  lay-verify="required" placeholder="请输入手机号" autocomplete="off" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱：</label>
                    <div class="layui-input-block">
                        <input type="email" name="email" required  lay-verify="required" placeholder="请输入邮箱" autocomplete="off" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="submit" value="确 定" class="layui-btn layui-btn-normal" lay-submit lay-filter="adminInfo"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>