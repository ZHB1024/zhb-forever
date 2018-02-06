package com.forever.zhb.service;

import com.forever.zhb.ServiceConstants;
import com.forever.zhb.service.spring.SpringBeanLocator;

public class LoginLogManagerFactory {
	
	public static LoginLogManager getLoginLogManagerBean(){
		Object bean = SpringBeanLocator.getInstance(
                ServiceConstants.SERVICE_CLIENT_CONF).getBean(
                		ServiceConstants.LOGIN_LOG_MANAGER);
        return (LoginLogManager) bean;
	}

}
