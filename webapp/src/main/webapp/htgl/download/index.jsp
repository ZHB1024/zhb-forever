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
       <div align="center">
        <table >
         <tr>
            <c:forEach var="vo" items="${fileVos}" varStatus="idx">
              <td width="50" height="50">
               <a href="<%=ctxPath%>/htgl/fileDownloadController/download?fileName=${vo.name}" target="_blank">
                <img alt="" src="<%=ctxPath%>/htgl/fileDownloadController/download?fileName=${vo.name}">
               </a>
              </td>
<%--  <c:if test="${(idx.count+1)%2 == 0}">
         </tr>
         <tr>
       </c:if> --%>
        </c:forEach>
       </tr>
      </table>
      </div>
    </li>
  </ul>
