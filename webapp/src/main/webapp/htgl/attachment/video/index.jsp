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
        <form id="theform" class="layui-form" action="<%=ctxPath%>/htgl/attachmentController/videoIndex" method="post">
            <div class="layui-form-item">
                <div class="layui-inline tool-btn">
                    <a class="layui-btn layui-btn-small layui-btn-normal addBtn" href="<%=ctxPath%>/htgl/attachmentController/toUploadVideo"><i class="layui-icon">&#xe654;</i></a>
                    <button class="layui-btn layui-btn-small layui-btn-danger delBtn" onclick="submitAction('deleteAccount');"><i class="layui-icon">&#xe640;</i></button>
                </div>
                <div class="layui-inline">
                    <input type="text" name="fileName" value="${fileName}" placeholder="请输入视频名字" autocomplete="off" class="layui-input">
                </div>
                <input type="submit" class="layui-btn layui-btn-normal" lay-submit="search" value="搜索">
            </div>

        </form>

        <div class="attachment_list">
            <div class="attachment_success">
                <c:forEach var="vo" items="${page.list}" varStatus="idx">
                    <div class="puimg">
                        <video controls src="<%=ctxPath%>/htgl/attachmentController/download?id=${vo.id}" preload="auto">
                            您的浏览器不支持 video 标签，请升级浏览器。
                        </video>
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

    /*弹出层 照片信息*/
    function showPictureInfo(fileName,fileSize,fileId){
        var url = '<%=ctxPath%>/htgl/fileDownloadController/download?id=' + fileId;
        var img = "<div style='width:500px ;height:600px;'><img src='" + url + "' alt='" + fileName + "'/></div>";
        var info = "<div>" +  fileName + fileSize + "</div>";
        var contents = img +  info;

        layer.open({
            title: '照片信息',
            type: 1,
            //skin: 'layui-layer-rim', //加上边框
            area: ['800px','600px'], //宽高
            content: contents,
            btn: ['确定'],
            success: function(layero, index){

            },
            yes: function(index, layero){
                layer.closeAll();
            }
        });

    }
</script>