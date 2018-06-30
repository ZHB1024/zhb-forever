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
        <li class="layui-this">修改基本信息</li>
        <li>修改密码</li>
    </ul>
    <div class="layui-tab-content">

        <%--基本信息--%>
        <div class="layui-tab-item layui-show">
            <form class="layui-form" style="float:left;margin:15px;padding:10px;width:65%;" action="<%=ctxPath%>/htgl/account/upAccount" method="post">
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
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="submit" value="修 改" class="layui-btn layui-btn-normal" lay-submit lay-filter="adminInfo"/>
                    </div>
                </div>
            </form>
            <div class="head_image">
                <img id="me_image" src="<%=ctxPath%>/images/my/me.jpg" alt="image description"/>
                <%--<input style="clear:both;width:100px;height: 30px" type="button" value="上传新照片"/>--%>
                <button type="button"  class="layui-btn" id="uploadNewPhoto"><i class="layui-icon"></i>上传新照片</button>
            </div>
        </div>

        <%--修改密码--%>
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
                    <label class="layui-form-label">再次输入新密码：</label>
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

<script type="text/javascript">
    layui.use('upload', function(){
        var $ = layui.jquery;
        var upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1',
            url: '/upload/',
            auto: false,
            bindAction: '#uploadButton',
            before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    debugger;
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            },
            done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败');
                }
                //上传成功
            },
            error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });
    });
</script>