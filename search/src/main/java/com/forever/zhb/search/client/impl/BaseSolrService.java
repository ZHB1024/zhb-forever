package com.forever.zhb.search.client.impl;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;

import com.forever.zhb.service.base.BaseService;

public class BaseSolrService extends BaseService {
	
	protected SolrServer solr;

    public SolrServer getSolr() {
        return this.solr;
    }

    public void setSolr(SolrServer solr) {
        this.solr = solr;
    }

    protected QueryResponse query(SolrParams params) {
        try {
            return this.solr.query(params);
        } catch (SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    protected QueryResponse query(SolrServer solr, SolrParams params) {
        int i = 0;
        while (i++ < 5) {
            try {
                return solr.query(params);
            } catch (SolrServerException e) {
                this.log.info("solr query exception,time:{},msg:{}", Integer.valueOf(i), e.getMessage());
            }
        }
        throw new RuntimeException("query exception");
    }

}
