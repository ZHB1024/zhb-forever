package com.forever.zhb.search.elasticsearch;

import java.net.UnknownHostException;
import java.util.List;

import com.forever.zhb.search.elasticsearch.indexdata.ElasticSearchIndexData;

public interface ElasticSearch {

	void getConnect() throws UnknownHostException;
	
	void createIndex();
	
	void initIndex(ElasticSearchIndexData data) ;
	
	void initIndex(List<ElasticSearchIndexData> datas);
	
	void query() throws Exception;
	
	void closeConnect();
}
