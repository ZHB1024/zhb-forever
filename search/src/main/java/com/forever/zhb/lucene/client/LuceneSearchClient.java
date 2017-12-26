package com.forever.zhb.lucene.client;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

import com.forever.zhb.page.Page;
import com.forever.zhb.vo.DocumentVo;

public interface LuceneSearchClient {
	
	void initLuceneIndex(List<Document> documents) throws IOException;
	
	Page<DocumentVo> luceneSearch(String keyword, int start ,int pageSize) throws Exception;

}
