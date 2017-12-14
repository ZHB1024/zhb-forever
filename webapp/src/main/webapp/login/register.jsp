<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%@ taglib uri="zhb-forever"  prefix="zhb-forever" %>
<%
  String ctxPath = request.getContextPath();
%> 

<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.8.0.js"></script>

  <div align="center">
    <h1>register</h1>
  </div>
  
  <div align="center">
    <form id="theform" action="<%=ctxPath %>/loginController/register" method="post">
    <table >
      <c:if test="${errorMsg != null}">
        <tr>
         <td colspan="2">
          <span style="color:red;"><c:out value="${errorMsg}"/></span>
         </td>
        </tr>
      </c:if>
      <tr>
        <td>用户名：</td>
        <td><input type="text" name="name" id="name" maxlength="30"/></td>
      </tr>
      <tr>
        <td>真实姓名：</td>
        <td><input type="text" name="realName" id="realName" maxlength="30"/></td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>
          <input type="radio" name="sex" id="sex" value="男"/>男
          <input type="radio" name="sex" id="sex" value="女"/>女
        </td>
      </tr>
      <tr>
        <td>手机号：</td>
        <td>
          <input type="text" name="phone" id="phone"/>
        </td>
      </tr>
      <tr>
        <td>电子邮箱：</td>
        <td>
          <input type="text" name="email" id="email"/>
        </td>
      </tr>
      <tr>
        <td>密码</td>
        <td><input type="text" name="password" id="password" maxlength="30"/></td>
      </tr>
      <tr>
        <td>确认密码</td>
        <td><input type="text" name="confirmPass" id="confirmPass" maxlength="30"/></td>
      </tr>
      
      <tr>
        <td></td>
        <td><input type="button" name="register" id="register" value="确 定"/></td>
      </tr>
      
    </table>
   </form> 
  </div>
 
 <script type="text/javascript">
 
 $("input[name='register']").click(function(){
	 var url ='<%=ctxPath%>/loginController/register';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 });
</script>
 