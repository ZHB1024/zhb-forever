<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>upload</h1>
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form name="upform" id="theform"  method="post" action="<%=ctxPath%>/htgl/aiController/detectFace" enctype="multipart/form-data">
        <div>
         <dl >
           <dd>
            <input type ="file" name="faceFile" id="faceFile"/>
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

