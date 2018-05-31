package com.forever.zhb.search.solr.client;

import com.forever.zhb.search.ServicesConstants;
import com.forever.zhb.search.spring.bean.locator.SpringBeanLocator;

public class SearchServiceClientFactory {
	
	public static SearchServiceClient getSearchServiceClientBean() {
        Object bean = SpringBeanLocator.getInstance(
                ServicesConstants.SEARCH_CLIENT_CONF).getBean(
                ServicesConstants.SEARCH_SERVICE_CLIENT);
        return (SearchServiceClient) bean;
    }
	

}
