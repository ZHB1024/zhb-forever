package com.forever.zhb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.forever.zhb.Constants;


public class SquidIpFilter implements Filter {
    protected final Log log = LogFactory.getLog(getClass());

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request2 = (HttpServletRequest)request;
        SquidEnabledRequest squidRequest = new SquidEnabledRequest(request2);
        //log.info("ip：" + squidRequest.getRemoteAddr());
        request2.getSession().setAttribute(Constants.IP, squidRequest.getRemoteAddr());
        chain.doFilter(squidRequest, response);
    }

    public void destroy() {
    }

}
