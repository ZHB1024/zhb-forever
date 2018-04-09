<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>upload--excel</h1>
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form name="theform" id="theform"  method="post" action="<%=ctxPath%>/htgl/attachmentController/uploadExcel" enctype="multipart/form-data">
        <div>
         <dl >
           <dd>
            <input type ="file" name="file" id="file" required/>
           </dd>
        </dl>
        <dl >
          <dd>
            <input type="submit" value="上传" />
          </dd>
        </dl>
        <c:if test="${null != content }">
          <dl >
           <dd>
            <textarea>${content}</textarea>
           </dd>
          </dl>
        </c:if>
        <dl >
          <dd>
            <input type="button" id="export" name ="export" value="导 出" />
          </dd>
        </dl>
      </div>
    </form>       
   </div>
  </div>
  
<script type="text/javascript">
 $("input[name='export']").click(function(){
	 var url ='<%=ctxPath %>/htgl/attachmentController/exportExcel';
	 $("#theform").attr("action",url);
     $("#theform").submit();
 });
</script>
