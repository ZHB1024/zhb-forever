package com.forever.zhb.dic;

public enum FileTypeEnum {
    
    IMAGE("image",0),EXCEL("excel",1),WORD("word",2),PDF("pdf",3),TXT("txt",4),VIDEO("video",5),HEAD("head",6);
    
    private String name;
    private int index;
    
    private FileTypeEnum(String name,int index){
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
