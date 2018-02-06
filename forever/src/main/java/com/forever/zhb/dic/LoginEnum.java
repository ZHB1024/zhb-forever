package com.forever.zhb.dic;

public enum LoginEnum {
	
	UN_LOGIN("登录失败",0),LOGIN_IN("登录成功",1),LOGIN_OUT("退出",2);
	
	private String name;
	private int index;
	
	private LoginEnum(String name,int index){
		this.name = name;
		this.index = index;
	}
	
	public static String getName(int index){
		for (DeleteFlagEnum deleteFlagEnum : DeleteFlagEnum.values()) {
			if (deleteFlagEnum.getIndex() == index) {
				return deleteFlagEnum.getName();
			}
		}
		return "未定义";
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
