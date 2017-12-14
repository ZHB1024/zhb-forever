package com.forever.zhb.lucene.client;

import com.forever.zhb.ServicesConstants;
import com.forever.zhb.spring.bean.locator.SpringBeanLocator;

public class LuceneSearchClientFactory {
	
	public static LuceneSearchClient getLuceneSearchClientBean() {
        Object bean = SpringBeanLocator.getInstance(
                ServicesConstants.SEARCH_CLIENT_CONF).getBean(
                ServicesConstants.LUCENE_SEARCH_CLIENT);
        return (LuceneSearchClient) bean;
    }

}
