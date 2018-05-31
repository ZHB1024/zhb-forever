package com.forever.zhb.search.lucene;

import com.forever.zhb.search.ServicesConstants;
import com.forever.zhb.search.spring.bean.locator.SpringBeanLocator;

public class LuceneSearchClientFactory {
	
	public static LuceneSearchClient getLuceneSearchClientBean() {
        Object bean = SpringBeanLocator.getInstance(
                ServicesConstants.SEARCH_CLIENT_CONF).getBean(
                ServicesConstants.LUCENE_SEARCH_CLIENT);
        return (LuceneSearchClient) bean;
    }

}
