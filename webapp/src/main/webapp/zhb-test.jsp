<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
    String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/cropper/cropper.css" />
<script type="text/javascript" src="<%=ctxPath%>/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/cropper/cropper.js"></script>


<div class="box">
    <img id="image" src="<%=ctxPath%>/images/my/loading.gif">
</div>

<script type="text/javascript">
    $('#image').cropper({
        aspectRatio: 16 / 16,//  width/height
        viewMode:1,
        crop: function (e) {
            console.log(e);
        }
    });
</script>