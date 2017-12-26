package com.forever.zhb.search.client;

import com.forever.zhb.ServicesConstants;
import com.forever.zhb.spring.bean.locator.SpringBeanLocator;

public class SearchServiceClientFactory {
	
	public static SearchServiceClient getSearchServiceClientBean() {
        Object bean = SpringBeanLocator.getInstance(
                ServicesConstants.SEARCH_CLIENT_CONF).getBean(
                ServicesConstants.SEARCH_SERVICE_CLIENT);
        return (SearchServiceClient) bean;
    }
	

}
