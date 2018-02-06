<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>login log</h1>
  </div>
  <div class="states" id="states">
     <div class="succes">
       <table class="table table-hover table-bordered">
      	<thead>
       		<tr>
        		<th>序号</th>
        		<th>用户名</th>
        		<th>访问IP</th>
        		<th>登录情况</th>
        		<th>访问时间</th>
       		</tr>
      	</thead>
      	<tbody>
       		<c:forEach var="loginLogInfo" items="${page.list}" varStatus="status">
       		<tr>
         		<td>${status.index+1}</td>
         		<td><c:out value="${loginLogInfo.userName}"/></td>
         		<td><c:out value="${loginLogInfo.clientIp}"/></td>
         		<td><c:out value="${loginLogInfo.loginInName}"/></td>
         		<td><fmt:formatDate value="${loginLogInfo.createTime.time}" pattern="yy-MM-dd HH:mm:ss"/></td>
        	</tr>
       		</c:forEach>
      	</tbody>
      </table>
    </div>
    <div class="page">
     共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
     <jsp:include page="/page/pageNavigator.jsp" />
    </div>
  </div>
