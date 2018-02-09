package com.forever.zhb.design.pattern.proxy;

public class Master implements Person {
	
	private String name;
	
	public Master(String name){
		this.name = name;
	}

	@Override
	public String searchHouse() {
		return name + "找房子";
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
