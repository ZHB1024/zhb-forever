package com.forever.zhb.search.elasticsearch;

import java.net.UnknownHostException;
import java.util.List;

import com.forever.zhb.search.elasticsearch.indexdata.ElasticSearchIndexData;
import com.forever.zhb.search.page.Page;

public interface ElasticSearch {

	void getConnect() throws UnknownHostException;
	
	void initIndex(String index,String type ,ElasticSearchIndexData data) ;
	
	void initIndex(String index,String type ,List<ElasticSearchIndexData> datas);
	
	ElasticSearchIndexData getIndexById(String id,String index,String type);
	
	boolean updateIndexById(ElasticSearchIndexData data,String index,String type);
	
	boolean delIndexByIndex(String index);
	
	boolean delIndexById(String id,String index,String type);
	
	Page<ElasticSearchIndexData> query(String index,String type ,String keyWord , int start,int pageSize) throws Exception;
	
	void closeConnect();
}
