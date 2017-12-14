package com.forever.zhb.service.impl;

import java.util.List;

import com.forever.zhb.dao.IForeverDAO;
import com.forever.zhb.pojo.FunctionInfoData;
import com.forever.zhb.service.InitManager;

public class InitManagerImpl implements InitManager {
    
    private IForeverDAO foreverDao;
    
    public void setForeverDao(IForeverDAO foreverDao) {
        this.foreverDao = foreverDao;
    }

    public void save(FunctionInfoData functionInfoData){
        foreverDao.addFunctionInfo(functionInfoData);
    }
    
    public List<FunctionInfoData> getFunctionInfo(){
        return foreverDao.getFunctionInfos();
    }

}
