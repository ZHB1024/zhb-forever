<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>solr search</h1>
  </div>
  <ul class="states">
     <li class="succes">
       <form id="theform" action="<%=ctxPath %>/searchController/searchKonwSolr">
      <input type="hidden" name="start" value="${start}">
      <input type="text" name="keyword" placeholder="请输入关键字" value="${keyword}"/>
      &nbsp;
      <input type="submit" value="查 询"/>
      
      <br/>
      
    <c:if test="${ konws.size > 0}">
     <table border="1" >
      <tr align="center">
        <td>序号</td>
        <td>creater</td>
        <td>title</td>
        <td>content</td>
      </tr>
      <c:forEach var="item" items="${konws.list}" varStatus="stas">
       <tr align="center">
         <td>${stas.index+1}</td>
         <td>${item.creater}</td>
         <td>${item.title}</td>
         <td>${item.content}</td>
       </tr>
      </c:forEach>
     </table>
             共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
     <jsp:include page="/page/pageNavigator.jsp" />
    </c:if>
   </form>
     </li>
  </ul>