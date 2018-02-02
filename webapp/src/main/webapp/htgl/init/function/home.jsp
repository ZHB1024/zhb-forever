<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>query functions</h1>
    <!-- <p>This is a quick overview of some features</p> -->
  </div>
  <div class="states" id="states">
   <div class="succes">
    <table class="table table-hover table-bordered">
     <thead>
      <tr>
        <th>功能名称</th>
        <th>描述</th>
        <th>创建时间</th>
        <th>修改时间</th>
      </tr>
     </thead>
     <tbody>
      <c:forEach var="function" items="${functions}">
       <tr>
         <td><c:out value="${function.name}"/></td>
         <td><c:out value="${function.description}"/></td>
         <td><fmt:formatDate value="${function.createTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
         <td><fmt:formatDate value="${function.updateTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
       </tr>
      </c:forEach>
      </tbody>
    </table>
 </div>
</div>
  
