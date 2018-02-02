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
  <div class="states" id="states">
     <div class="succes">
      <table class="table table-hover table-bordered">
       <thead>
        <tr>
          <th>姓名</th>
          <th>性别</th>
         </tr>
         </thead>
         <tbody>
         <c:forEach var="user" items="${users}">
          <tr>
           <td><c:out value="${user.name}"/></td>
           <td><c:out value="${user.sex}"/></td>
           </tr>
          </c:forEach>
          </tbody>
         </table>
     </div>
  </div>
