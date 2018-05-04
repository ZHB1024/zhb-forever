package com.forever.zhb.dic;

public enum JFreeDrawTypeEnum {
	
	ALL("所有",0),LINE("折线图",1),PIE("饼状图",2),BAR("柱状图",3);
	
	private String name;
	private int index;
	
	private JFreeDrawTypeEnum(String name,int index){
		this.name = name;
		this.index = index;
	}
	
	public static String getName(int index){
		for (DeleteFlagEnum deleteFlagEnum : DeleteFlagEnum.values()) {
			if (deleteFlagEnum.getIndex() == index) {
				return deleteFlagEnum.getName();
			}
		}
		return ALL.getName();
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
