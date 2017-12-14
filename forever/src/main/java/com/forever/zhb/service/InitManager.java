package com.forever.zhb.service;

import java.util.List;

import com.forever.zhb.pojo.FunctionInfoData;

public interface InitManager {
    
    List<FunctionInfoData> getFunctionInfo();
    
    void save(FunctionInfoData functionInfoData);

}
