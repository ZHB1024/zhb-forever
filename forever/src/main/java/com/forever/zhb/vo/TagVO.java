package com.forever.zhb.vo;

import java.io.Serializable;

public class TagVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5276161764253845738L;
	
	private String id;
    private String name;
    private String description;
    private String parentTagId;
    private int seqNum;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentTagId() {
        return this.parentTagId;
    }

    public void setParentTagId(String parentTagId) {
        this.parentTagId = parentTagId;
    }

    public int getSeqNum() {
        return this.seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

}
