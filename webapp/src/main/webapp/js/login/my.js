$("input[name='login']").click(function(){
     var name = $("input[name='name']").val().trim();
     var password = $("input[name='password']").val().trim();
     if(''==name || ''== password){
    	 alert("用户名 和 密码都不能为空！");
    	 return false;
     }
     var url ='<%=ctxPath%>/loginController/login';
     $("#theform").attr("action",url);
     $("#theform").submit();
 });
 
 $("input[name='register']").click(function(){
     var url ='<%=ctxPath%>/loginController/toRegister';
     $("#theform").attr("action",url);
     $("#theform").submit();
 });