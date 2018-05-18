<%@ page contentType="text/html; charset=utf-8" language="java"  %>
<%@ page import="com.forever.zhb.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>zhb_forever</title>
<style type="text/css">
html, body, p, ul, li, form, img, h1, h2, h3, h4, h5, h6, input, select, fieldset {
    margin: 0;
    padding: 0;
}
body {
   /* background: url("http://t4.chsi.com.cn/images/index/bodyBG.jpg") repeat-x fixed center top #CEE3ED;*/
   background:#FFF;
    color: #333333;
    font: 12px/22px "宋体",Verdana,Arial,Helvetica,sans-serif;
}
input, select, option {
    font-size: 12px;
}
ul, li {
    list-style: none outside none;
}
img {
    border: 0 none;
    vertical-align: middle;
}
em {
    font-style: normal;
    font-weight: normal;
}
a:link, a:visited {
    color: #333333;
    text-decoration: none;
}
a:hover {
    color: #BA2636;
    text-decoration: underline;
}
.clearFloat {
    clear: both;
    font-size: 0;
    height: 0;
    line-height: 0;
    overflow: hidden;
}
body {
    margin:0 auto;
    padding:0px;
    text-align:center;
}
a {
    color:#333;
    text-decoration:none;
}
a:visited {
    color:#551a8b;
}
a:hover {
    color:#C00;
    text-decoration:underline;
}
form,ul {
    margin:0;
    padding:0;
}
ul li {
    list-style:none;
}

.vote_wrap {
    background: none repeat scroll 0 0 #FFFFFF;
    clear: both;
    margin: 0 auto;
    padding: 0 20px;
    width: 750px;
}
.header_logo {
    clear: both;
    color: #333333;
    height: 36px;
    margin: 0 auto;
    overflow: hidden;
    text-align: left;
    padding-top:10px;
    width: 750px;
}
/**/

.t1 {
    background: url("/images/info_cl_001.png") no-repeat scroll 0 0 transparent;
    height: 126px;
}
.t2 {
    background: url("/images/info_cl_002.png") repeat-y scroll 0 0 transparent;
    overflow: hidden;
    padding: 0 110px 100px 115px;
    text-align:center;
}
.t3 {
    background: url("/images/info_cl_003.png") no-repeat scroll 0 0 transparent;
    height: 4px;
    overflow: hidden;
}
</style>
</head>

<body>
<div class="vote_wrap">
<div class="header_logo" title="zhb_forever">
<a title="zhb_forever" target="_blank" href="http://www.chsi.com.cn/"><img alt="zhb_forever" src="http://t1.chsi.com.cn/chsi/images/index/logo.png"></a>
</div>
<div class="cb" style=" width:608px; padding:10px 0px 20px 0px;">
<%--<table width=100% border=0 cellpadding=0 cellspacing=0 align="center"  >
  <tr>
    <td width="600" height="300" align="center" style="color:#333333"><strong><%=request.getAttribute(Constants.REQUEST_ERROR)%></strong></td>
  </tr>
</table>--%>

<div class="t1"></div>
<div class="t2">
 <strong>
   <h2><span style="color:#FF0000"><%=request.getAttribute(Constants.REQUEST_ERROR)%></span></h2><br>
 </strong>
</div>
<div class="t3"></div>
</div>

</div>
</body>
</html>
