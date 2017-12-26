package com.forever.zhb.search.index;

import java.util.Calendar;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class NewsIndexData {
	
	@Field
    private String id;

    @Field
    private String title;

    @Field("content")
    private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    /*@Field("create_time")
    private Calendar createTime;*/
    
}
