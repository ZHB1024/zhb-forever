<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%
    String ctxPath = request.getContextPath();
%>

<script type="text/javascript">
    $(function(){
        var locationhref = window.location.href;
        $("ul.layui-nav a").each(function(){
            if(this.href == locationhref){
                this.parentNode.className = 'layui-this';
                this.parentNode.parentNode.parentNode.className = 'layui-nav-item layui-nav-itemed';
            }
        })
    });
</script>


    <!--侧边栏-->
    <div class="main-layout-side">
        <%--<div class="m-logo">
        </div>--%>
        <ul class="layui-nav layui-nav-tree" lay-filter="leftNav">

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe607;</i>系统管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=ctxPath%>/htgl/initController/initFunctionInfo"  id='menu_set' title="菜单管理"><span class="l-line"></span>菜单管理</a></dd>
                    <dd><a href="<%=ctxPath%>/testController/toLayer"  id='user_set' title="用户管理"><span class="l-line"></span>用户管理</a></dd>
                </dl>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe608;</i>照片管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=ctxPath%>/htgl/initController/initFunctionInfo"  id='upload_photo' title="上传照片"><span class="l-line"></span>上传照片</a></dd>
                    <dd><a href="<%=ctxPath%>/testController/toLayer"  id='scan_photo' title="浏览照片"><span class="l-line"></span>浏览照片</a></dd>
                </dl>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe604;</i>搜索管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=ctxPath%>/htgl/initController/initFunctionInfo"  id='solr_search' title="solr"><span class="l-line"></span>solr</a></dd>
                    <dd><a href="<%=ctxPath%>/testController/toLayer"  id='solr_search_search' title="lucene"><span class="l-line"></span>solr_searchsolr_search</a></dd>
                    <dd><a href="<%=ctxPath%>/testController/toLayer"  id='elastic_search' title="elasticsearch"><span class="l-line"></span>elasticsearch</a></dd>
                </dl>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe60c;</i>人工智能</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=ctxPath%>/htgl/initController/initFunctionInfo"  id='ai_face' title="人脸识别"><span class="l-line"></span>人脸识别</a></dd>
                    <dd><a href="<%=ctxPath%>/testController/toLayer"  id='ai_spider' title="网络爬虫"><span class="l-line"></span>网络爬虫</a></dd>
                </dl>
            </li>

            <%--<li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe60a;</i>RBAC</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;" data-url="email.html" data-id='4' data-text="邮件系统"><i class="iconfont">&#xe603;</i>邮件系统</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe60d;</i>生成静态</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><i class="iconfont">&#xe600;</i>备份管理</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;" data-url="admin-info.html" data-id='5' data-text="个人信息"><i class="iconfont">&#xe606;</i>个人信息</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;" data-url="system.html" data-id='6' data-text="系统设置"><i class="iconfont">&#xe60b;</i>系统设置</a>
            </li>--%>
        </ul>
    </div>