<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="zhb-forever" prefix="zhb-forever"%>
<%
	String ctxPath = request.getContextPath();
%>

<div class="text-section">
	<h1>elatic search</h1>
</div>
<div class="states" id="states">
	<div class="succes">
		<form id="theform"
			action="<%=ctxPath%>/htgl/searchController/elasticSearch">
			<input type="hidden" name="start" value="${start}"> <input
				type="text" name="keyword" placeholder="请输入关键字" value="${keyword}" />
			&nbsp; <input type="submit" value="查 询" />

			<c:if test="${ page.size > 0}">
				<table border="1">
					<tr align="center">
						<td>序号</td>
						<td>id</td>
						<td>name</td>
						<td>sex</td>
						<td>age</td>
						<td>birthday</td>
						<td>phone</td>
						<td>email</td>
						<!--  <td>匹配度</td> -->
					</tr>
					<c:forEach var="item" items="${page.list}" varStatus="stas">
						<tr align="center">
							<td>${stas.index+1}</td>
							<td>${item.id}</td>
							<td>${item.name}</td>
							<td>${item.sex}</td>
							<td>${item.age}</td>
							<td>${item.birthday}</td>
							<td>${item.phone}</td>
							<td>${item.email}</td>
							<%--  <td>${item.score}</td> --%>
						</tr>
					</c:forEach>
				</table>
             共：${ page.totalCount } &nbsp; 条  &nbsp;&nbsp;
     <jsp:include page="/page/pageNavigatorSearch.jsp" />
			</c:if>
		</form>
	</div>
</div>

<script type="text/javascript">
$("input[name='del']").click(function(){
	var url = '<%=ctxPath%>
	/luceneController/deleteMessageIndex';
		$("#theform").attr("action", url);
		$("#theform").submit();
	});
</script>

