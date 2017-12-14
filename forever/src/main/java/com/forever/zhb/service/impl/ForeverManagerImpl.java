package com.forever.zhb.service.impl;

import java.util.List;

import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.pojo.FunctionInfoData;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.NewsInfoData;
import com.forever.zhb.pojo.RoleInfoData;
import com.forever.zhb.pojo.User;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.dao.IForeverDAO;

public class ForeverManagerImpl implements IForeverManager {

    private IForeverDAO foreverDao;

    public void setForeverDao(IForeverDAO foreverDao) {
        this.foreverDao = foreverDao;
    }
    
/* function_info*/    
    public void addFunctionInfo(FunctionInfoData functionInfoData){
        foreverDao.addFunctionInfo(functionInfoData);
    }
    
    public FunctionInfoData getFunctionInfoByName(String name){
        return foreverDao.getFunctionInfoByName(name);
    }
    
    public List<FunctionInfoData> getFunctionInfos(){
        return foreverDao.getFunctionInfos();
    }
    
/*role_info*/    
    public void addRole(RoleInfoData roleInfoData){
        foreverDao.addRole(roleInfoData);
    }
    public RoleInfoData getRoleInfoByName(String name){
        return foreverDao.getRoleInfoByName(name);
    }
    public List<RoleInfoData> getRoleInfos(){
        return foreverDao.getRoleInfos();
    }
/*user_info*/
    public void addUserInfo(UserInfoData userInfoData){
        foreverDao.addUserInfo(userInfoData);
    }
    public UserInfoData getUserInfoByName(String name){
        return foreverDao.getUserInfoByName(name);
    }
    public UserInfoData getUserInfoById(String id){
        return foreverDao.getUserInfoById(id);
    }
    
/*login_info*/    
    public void addLoginInfo(LoginInfoData loginInfoData){
        foreverDao.addLoginInfo(loginInfoData);
    }
    public LoginInfoData getLoginInfoByName(String name){
        return foreverDao.getLoginInfoByName(name);
    }
    
    public UserInfoData getLoginInfo(String name,String password){
        return foreverDao.getLoginInfo(name, password);
    }
    
    public void addUser(User user) {
        foreverDao.addUser(user);
    }

    public List<User> getAllUser() {
        return foreverDao.getAllUser();
    }

    public boolean delUser(String id) {
        return foreverDao.delUser(id);
    }
    
    public List<User> getUsersByConditions(List<ForeverCriteria> conditions){
        return foreverDao.getUsersByConditions(conditions);
    }
    
    public List<List<Object[]>> exportExcel(){
    	return foreverDao.exportExcel();
    }
    
    public void operateMongoDB(){
        foreverDao.operateMongoDB();
    }
    
    public void saveFileInfoData(FileInfoData fileInfoData){
        foreverDao.saveFileInfoData(fileInfoData);
    }
    
    public List<FileInfoData> getFileInfoDataByIdOrName(List<ForeverCriteria> conditions){
        return foreverDao.getFileInfoDataByIdOrName(conditions);
    }
    
    public List<NewsInfoData> getNewsInfos(){
    	return foreverDao.getNewsInfos();
    }

}
