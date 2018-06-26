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
        <li class="layui-this">修改资料</li>
        <li>修改密码</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="layui-form"  style="width: 90%;padding-top: 20px;" action="<%=ctxPath%>/htgl/account/upAccount" method="post">
                <input type="hidden" name="id" value="${account.userInfoData.id}" />
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" readOnly maxlength="30" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input" value="${account.userInfoData.name}" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">真实姓名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="realName" readOnly maxlength="30" required  lay-verify="required" placeholder="请输入真实姓名" autocomplete="off" class="layui-input" value="${account.userInfoData.realName}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性别：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="sex" value="男" title="男" <c:if test="${account.userInfoData.sex=='男'}">checked</c:if> />
                        <input type="radio" name="sex" value="女" title="女" <c:if test="${account.userInfoData.sex=='女'}">checked</c:if> />
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">手机号：</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone" required  lay-verify="required" placeholder="请输入手机号" autocomplete="off" class="layui-input" value="${account.userInfoData.phone}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱：</label>
                    <div class="layui-input-block">
                        <input type="email" name="email" required  lay-verify="required" placeholder="请输入邮箱" autocomplete="off" class="layui-input" value="${account.userInfoData.email}">
                    </div>
                </div>
                <%--<div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注：</label>
                    <div class="layui-input-block">
                        <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>--%>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="submit" value="修 改" class="layui-btn layui-btn-normal" lay-submit lay-filter="adminInfo"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-tab-item">
            <form class="layui-form"  style="width: 90%;padding-top: 20px;" action="<%=ctxPath%>/htgl/account/modifyPassword" method="post">
                <input type="hidden" name="userId" value="${account.userInfoData.id}" />
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名：</label>
                    <div class="layui-input-block">
                        <input type="text" name="username" disabled autocomplete="off" class="layui-input layui-disabled" value="${account.userInfoData.name}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">旧密码：</label>
                    <div class="layui-input-block">
                        <input type="password" name="password" required lay-verify="required" placeholder="请输入旧密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码：</label>
                    <div class="layui-input-block">
                        <input type="password" name="newPassword" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">重复密码：</label>
                    <div class="layui-input-block">
                        <input type="password" name="confirmPassword" required lay-verify="required" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="submit" value="修 改" class="layui-btn layui-btn-normal" lay-submit lay-filter="adminPassword" />
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>