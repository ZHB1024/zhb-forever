<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String ctxPath = request.getContextPath();
%>    
<div align="center">
  <form name="upform" id="upform"  method="post" action="<%=ctxPath%>/htgl/user?action=upload" enctype="multipart/form-data">
      <div>
        <input type = "hidden" name = "id" value = "123"/>
        <input type = "hidden" name = "userName" value = "huibin"/> 
      </div>
      
      <div>
        <dl >
         <dt><label>★</label>申请表：</dt>
         <dd>
         <input type ="file" name="firstFile" id="firstFile"/>
         </dd>
        </dl>
        <dl >
          <dt></dt>
          <dd>
            <input type="submit" value="上传" />
          </dd>
        </dl>
      </div>
       
    </form>           
</div>