<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String conPath = request.getContextPath();
System.out.println(conPath);
%>
<div align="center">
    <h1>success</h1>
  </div>
  
  <div align="center">
    <table border="1">
      <tr>
        <td><c:out value="${redis.key}"/></td>
      </tr>
      <c:forEach var="country" items="${redis.values}">
       <tr>
         <td><c:out value="${country}"/></td>
       </tr>
      </c:forEach>
    </table>
    
  </div>