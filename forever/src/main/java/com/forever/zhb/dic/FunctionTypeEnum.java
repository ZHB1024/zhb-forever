package com.forever.zhb.dic;

public enum FunctionTypeEnum {
    PHOTO("PHOTO","照片"),BILL("BILL","账单");
    
    private String name;
    private String description;
    
    private FunctionTypeEnum(String name,String description) {
        this.name = name;
        this.description = description;
    }
    
    public static String getName(String description){
        for (FunctionTypeEnum functionTypeEnum : FunctionTypeEnum.values()) {
            if (functionTypeEnum.getDescription().equals(description)) {
                return functionTypeEnum.getName();
            }
        }
        return "未定义";
    }
    
    public static String getDescription(String name){
        for (FunctionTypeEnum functionTypeEnum : FunctionTypeEnum.values()) {
            if (functionTypeEnum.getName().equals(name)) {
                return functionTypeEnum.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
