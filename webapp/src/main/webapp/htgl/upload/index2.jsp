<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>upload</h1>
  </div>
  <div class="states">
     <div class="succes">
       <form name="upform" id="theform"  method="post" action="<%=ctxPath%>/htgl/uploadController/upload" enctype="multipart/form-data">
        <div>
         <dl >
           <dd>
            <input type ="file" name="firstFile" id="firstFile"/>
           </dd>
        </dl>
        <dl >
          <dd>
            <input type="submit" value="上传" />
          </dd>
        </dl>
      </div>
    </form>       
   </div>
  </div>

<script src="<%=ctxPath%>/js/jquery.form.js"></script>

<script type="text/javascript">
function batchUploadNoPage(){
    var _orgTypesArr = [];
    $("input[name='comOrgTypes']:checked").each(
        function (i) {
            _orgTypesArr.push($(this).val());
        });
    
    var uploadFile = $("#uploadExcel").val();
    if(uploadFile == ""){
        alert("请上传格式为 xls 或  xlsx 的excel文件");
        return false;
    }
    var files = new Array();
    files = uploadFile.split(".");
    var fileType = files[files.length - 1];
    if (fileType != 'xls' && fileType != 'xlsx') {
        alert(" 文件格式不正确，请上传格式为 xls 或  xlsx 的文件！");
        return false;
    }
    
    $("#tip_notice").hide();
    $("#search_result_box").hide();
    $("#loading_div").show();
    
    $("#fileName").val(uploadFile);
    
    var options1 = {
 		   target: '#output',          //把服务器返回的内容放入id为output的元素中    
            		   //beforeSubmit: showRequest,  //提交前的回调函数
            		   url: "<%=ctxPath%>/xgzp/modify/school/check.do",                 
            		   type: "post",               //默认是form的method（get or post），如果申明，则会覆盖
            		   dataType: 'json',           //html(默认), xml, script, json...接受服务端返回的类型
            		   contentType: "application/x-www-form-urlencoded; charset=utf-8",   //设置编码集
            		   //clearForm: true,          //成功提交后，清除所有表单元素的值
            		   //resetForm: true,          //成功提交后，重置所有表单元素的值
            		   timeout: 10000,               //限制请求的时间，当请求大于3秒后，跳出请求
            		   success: function(data1){          
//                            console.log("返回结果："+data1);
                           if(data1.flag==false){
                               if(data1.personNum==-99){
                            	   alert(data1.message);
                            	   return false;
                               }
                        	   if(!confirm(data1.message)){
                                   return false;
                               }
	                           $(".issue_"+count)[0].value="false";
                           }else{
	                           $(".issue_"+count)[0].value="true";
                           }
                           console.log($(".issue_"+count)[0]);
                           if(true){
                               // Dialog         
                                $('#dialog').dialog({
                                    autoOpen: true,
                                    width: 500,
                                    height:440,
                                    modal: true,
                                    buttons: {
                                        "取消": function() { 
                                            $(this).dialog("close"); 
                                        }, 
                                        "更换": function() { 
                                        }
                                    }
                                });
                            }
                       },
                       error: function(data1){    
                    	   console.log(data1);
                       }
   };
   $("#uploadform").ajaxSubmit(options1);        
 
   /* 传统ajax提交 */   
    <%-- var url="<%=ctxPath%>/htgl/group/batchUploadNoPage.action";
    var formData = new FormData();
    formData.append("file",$("#uploadExcel")[0].files[0]);
    formData.append("comOrgTypes",_orgTypesArr);
    formData.append("dataType",<%=dataType%>);
    formData.append("fileName",uploadFile);
    $.ajax({             
        url:url,              
        type:"POST",    
        traditional:true,
        data:formData,
        // 告诉jQuery不要去处理发送的数据
        processData : false, 
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        success:function(data){  
            $("#search_result_box").find("ul.address_ul").empty().append(data);
            $("#loading_div").hide();
             $("#select-all").removeAttr("checked");
            var _result_total = $("#search_result_box").find("ul.address_ul li label").length;
            $("#search_result_box").find("span.res-total-number").text(_result_total);
            $("#search_result_box").show();
        }
    }); --%>
    
    
 /* 模拟ajax提交 */   
    <%-- function batchUploadNoPage(){
        var _orgTypesArr = [];
        $("input[name='comOrgTypes']:checked").each(
            function (i) {
                _orgTypesArr.push($(this).val());
            });
        
        var uploadFile = $("#uploadExcel").val();
        if(uploadFile == ""){
            alert("请上传格式为 xls 或  xlsx 的excel文件");
            return false;
        }
        var files = new Array();
        files = uploadFile.split(".");
        var fileType = files[files.length - 1];
        if (fileType != 'xls' && fileType != 'xlsx') {
            alert(" 文件格式不正确，请上传格式为 xls 或  xlsx 的文件！");
            return false;
        }
        
        $("#tip_notice").hide();
        $("#search_result_box").hide();
        $("#loading_div").show();
        
        $("#fileName").val(uploadFile);
        
        var options1 = {
                url: "<%=ctxPath%>/htgl/group/batchUploadNoPage.action",                 
                type: "post",               //默认是form的method（get or post），如果申明，则会覆盖
                contentType: "application/x-www-form-urlencoded; charset=utf-8",   //设置编码集
                ///timeout: 10000,               //限制请求的时间，当请求大于3秒后，跳出请求
                success: function(data){   
                   $("#search_result_box").find("ul.address_ul").empty().append(data);
                     $("#loading_div").hide();
                      $("#select-all").removeAttr("checked");
                     var _result_total = $("#search_result_box").find("ul.address_ul li label").length;
                     $("#search_result_box").find("span.res-total-number").text(_result_total);
                     $("#search_result_box").show();
                },
                error: function(data1){    
                    console.log(data1);
                }
       };
       $("#uploadform").ajaxSubmit(options1);        
     } --%>
    
 }
 
</script>
