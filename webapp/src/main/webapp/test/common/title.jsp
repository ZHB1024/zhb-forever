<%@page import="com.forever.zhb.util.WebAppUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.forever.zhb.pojo.UserInfoData"%>
<%
  String ctxPath = request.getContextPath();
  UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
  request.setAttribute("user", userInfo);
%> 

<div>
    title
</div>