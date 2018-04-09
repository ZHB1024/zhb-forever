<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String ctxPath = request.getContextPath();
%>

<div class="text-section">
	<h1>mail</h1>
</div>
<div class="states" id="states">
	<div class="succes">
	    <c:if test="${null != errorMsg}">
	      <span style="color: red;">
		   <c:out value="${errorMsg}" escapeXml="false" />
	      </span>
	    </c:if>
	    
		<form id="theform" action="<%=ctxPath %>/htgl/mailController/sendMail" method="post">
			<table>
				<tr>
					<td>收件人：</td>
					<td>
					<input type="email" name="toMail" required value="${mailVo.toMail}" />
					</td>
				</tr>
				<tr>
					<td>主题：</td>
					<td>
					<input type="text" name="title" required value="${mailVo.title}" />
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td><textarea name="content" required value="${mailVo.content}" ></textarea></td>
				</tr>

				<tr>
					<td><input type="submit" name="send" value="发 送" /></td>
				</tr>

			</table>
		</form>
	</div>
</div>


<script type="text/javascript">
 
 $("input[name='save']").click(function(){
	 var url ='<%=ctxPath %>/htgl/mailController/sendMail';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 });
</script>
