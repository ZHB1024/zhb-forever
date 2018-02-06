package com.forever.zhb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.forever.zhb.Constants;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.util.WebAppUtil;

public class LoginFilter implements Filter {
    protected final Log log = LogFactory.getLog(getClass());

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response =(HttpServletResponse)arg1;
        UserInfoData userInfo = WebAppUtil.getUserInfoData(request);
        if (null == userInfo || StringUtils.isBlank(userInfo.getName())) {
            String ctxPath = request.getContextPath();
            try {
            	request.setAttribute(Constants.REQUEST_ERROR, "登陆后才能访问系统");
				request.getRequestDispatcher(ctxPath + "/login/login.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
            return;
        }
        chain.doFilter(request, arg1);
    }

    public void destroy() {
    }

}
