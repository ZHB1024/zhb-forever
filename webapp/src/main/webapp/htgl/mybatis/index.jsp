<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div align="center">
    <h1>query users</h1>
  </div>
  
  <div align="center" >
    <table border="1" width="400">
      <tr>
        <td>id</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>密码</td>
      </tr>
      <c:forEach var="user" items="${users}">
       <tr>
         <td><c:out value="${user.id}"/></td>
         <td><c:out value="${user.userName}"/></td>
         <td><c:out value="${user.age}"/></td>
         <td><c:out value="${user.password}"/></td>
       </tr>
      </c:forEach>
    </table>
    
  </div>
 
 