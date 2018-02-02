<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>所有照片</h1>
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form id="theform" action="">
        <input type="hidden" name="start" value="${start}">
        <c:forEach var="vo" items="${page.list}" varStatus="idx">
           <div class="puimg">
               <a href="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" target="_blank">
                 <img alt="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" src="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" >
               </a>
           </div>
        </c:forEach>
        共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
     <jsp:include page="/page/pageNavigator.jsp" />
        </form>
    </div>
  </div>
