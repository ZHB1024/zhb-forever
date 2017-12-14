package com.forever.zhb.dic;

public enum DeleteFlagEnum {
	
	UDEL("未删除",0),DEL("已删除",1);
	
	private String name;
	private int index;
	
	private DeleteFlagEnum(String name,int index){
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
