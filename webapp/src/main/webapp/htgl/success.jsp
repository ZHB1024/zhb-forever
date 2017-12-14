<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div align="center">
    <h1>add success</h1>
  </div>
  
  <div align="center">
    <table border="1">
      <tr>
        <td>姓名</td>
        <td>年龄</td>
      </tr>
      <c:forEach var="user" items="${users}">
       <tr>
         <td><c:out value="${user.userName}"/></td>
         <td><c:out value="${user.age}"/></td>
       </tr>
      </c:forEach>
    </table>
    
  </div>
 
 