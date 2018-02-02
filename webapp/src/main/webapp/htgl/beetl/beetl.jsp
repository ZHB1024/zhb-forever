<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>beetl</h1>
    <!-- <p>This is a quick overview of some features</p> -->
  </div>
  <div class="states" id="states">
     <div class="succes">
       <c:out value="${content}"/>
     </div>
  </div> 

