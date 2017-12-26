<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>所有照片</h1>
  </div>
  <ul class="states">
     <li class="succes">
       <!-- <div class="puimg"> -->
        <c:forEach var="vo" items="${fileInfos}" varStatus="idx">
           <div class="puimg"></div>
           <span <c:if test="${(idx.count)%3 == 0}">class="nomarginR"</c:if> >
               <a href="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" target="_blank">
                 <img alt="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" src="<%=ctxPath%>/htgl/fileDownloadController/download?id=${vo.id}" >
               </a>
           </span>
        </c:forEach>
      <!-- </div> -->
    </li>
  </ul>
