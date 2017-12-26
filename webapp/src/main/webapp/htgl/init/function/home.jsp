<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>query functions</h1>
    <!-- <p>This is a quick overview of some features</p> -->
  </div>
  <ul class="states">
   <li class="succes">
    <div align="center">
    <table border="1" width="400">
      <tr>
        <td>功能名称</td>
        <td>描述</td>
        <td>创建时间</td>
        <td>修改时间</td>
      </tr>
      <c:forEach var="function" items="${functions}">
       <tr>
         <td><c:out value="${function.name}"/></td>
         <td><c:out value="${function.description}"/></td>
         <td><fmt:formatDate value="${function.createTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
         <td><fmt:formatDate value="${function.updateTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
       </tr>
      </c:forEach>
    </table>
  </div>
 </li>
</ul>
  
