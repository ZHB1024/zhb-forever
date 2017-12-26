package com.forever.zhb.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forever.zhb.Constants;
import com.forever.zhb.utils.environments.Environment;

public class EnvironmentsFilter implements Filter {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public void init(FilterConfig paramFilterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse,
			FilterChain paramFilterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)paramServletRequest;
		//Environment.logEnv("Client environment:", log);
		
		/*
		request.getSession().setAttribute(Constants.HOST_NAME, this.hostName);*/
		paramFilterChain.doFilter(request, paramServletResponse);
	}

	public void destroy() {
	}
}
