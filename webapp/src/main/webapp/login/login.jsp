<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%>
<head>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/login/login.css" />
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/login/my.js"></script>
<title>登录管理</title>
</head>

<body>
<div id="loginDiv">
<h1>登录管理</h1>
    <form id="theform" action="" method="post">
        <c:if test="${errorMsg != null}">
          <p style="color:red;"><c:out value="${errorMsg}"/></p>
        </c:if>  
        <p><input type="text" name="name" id="name" value="${name}" placeholder="用户名" maxlength="30"/></p>
        <p><input type="password" name="password" id="password" value="${password}" placeholder="密码" maxlength="30"/></p>
        <input type="hidden" name="redirectUrl" value="${redirectUrl}"/>
        <p><input type="button" name="login" id="login" value="登录"></p>
    </form>
</div>
</body>
<script type="text/javascript">
$('#password').on('keypress',function(event){ 
    var theEvent = window.event||arguments.callee.caller.arguments[0];
    var code = theEvent.which;
    if(code == 13){
        loginSys();
    }
});

$("input[name='login']").click(function(){
    loginSys();
});

function loginSys(){
    var name = $("input[name='name']").val().trim();
    var password = $("input[name='password']").val().trim();
    if(''==name || ''== password){
        alert("用户名 和 密码都不能为空！");
        return false;
    }
    var url ;
    var redirectUrl = $("input[name='redirectUrl']").val().trim();
    if(null == redirectUrl || "" == redirectUrl){
    	url ='<%=ctxPath%>/loginController/login';
    }else{
    	url ='<%=ctxPath%>/loginController/loginWithUrl';
    }
    $("#theform").attr("action",url);
    $("#theform").submit();
}

$("input[name='register']").click(function(){
    var url ='<%=ctxPath%>/loginController/toRegister';
    $("#theform").attr("action",url);
    $("#theform").submit();
});
</script>