<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%@ taglib uri="zhb-forever"  prefix="zhb-forever" %>
<%
  String ctxPath = request.getContextPath();
%> 
  <div class="text-section">
    <h1>query roles</h1>
    <!-- <p>This is a quick overview of some features</p> -->
  </div>
  
  <div class="states" id="states">
   <div class="succes">
    <table class="table table-hover table-bordered">
      <thead>
       <tr>
        <th>角色名称</th>
        <th>描述</th>
        <th>创建时间</th>
        <th>修改时间</th>
       </tr>
      </thead>
      <tbody>
       <c:forEach var="role" items="${roles}">
        <tr>
         <td><c:out value="${role.name}"/></td>
         <td><c:out value="${role.description}"/></td>
         <td><fmt:formatDate value="${role.createTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
         <td><fmt:formatDate value="${role.updateTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
        </tr>
       </c:forEach>
      </tbody>
    </table>
  </div>
  </div>
 
 