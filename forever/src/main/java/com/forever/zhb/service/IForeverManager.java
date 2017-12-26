package com.forever.zhb.service;

import java.util.List;

import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.criteria.ForeverPage;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.pojo.FunctionInfoData;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.NewsInfoData;
import com.forever.zhb.pojo.RoleInfoData;
import com.forever.zhb.pojo.User;
import com.forever.zhb.pojo.UserInfoData;

public interface IForeverManager {
    
/*function_info*/    
    void addFunctionInfo(FunctionInfoData functionInfoData);
    FunctionInfoData getFunctionInfoByName(String name);
    List<FunctionInfoData> getFunctionInfos();

/*role_info*/    
    void addRole(RoleInfoData roleInfoData);
    RoleInfoData getRoleInfoByName(String name);
    List<RoleInfoData> getRoleInfos();

/*user_info*/   
    void addUserInfo(UserInfoData userInfoData);
    UserInfoData getUserInfoByName(String name);
    UserInfoData getUserInfoById(String id);
    
    UserInfoData getLoginInfo(String name,String password);

/*login_info*/    
    void addLoginInfo(LoginInfoData loginInfoData);
    LoginInfoData getLoginInfoByName(String name);
    
    void addUser(User user);
    List<User> getAllUser();
     
    boolean delUser(String id);
    
    List<User> getUsersByConditions(List<ForeverCriteria> conditions);
    
    List<List<Object[]>> exportExcel();
    
    void operateMongoDB();
    
    void saveFileInfoData(FileInfoData fileInfoData);
    
    void addFileInfoData(FileInfoData fileInfoData);
    
    List<FileInfoData> getFileInfoDataByIdOrName(List<ForeverCriteria> conditions);
    
    ForeverPage<FileInfoData> getFileInfoPage(List<ForeverCriteria> conditions);
    
    List<FileInfoData> getFileInfo(List<ForeverCriteria> conditions);
    
    List<NewsInfoData> getNewsInfos();
}
