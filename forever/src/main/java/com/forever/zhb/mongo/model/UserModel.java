package com.forever.zhb.mongo.model;

import java.io.Serializable;

public class UserModel implements Serializable {
	
	private static final long serialVersionUID = 1L;  
    private String name;  
    private String sex;  
  
    public UserModel(String name, String sex) {  
        super();  
        this.name = name;  
        this.sex = sex;  
    }  
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}  
}
