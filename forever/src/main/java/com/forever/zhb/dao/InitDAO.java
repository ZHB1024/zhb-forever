package com.forever.zhb.dao;

import java.util.List;

import com.forever.zhb.pojo.FunctionInfoData;

public interface InitDAO {
    
    void save(Object object);
    
    void save(FunctionInfoData functionInfoData);
    
    List<FunctionInfoData> getFunctionInfo();

}
