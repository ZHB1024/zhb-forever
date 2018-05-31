package com.forever.zhb.search.solr.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.forever.zhb.search.page.Page;
import com.forever.zhb.search.solr.index.KnowIndexData;
import com.forever.zhb.search.solr.index.NewsIndexData;
import com.forever.zhb.search.vo.KnowledgeVO;

public interface SearchServiceClient {
	
	List<NewsIndexData> getNews(String keyword,String orderField, int start,
            int pageSize);
	
	void addNews(String id, String title,String content);
	
	void addKnowledge(List<KnowIndexData> knowIndexDatas) throws SolrServerException, IOException;
	
	Page<KnowledgeVO> searchKnow(String keywords, String systemId, Map<String, String> queryParams, int start,
            int pageSize);

}
