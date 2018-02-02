<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>account</h1>
    <!-- <p>个人信息</p> -->
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form id="theform" action="" method="post">
        <input type="hidden" name="id" value="${user.id}"/>
         <table >
           <c:if test="${errorMsg != null}">
       		 <tr>
       		  <td colspan="2">
         		 <span style="color:red;"><c:out value="${errorMsg}" escapeXml="false" /></span>
         	  </td>
             </tr>
           </c:if>
      <tr>
        <td>用户名：</td>
        <td><input type="text" name="name" value="${user.name}" readOnly maxlength="30"/></td>
      </tr>
      <tr>
        <td>真实姓名：</td>
        <td>
         <input type="text" name="realName" value="${user.realName}" <c:if test="${user.realName != null }">readOnly</c:if> maxlength="30"/>
        </td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>
         <c:if test="${user.sex == '男'}">
          <input type="radio" name="sex" value="男" checked />男
         </c:if>
         <c:if test="${user.sex == '女'}">
          <input type="radio" name="sex" value="女" checked />女
         </c:if>
         <c:if test="${user.sex == null}">
          <input type="radio" name="sex" value="男" />男
          <input type="radio" name="sex" value="女" />女
         </c:if>
        </td>
      </tr>
      <tr>
        <td>手机号：</td>
        <td>
          <input type="text" name="phone" value="${user.phone}"/>
        </td>
      </tr>
      <tr>
        <td>电子邮箱：</td>
        <td>
          <input type="text" name="email" value="${user.email}"/>
        </td>
      </tr>
      <tr>
        <td><input type="button" name="save" value="保 存"/></td>
        <td><a href="<%=ctxPath %>/htgl/account/toModifyPassword?id=${user.id}">修改密码</a></td>
      </tr>
      
       </table>
      </form> 
     </div>
  </div>

 
 <script type="text/javascript">
 
 $("input[name='save']").click(function(){
	 var url ='<%=ctxPath %>/htgl/account/upAccount';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 });
</script>
 