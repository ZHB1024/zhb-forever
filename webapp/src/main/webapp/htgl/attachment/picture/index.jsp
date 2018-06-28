<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
    String ctxPath = request.getContextPath();
%>
<html class="iframe-h">

<body>
<div class="wrap-container clearfix">
    <div class="column-content-detail">
        <form id="theform" class="layui-form" action="<%=ctxPath%>/htgl/attachmentController/pictureIndex" method="post">
            <div class="layui-form-item">
                <div class="layui-inline tool-btn">
                    <a class="layui-btn layui-btn-small layui-btn-normal addBtn" href="<%=ctxPath%>/htgl/attachmentController/toUploadPicture"><i class="layui-icon">&#xe654;</i></a>
                    <button class="layui-btn layui-btn-small layui-btn-danger delBtn" onclick="submitAction('deleteAccount');"><i class="layui-icon">&#xe640;</i></button>
                </div>
                <div class="layui-inline">
                    <input type="text" name="fileName" value="${fileName}" placeholder="请输入照片名字" autocomplete="off" class="layui-input">
                </div>
                <input type="submit" class="layui-btn layui-btn-normal" lay-submit="search" value="搜索">
            </div>

        </form>

        <div class="picture_list" id="picture_list">
            <div class="picture_succes">
                <c:forEach var="vo" items="${page.list}" varStatus="idx">
                    <div class="puimg">
                        <a href="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" target="_blank">
                            <img alt="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" src="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" >
                        </a>
                    </div>
                </c:forEach>
            </div>

        </div>

    </div>

    <div class="page_navigator">
        共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
        <jsp:include page="/page/pageNavigator.jsp" />
    </div>
</div>


</body>

</html>

<script type="text/javascript">
    /*提交表单*/
    function submitAction(action){
        var url = '<%=ctxPath%>/htgl/account/'+ action;
        $("#theform").attr("action",url);
        $("#theform").submit();
    }

    /*获取用户信息*/
    function showUserInfo(name){
        var url = '/htgl/account/getAccount';
        $.ajax({
            url:url,
            type:"POST",
            traditional:true,
            data: {'name':name},
            success:function(data){
                popup(JSON.parse(data));
            },
            error:function(){
                alert('网络异常，请刷新页面重试！');
            }
        });
    }

    /*弹出层*/
    function popup(data){
        var str = '<table align="center" style="border-collapse:separate; border-spacing:10px;">';

        str += generatorValue("用户名",data.name);
        str += generatorValue("姓名",data.realName);
        str += generatorValue("性别",data.sex);
        str += generatorValue("手机号",data.phone);
        str += generatorValue("电子邮箱",data.email);

        str += '</table>';
        layer.open({
            title: '详细信息',
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['420px', '340px'], //宽高
            content: str, //这里content是一个普通的String
            btn: ['确定'],
            success: function(layero, index){

            },
            yes: function(index, layero){
                layer.closeAll();
            }
        });
    }

    function generatorValue(name,value){
        var str ='';
        str += '<tr>';
        str += '<th>';
        str += name + '：';
        str += '</th>';
        str += '<td>';
        str += value;
        str += '</td>';
        str += '</tr>';
        return str;
    }

</script>