package com.forever.zhb.search.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

import com.forever.zhb.search.page.Page;
import com.forever.zhb.search.vo.DocumentVo;

public interface LuceneSearchClient {
	
	void initLuceneIndex(List<Document> documents) throws IOException;
	
	Page<DocumentVo> luceneSearch(String keyword, int start ,int pageSize) throws Exception;

}
