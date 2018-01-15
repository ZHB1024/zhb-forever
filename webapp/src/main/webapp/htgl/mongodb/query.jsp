<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>mongodb</h1>
    <!-- <p>query result</p> -->
  </div>
  <ul class="states">
     <li class="succes">
     <div align="center">
       <table>
        <tr>
          <td>姓名</td>
          <td>性别</td>
         </tr>
         <c:forEach var="user" items="${users}">
          <tr>
           <td><c:out value="${user.name}"/></td>
           <td><c:out value="${user.sex}"/></td>
           </tr>
          </c:forEach>
         </table>
      </div>
     </li>
  </ul>
