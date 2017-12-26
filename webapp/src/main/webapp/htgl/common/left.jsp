<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%>

<aside id="sidebar">
 <strong class="logo"><a href="javascript:history.go(-1);">返回</a></strong>
   <ul class="tabset buttons">
     <li <c:if test="${active1}">class="active"</c:if> >
        <form id="theform1" action="<%=ctxPath%>/htgl/initController/initFunctionInfo" method="post">
           <a href="javascript:tileBut(1);" class="ico1"><span>功能管理</span><em></em></a>
            <span class="tooltip"><span>功能管理</span></span>
        </form>
      </li>
                
      <li <c:if test="${active2}">class="active"</c:if>>
          <form id="theform2" action="<%=ctxPath%>/htgl/initController/initRoleInfo" method="post">
             <a href="javascript:tileBut(2);" class="ico2"><span>角色管理</span><em></em></a>
             <span class="tooltip"><span>角色管理</span></span>
          </form>
      </li>
                
      <li <c:if test="${active3}">class="active"</c:if> >
           <form id="theform3" action="<%=ctxPath%>/htgl/beetlController/beetl" method="post">
               <a href="javascript:tileBut(3);" class="ico3"><span>beetl</span><em></em></a>
               <span class="tooltip"><span>beetl</span></span>
           </form>
      </li>
                
      <li <c:if test="${active4}">class="active"</c:if> >
           <form id="theform4" action="<%=ctxPath%>/htgl/mongodb?action=findAllMongoDB" method="post">
              <a href="javascript:tileBut(4);" class="ico4"><span>mongodb</span><em></em></a>
              <span class="tooltip"><span>mongodb</span></span>
           </form>
       </li>
                
       <li <c:if test="${active5}">class="active"</c:if> >
            <form id="theform5" action="<%=ctxPath%>/htgl/uploadController/toUpload" method="post">
              <a href="javascript:tileBut(5);" class="ico5"><span>toUpload</span><em></em></a>
              <span class="tooltip"><span>upload</span></span>
           </form>
       </li>
                
       <li <c:if test="${active6}">class="active"</c:if> >
            <form id="theform6" action="<%=ctxPath%>/htgl/fileDownloadController/toDownload" method="post">
              <a href="javascript:tileBut(6);" class="ico6"><span>toDownload</span><em></em></a>
              <span class="tooltip"><span>download</span></span>
           </form>
           
           <!-- <a href="#tab-6" class="ico6">
            <span>Comments</span><em></em>
           </a>
           <span class="num">11</span>
           <span class="tooltip"><span>Comments</span></span> -->
       </li>
                
       <li <c:if test="${active7}">class="active"</c:if> >
           <a href="#tab-7" class="ico7"><span>Plug-in</span><em></em></a>
           <span class="tooltip"><span>Plug-in</span></span>
       </li>
                
       <li <c:if test="${active8}">class="active"</c:if> >
           <a href="#tab-8" class="ico8"><span>Settings</span><em></em></a>
           <span class="tooltip"><span>Settings</span></span>
       </li>
                
   </ul>
   <span class="shadow"></span>
</aside>
<script type="text/javascript">
function tileBut(num){
    $("#theform" + num).submit();
}
</script>