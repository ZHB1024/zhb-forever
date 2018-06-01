package com.forever.zhb.search.elasticsearch.init;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.search.Constants;
import com.forever.zhb.search.elasticsearch.ElasticSearch;
import com.forever.zhb.search.elasticsearch.ElasticSearchClientFactory;
import com.forever.zhb.search.elasticsearch.indexdata.ElasticSearchIndexData;
import com.forever.zhb.search.util.RandomValueUtil;

public class InitDataElasticSearch {
	
	private static ElasticSearch elasticSearchClient = ElasticSearchClientFactory.getElasticSearchClientBean();
	
	public static void initData(){
		List<ElasticSearchIndexData> datas = new ArrayList<ElasticSearchIndexData>();
    	for(int i=0;i<100;i++){
    		ElasticSearchIndexData data = new ElasticSearchIndexData();
    		data.setId(RandomValueUtil.getRandomUUID());
    		data.setName(RandomValueUtil.randomName(i));
    		data.setAge(RandomValueUtil.randomAge());
    		data.setSex(RandomValueUtil.randomSex());
    		datas.add(data);
    	}
    	elasticSearchClient.initIndex(Constants.DEFAULT_ELASTIC_SEARCH_INDEX,Constants.DEFAULT_ELASTIC_SEARCH_TYPE,datas);
	}

}
