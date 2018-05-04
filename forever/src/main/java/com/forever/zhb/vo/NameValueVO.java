package com.forever.zhb.vo;

public class NameValueVO {
	
	public NameValueVO(){}
	
	public NameValueVO(String name,String value){
		this.name = name;
		this.value = value;
	}
	
	
	String name;
	String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
