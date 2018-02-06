package com.forever.zhb.controller.aspect;

import javax.servlet.http.HttpServletRequest;

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

@Aspect
@Component
public class HttpAspect {
	
	private Logger loger = LoggerFactory.getLogger(HttpAspect.class);
	
	@Pointcut("execution(public * com.forever.zhb.controller.annotation.LoginController.*(..))")
	public void log(){
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint){
		loger.info("before------------");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		loger.info("url={}",request.getRequestURI());
		loger.info("method={}",request.getMethod());
		loger.info("ip={}",request.getRemoteAddr());
		loger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName());
		/*Object[] objects = joinPoint.getArgs();
		if (null != objects) {
			HttpServletRequest request2 = (HttpServletRequest)objects[0];
			String userName1 = request.getParameter("name");
			String userName = request2.getParameter("name");
			loger.info(userName1);
			loger.info(userName);
		}
		for (Object object : objects) {
			loger.info(object.toString());
		}*/
		loger.info("args={}",joinPoint.getArgs());
		
	}
	
	@After("log()")
	public void doAfter(){
		loger.info("after------------");
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void doAfterReturning(Object object){
		loger.info("response={}",object.toString());
	}

}
