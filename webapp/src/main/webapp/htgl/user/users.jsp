<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="zhb-forever" prefix="zhb-forever"%>
<%
    String ctxPath = request.getContextPath();
%>
<html class="iframe-h">

<body>
<div class="wrap-container clearfix">
    <div class="column-content-detail">
        <form id="theform" class="layui-form" action="" method="post">
            <div class="layui-form-item">
                <div class="layui-inline tool-btn">
                    <a class="layui-btn layui-btn-small layui-btn-normal addBtn" href="<%=ctxPath%>/htgl/account/toAddUser"><i class="layui-icon">&#xe654;</i></a>
                    <button class="layui-btn layui-btn-small layui-btn-danger delBtn" onclick="submitAction('deleteAccount');"><i class="layui-icon">&#xe640;</i></button>
                    <%--<button class="layui-btn layui-btn-small layui-btn-warm listOrderBtn hidden-xs" data-url="article-add.html"><i class="iconfont">&#xe656;</i></button>--%>
                </div>
                <div class="layui-inline">
                    <input type="text" name="title" required lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <select name="states" lay-filter="status">
                        <option value="">请选择一个状态</option>
                        <option value="010">正常</option>
                        <option value="021">停止</option>
                        <option value="0571">删除</option>
                    </select>
                </div>
                <button class="layui-btn layui-btn-normal" lay-submit="search">搜索</button>
            </div>
        </form>

        <form>
            <div class="layui-form" id="table-list">
                <table class="layui-table" lay-even lay-skin="nob">
                    <thead>
                    <tr>
                        <th><input type="checkbox" name="allChoose" lay-skin="primary" lay-filter="allChoose"></th>
                        <%--<th class="hidden-xs">ID</th>
                        <th class="hidden-xs">排序</th>--%>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>性别</th>
                        <th>手机号</th>
                        <th>电子邮箱</th>
                        <th class="hidden-xs">创建时间</th>
                        <th class="hidden-xs">修改时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${page.list}">
                        <tr>
                            <td><input type="checkbox" name="userId" lay-skin="primary" value="${user.id}"></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.realName}"/></td>
                            <td><c:out value="${user.sex}"/></td>
                            <td><c:out value="${user.phone}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td class="hidden-xs"><fmt:formatDate value="${user.createTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
                            <td class="hidden-xs"><fmt:formatDate value="${user.updateTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
                            <td><zhb-forever:deleteFlag delete="${user.deleteFlag}"/></td>
                            <td>
                                <div class="layui-inline">
                                    <%--<a class="layui-btn layui-btn-small layui-btn-normal go-btn" id="updateBtn" href="<%=ctxPath%>/htgl/account/toAccount?userId=${user.id}"><i class="layui-icon">&#xe642;</i></a>--%>
                                    <a class="layui-btn layui-btn-small layui-btn-danger del-btn" id="deleteBtn" href="<%=ctxPath%>/htgl/account/deleteAccount?userId=${user.id}"><i class="layui-icon">&#xe640;</i></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="page-wrap">
                    共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
                    <jsp:include page="/page/pageNavigator.jsp" />
                </div>
            </div>
        </form>
    </div>
</div>
</body>

</html>

<script type="text/javascript">
    function submitAction(action){
        var url = '<%=ctxPath%>/htgl/account/'+ action;
        $("#theform").attr("action",url);
        $("#theform").submit();
    }
</script>