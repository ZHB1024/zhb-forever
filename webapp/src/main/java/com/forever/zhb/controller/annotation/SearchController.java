package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.Constants;
import com.forever.zhb.search.elasticsearch.ElasticSearch;
import com.forever.zhb.search.elasticsearch.ElasticSearchClientFactory;
import com.forever.zhb.search.lucene.LuceneSearchClient;
import com.forever.zhb.search.lucene.LuceneSearchClientFactory;
import com.forever.zhb.search.page.Page;
import com.forever.zhb.pojo.NewsInfoData;
import com.forever.zhb.search.solr.client.SearchServiceClient;
import com.forever.zhb.search.solr.client.SearchServiceClientFactory;
import com.forever.zhb.search.solr.index.KnowIndexData;
import com.forever.zhb.search.solr.index.NewsIndexData;
import com.forever.zhb.search.util.LuceneUtil;
import com.forever.zhb.search.vo.DocumentVo;
import com.forever.zhb.search.vo.KnowledgeVO;
import com.forever.zhb.service.IForeverManager;

import org.apache.lucene.document.Field;
import org.apache.solr.client.solrj.SolrServerException;

@Controller
@RequestMapping("/htgl/searchController")
public class SearchController {
	
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
    
    private SearchServiceClient solrClient = SearchServiceClientFactory.getSearchServiceClientBean();
    private LuceneSearchClient luceneSearchClient = LuceneSearchClientFactory.getLuceneSearchClientBean();
    private ElasticSearch elasticSearchClient = ElasticSearchClientFactory.getElasticSearchClientBean();
    
    
    /*to solr*/
    @RequestMapping("/toSolr")
    public String toSolr(HttpServletRequest request,HttpServletResponse response){
        return "htgl.search.index";
    }
    
    /*add solr*/
    @RequestMapping("/addSolr")
    public String addSolr(HttpServletRequest request,HttpServletResponse response){
    	SearchServiceClient client = SearchServiceClientFactory.getSearchServiceClientBean();
    	List<NewsInfoData> news = foreverManager.getNewsInfos();
    	if (null != news && news.size()>0) {
    		for (NewsInfoData newsInfo : news) {
    			client.addNews(newsInfo.getId(), newsInfo.getTitle(), newsInfo.getContent());
    		}
    	}
    	return "htgl.search.index";
    }
    
    /*solr*/
    @RequestMapping("/solr")
    public String solr(HttpServletRequest request,HttpServletResponse response){
    	String keyword = request.getParameter("keyword");
		if (StringUtils.isBlank(keyword)) {
			request.setAttribute("errorMsg", "请输入查询关键字！");
			return "htgl.index.search";
		}
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		int pageSize = Constants.PAGE_SIZE;
		
    	List<NewsIndexData> news = solrClient.getNews(keyword,"id",0,10);
    	if (null != news) {
			System.out.println(news.size());
		}
    	request.setAttribute("keyword", keyword);
    	request.setAttribute("news", news);
    	return "htgl.search.index";
    }
    
    /*add knowSolr*/
    @RequestMapping("/addKnowSolr")
    public String addKnowSolr(HttpServletRequest request,HttpServletResponse response) throws SolrServerException, IOException{
    	SearchServiceClient client = SearchServiceClientFactory.getSearchServiceClientBean();
    	List<KnowIndexData> knows = new ArrayList<KnowIndexData>();
    	
    	KnowIndexData know1 = new KnowIndexData();
    	know1.setId("10");
    	know1.setTitle("大数据");
    	know1.setContent("大数据很大，真的特别大");
    	know1.setType("konw");
    	know1.setKeywords("大数据");
    	know1.setSort(1);
    	know1.setCreater("zhb");
    	List<String> systemIds = new ArrayList<String>();
    	systemIds.add("msg");
    	systemIds.add("gxjx");
    	know1.setSystemIds(systemIds);
    	know1.setUpdateTime(Calendar.getInstance().getTimeInMillis());
    	
    	knows.add(know1);
    	
    	KnowIndexData know2 = new KnowIndexData();
    	know2.setId("20");
    	know2.setTitle("云计算");
    	know2.setContent("你知道么，不知道，数据计算");
    	know2.setType("konw");
    	know2.setKeywords("云计算");
    	know2.setSort(2);
    	know2.setCreater("zhb");
    	List<String> systemIds2 = new ArrayList<String>();
    	systemIds2.add("dsk");
    	systemIds2.add("gk");
    	know2.setSystemIds(systemIds2);
    	know2.setUpdateTime(Calendar.getInstance().getTimeInMillis());
    	
    	knows.add(know2);
    	
    	
    	client.addKnowledge(knows);
    	
    	return "htgl.search.index";
    }
    
    /*searchKonwSolr*/
    @RequestMapping("/searchKonwSolr")
    public String searchKonwSolr(HttpServletRequest request,HttpServletResponse response){
    	String keyword = request.getParameter("keyword");
		if (StringUtils.isBlank(keyword)) {
			request.setAttribute("errorMsg", "请输入查询关键字！");
			return "htgl.index.search";
		}
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		int pageSize = Constants.PAGE_SIZE;
		
		Page<KnowledgeVO> konws = solrClient.searchKnow(keyword, null, null, Integer.parseInt(start), pageSize);
    	if (null != konws) {
			System.out.println(konws.getSize());
		}
    	request.setAttribute("keyword", keyword);
    	request.setAttribute("konws", konws);
    	return "htgl.search.index";
    }
    
 /*-----------------------------------Lucene--------------------------------------------------------------------*/   
    
    @RequestMapping("/initLuceneIndex")
	public String initLuceneIndex(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Document> documents = new ArrayList<Document>();
		List<NewsInfoData> newInfos = new ArrayList<NewsInfoData>();
		NewsInfoData data = new NewsInfoData();
		data.setId("8");
		data.setTitle("测试一下");
		data.setContent("北京天津深圳重庆上海广州");
		NewsInfoData data1 = new NewsInfoData();
		data1.setId("2");
		data1.setTitle("数据56");
		data1.setContent("大数据56，中央工45作组大数据56");
		newInfos.add(data);
		if (null != newInfos && newInfos.size() > 0) {
			for (NewsInfoData newInfo : newInfos) {
				Document document = LuceneUtil.createDocument();
				LuceneUtil.addStringFieldToDocument(document, "id", newInfo.getId(), Field.Store.YES);
				LuceneUtil.addStringFieldToDocument(document, "title", newInfo.getTitle(), Field.Store.YES);
				LuceneUtil.addStringFieldToDocument(document, "content", newInfo.getContent(), Field.Store.YES);
				documents.add(document);
			}
			luceneSearchClient.initLuceneIndex(documents);
		}
		return "htgl.lucene.index";
	}
    
    @RequestMapping("/luceneSearch")
	public String luceneSearch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String keyword = request.getParameter("keyword");
		if (StringUtils.isBlank(keyword)) {
			request.setAttribute("errorMsg", "请输入查询关键字！");
			return "htgl.lucene.index";
		}
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		int pageSize = Constants.PAGE_SIZE;
		Page<DocumentVo> page = luceneSearchClient.luceneSearch(keyword, Integer.parseInt(start), pageSize);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		request.setAttribute("count", page.getSize());
        return "htgl.lucene.index";
    }
	
//-----------------------------------------elasticsearch-------------------------------------------------------
    
    @RequestMapping("/elasticSearch")
	public String elasticSearch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		/*String keyword = request.getParameter("keyword");
		if (StringUtils.isBlank(keyword)) {
			request.setAttribute("errorMsg", "请输入查询关键字！");
			return "htgl.lucene.index";
		}
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
			start = "0";
		}
		int pageSize = Constants.PAGE_SIZE;
		Page<DocumentVo> page = luceneSearchClient.luceneSearch(keyword, Integer.parseInt(start), pageSize);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		request.setAttribute("count", page.getSize());*/
    	elasticSearchClient.getConnect();
    	//elasticSearchClient.createIndex();
    	elasticSearchClient.query();
    	elasticSearchClient.closeConnect();
        return "htgl.elasticSearch.index";
    }

}
