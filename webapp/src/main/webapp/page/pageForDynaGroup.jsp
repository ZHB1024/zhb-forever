<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.forever.zhb.page.Page" %>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
  Page pageRs = (Page)request.getAttribute("page");
  String fromVal = (String)request.getAttribute("formValue");//提交表单的id,需要从后台传递
  String strUrl = (String)request.getAttribute("urlVal");//访问的action地址
  int lastPage = pageRs.getStartOfLastPage();
  int previousPage = pageRs.getStartOfPreviousPage();
  int nextPage = pageRs.getStartOfNextPage();
%>
<font>
<c:if test="${page.curPage == 1}">
首页&nbsp;&nbsp;上页
</c:if>
<c:if test="${page.previousPageAvailable == true}">
    <a href='javascript:firstPage();' style=" text-decoration:underline; color:#0000FF">首页</a>&nbsp;&nbsp;<a href='javascript:previousPage();' style=" text-decoration:underline; color:#0000FF">上页</a>
</c:if>
<c:if test="${page.nextPageAvailable == true}">
 &nbsp;&nbsp;<a href='javascript:nextPage();' style=" text-decoration:underline; color:#0000FF">下页</a>&nbsp;&nbsp;<a href='javascript:lastPage();' style=" text-decoration:underline; color:#0000FF">末页</a>
</c:if>
<c:if test="${page.curPage == page.totalPage}">
&nbsp;&nbsp;下页&nbsp;&nbsp;末页
</c:if>
&nbsp;&nbsp;
<select onchange="gotoPage(this)">
</select>
<script language="javascript">

function firstPage(){
    var url="<%=strUrl%>" + "?start=0";
    $("<%=fromVal%>").attr("action",url);
    $("<%=fromVal%>").submit();
}

function previousPage(){
    var url="<%=strUrl%>" + "?start=" + <%=previousPage%>;
    $("<%=fromVal%>").attr("action",url);
    $("<%=fromVal%>").submit();
}

function nextPage(){
    var url="<%=strUrl%>" + "?start=" + <%=nextPage%>;
    $("<%=fromVal%>").attr("action",url);
    $("<%=fromVal%>").submit();
}

function lastPage(){
    var url="<%=strUrl%>" + "?start=" + <%=lastPage%> ;
    $("<%=fromVal%>").attr("action",url);
    $("<%=fromVal%>").submit();
}

function gotoPage(selectObj){
    var page = selectObj.value;
    var start = (page - 1) * <c:out value="${page.pageCount}"/>;
    var url="<%=strUrl%>" + "?start=" + start;
    $("<%=fromVal%>").attr("action",url);
    $("<%=fromVal%>").submit();
}

</script>
</font>