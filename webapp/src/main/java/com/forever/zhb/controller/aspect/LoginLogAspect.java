package com.forever.zhb.controller.aspect;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forever.zhb.dic.LoginEnum;
import com.forever.zhb.pojo.LoginLogInfoData;
import com.forever.zhb.service.LoginLogManager;
import com.forever.zhb.service.LoginLogManagerFactory;
import com.forever.zhb.utils.BrowserUtils;

@Aspect
@Component
public class LoginLogAspect {
	
	private Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);
	
	@Pointcut("execution(public * com.forever.zhb.controller.annotation.LoginController.login(..))")
	public void log(){
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint){
	}
	
	@After("log()")
	public void doAfter(){
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void doAfterReturning(Object object){
		LoginLogManager loginLogManager = LoginLogManagerFactory.getLoginLogManagerBean();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		LoginLogInfoData loginLogInfoData = new LoginLogInfoData();
		if (StringUtils.isNotBlank(request.getParameter("name"))) {
			loginLogInfoData.setUserName(request.getParameter("name"));
		}else{
			loginLogInfoData.setUserName("unknown");
		}
		if ("login.home".equals(object.toString())) {
			loginLogInfoData.setLoginIn(LoginEnum.UN_LOGIN.getIndex());
		}else{
			loginLogInfoData.setLoginIn(LoginEnum.LOGIN_IN.getIndex());
		}
		loginLogInfoData.setBrowserName(BrowserUtils.checkBrowse(request));
		loginLogInfoData.setClientIp(request.getRemoteAddr());
		loginLogInfoData.setCreateTime(Calendar.getInstance());
		loginLogManager.save(loginLogInfoData);
	}

}
