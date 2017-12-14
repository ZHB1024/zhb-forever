<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.forever.zhb.utils.TimeUtil"%>
<%@ page import="com.forever.zhb.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String ctxPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td style="background-image:url(<%=ctxPath%>/error/error.jpg); background-position:center; background-repeat:no-repeat; color:#FF0000;font-weight:600;font-size:20px; letter-spacing:2px; font-family:'楷体_utf-8'" height="400" align="center" valign="middle">
	        <%
	        String message = (String)request.getAttribute(Constants.REQUEST_ERROR);
	        if(null != message){
	            out.print(message);
	        } else {
	        %>
	                         非正常访问！
			<%
			}
			%>
        	<br /><br />
	        <span style="font-size:15;padding-right:14px;">
	        <%
	        String currentTime = TimeUtil.getTime("yyyy-MM-dd HH:mm:ss");
	        out.println(currentTime);
	        %>
	        </span>
        </td>
    </tr>
</table>
</body>
</html>