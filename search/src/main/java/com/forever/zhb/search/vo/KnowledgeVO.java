package com.forever.zhb.search.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class KnowledgeVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6582007783268278637L;
	private List<String> systemIds;
    private String systemNames;
    private String type;
    private String title;
    private String keywords;
    private String content;
    private int sort;
    private Date updateTime;
    private String creater;

    public List<String> getSystemIds() {
        return this.systemIds;
    }

    public void setSystemIds(List<String> systemIds) {
        this.systemIds = systemIds;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSystemNames() {
        return this.systemNames;
    }

    public void setSystemNames(String systemNames) {
        this.systemNames = systemNames;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

}
