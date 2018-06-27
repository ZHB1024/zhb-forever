package com.forever.zhb.dic;

import com.forever.zhb.vo.NameValueVO;
import java.util.ArrayList;
import java.util.List;

public enum DeleteFlagEnum {
	
	UDEL("正常",0),DEL("已删除",1);
	
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

	public static List<NameValueVO> getAll(){
	    List<NameValueVO> vos = new ArrayList<NameValueVO>();
        for (DeleteFlagEnum deleteFlagEnum: DeleteFlagEnum.values()) {
            NameValueVO vo = new NameValueVO();
            vo.setName(deleteFlagEnum.getName());
            vo.setValue(deleteFlagEnum.getIndex()+"");
            vos.add(vo);
        }
	    return vos;
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
