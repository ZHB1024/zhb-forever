<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
  String ctxPath = request.getContextPath();
%> 

  <div class="text-section">
    <h1>JFree-draw</h1>
  </div>
  <div class="states" id="states">
     <div class="succes">
       <form name="theform" id="theform"  method="post" action="<%=ctxPath%>/htgl/jfreeController/jfreeDraw">
        <div>
        
         <dl>
           <dd>
             	生成图的类型:
            <select id="drawType" name="drawType">
              <c:forEach var="type" items="${jfreeType}">
                <option value="${type.value}" <c:if test="${ drawType == type.value}">selected</c:if> >${type.name}</option>
              </c:forEach>
            </select>
            
            <input type="submit" value="生 成" />
           </dd>
        </dl>
        
        <dl>
           <dd>
             <c:if test="${null != draw0 }">
               <img alt="${draw0}" src="${draw0}">
             </c:if>
             <c:if test="${null != draw1 }">
               <img alt="${draw1}" src="${draw1}">
             </c:if>
             <c:if test="${null != draw2 }">
               <img alt="${draw2}" src="${draw2}">
             </c:if>
           </dd>
       </dl>
        
      </div>
    </form>       
   </div>
  </div>
