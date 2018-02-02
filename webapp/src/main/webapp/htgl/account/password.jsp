<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>修改密码</h1>
   <!--  <p>This is a quick overview of some features</p> -->
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form id="theform" action="" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
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
        <td><input type="text" name="name" value="${user.name}" readOnly maxlength="30"/></td>
      </tr>
      <tr>
        <td>密码:</td>
        <td><input type="password" name="password" maxlength="30"/></td>
      </tr>
      <tr>
        <td>新密码:</td>
        <td><input type="password" name="newPassword" maxlength="30"/></td>
      </tr>
      <tr>
        <td>确认密码:</td>
        <td><input type="password" name="confirmPassword"  maxlength="30"/></td>
      </tr>
      
      <tr>
        <td colspan="2"><input type="button" name="save" value="保 存"/></td>
      </tr>
      
    </table>
    </form> 
   </div>
  </div>
<script type="text/javascript">
 $("input[name='save']").click(function(){
     var url ='<%=ctxPath %>/htgl/account/modifyPassword';
     $("#theform").attr("action",url);
     $("#theform").submit();
 });
</script>
 