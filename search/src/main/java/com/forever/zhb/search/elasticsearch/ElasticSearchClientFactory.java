package com.forever.zhb.search.elasticsearch;

import com.forever.zhb.search.ServicesConstants;
import com.forever.zhb.search.spring.bean.locator.SpringBeanLocator;

public class ElasticSearchClientFactory {
	
	public static ElasticSearch getElasticSearchClientBean() {
        Object bean = SpringBeanLocator.getInstance(
        		ServicesConstants.SEARCH_CLIENT_CONF).getBean(
        				ServicesConstants.ELASTIC_SEARCH_CLIENT);
        return (ElasticSearch) bean;
    }

}
