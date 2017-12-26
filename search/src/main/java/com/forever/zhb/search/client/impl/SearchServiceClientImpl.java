package com.forever.zhb.search.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.forever.zhb.common.indexdata.KnowIndexData;
import com.forever.zhb.page.Page;
import com.forever.zhb.page.PageUtil;
import com.forever.zhb.search.client.SearchServiceClient;
import com.forever.zhb.search.index.NewsIndexData;
import com.forever.zhb.vo.KnowledgeVO;

public class SearchServiceClientImpl extends BaseSolrService implements SearchServiceClient {
	
	private SolrServer solrAccount;
	
	@Override
	public void addNews(String id, String title, String content) {
		NewsIndexData news = new NewsIndexData();
		news.setId(id);
		news.setTitle(title);
		news.setContent(content);
		try {
			this.solrAccount.addBean(news);
			this.solrAccount.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NewsIndexData> getNews(String keyword, String orderField ,int start,int pageSize) {
		SolrQuery query = new SolrQuery();
        /*if (userTypes.size() > 1) {
            StringBuilder strBuild = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < userTypes.size(); ++i) {
                if (first) {
                    first = false;
                    strBuild.append("(");
                }
                String userType = (String) userTypes.get(i);
                strBuild.append(userType);
                if (i != userTypes.size() - 1) {
                    strBuild.append(" ");
                }
            }
            strBuild.append(")");
            query.addFilterQuery(
                    new String[] { new StringBuilder().append("user_type:").append(strBuild.toString()).toString() });
        } else if (1 == userTypes.size()) {
            query.addFilterQuery(new String[] {
                    new StringBuilder().append("user_type:").append((String) userTypes.get(0)).toString() });
        }*/
		
		//query.add("q", String.format("\"%s\"", keyword));
		query.setQuery(String.format("\"%s\"", new Object[] { keyword }));
		//query.set("text", "title:" + String.format("\"%s\"", new Object[] { keyword }));
		
        query.setStart(start);
        query.setRows(pageSize);

        if (StringUtils.isNotBlank(orderField)) {
            query.setSort(orderField, SolrQuery.ORDER.asc);
        }
        QueryResponse rsp = query(this.solrAccount, query);
		return rsp.getBeans(NewsIndexData.class);
	}
	
	@Override
	public void addKnowledge(List<KnowIndexData> knowIndexDatas) throws SolrServerException, IOException {
		if (null != knowIndexDatas && knowIndexDatas.size() > 0) {
			for (KnowIndexData knowIndexData : knowIndexDatas) {
				this.solrAccount.addBean(knowIndexData);
			}
			this.solrAccount.commit();
		}
	}
	
	@Override
	public Page<KnowledgeVO> searchKnow(String keywords, String systemId, Map<String, String> queryParams, int start,
            int pageSize) {
        SolrQuery query = new SolrQuery();
        query.setQuery(keywords);
        //query.addFilterQuery(new String[] { new StringBuilder().append("system_id:").append(systemId).toString() });
        query.setStart(Integer.valueOf(start));
        query.setRows(Integer.valueOf(pageSize));

        if (null != queryParams) {
        	for (String type : queryParams.keySet()) {
                query.setParam(type, new String[] { (String) queryParams.get(type) });
            }
			
		}
        QueryResponse rsp = query(this.solrAccount, query);
        List<KnowIndexData> knowIndexList = rsp.getBeans(KnowIndexData.class);
        List<KnowledgeVO> knowList = new ArrayList<KnowledgeVO>();
        for (KnowIndexData knowIndex : knowIndexList) {
            KnowledgeVO vo = new KnowledgeVO();
            vo.setTitle(knowIndex.getTitle());
            vo.setContent(knowIndex.getContent());
            vo.setKeywords(knowIndex.getKeywords());
            vo.setType(knowIndex.getType());
            vo.setUpdateTime(new Date(knowIndex.getUpdateTime()));
            knowList.add(vo);
        }

        long count = rsp.getResults().getNumFound();
        long startReturn = rsp.getResults().getStart();
        return PageUtil.getPage(knowList.iterator(), startReturn, pageSize, count);
    }
	
	
	
	
	public SolrServer getSolrAccount() {
		return solrAccount;
	}

	public void setSolrAccount(SolrServer solrAccount) {
		this.solrAccount = solrAccount;
	}

}
