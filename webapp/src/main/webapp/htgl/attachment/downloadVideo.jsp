<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
	<h1>downloadVideo</h1>
</div>

<div class="states" id="states">
	<div class="succes">
		<form id="theform" action="">
			<input type="hidden" name="start" value="${start}">
			<c:forEach var="vo" items="${page.list}" varStatus="idx">
				<div class="puimg">
					<video width="320" height="240" controls="controls">
						<source
							src="<%=ctxPath%>/htgl/attachmentController/downloadVideo?id=${vo.id}"
							type="${vo.fileType}">
						Your browser does not support the video tag.
					</video>
				</div>
			</c:forEach>
		</form>
	</div>
	<div class="page">
		共：${ page.totalCount } &nbsp; 条 &nbsp;&nbsp;
		<jsp:include page="/page/pageNavigator.jsp" />
	</div>
</div>

