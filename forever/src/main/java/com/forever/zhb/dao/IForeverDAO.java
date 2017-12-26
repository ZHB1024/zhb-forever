package com.forever.zhb.dao;

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

public interface IForeverDAO {

/*function_info*/    
    List<FunctionInfoData> getFunctionInfos();
    void addFunctionInfo(FunctionInfoData functionInfoData);
    FunctionInfoData getFunctionInfoByName(String name);

/*role_info*/   
    void addRole(RoleInfoData roleInfoData);
    RoleInfoData getRoleInfoByName(String name);
    List<RoleInfoData> getRoleInfos();

/*user_info*/
    void addUserInfo(UserInfoData userInfoData);
    UserInfoData getUserInfoByName(String name);
    UserInfoData getUserInfoById(String id);

/*login_info*/    
    void addLoginInfo(LoginInfoData loginInfoData);
    LoginInfoData getLoginInfoByName(String name);
    
    /*验证登录信息*/
    UserInfoData getLoginInfo(String name,String password);
    
    void addUser(User user);
    
    List<User> getAllUser();
    
    boolean delUser(String id);
    
    List<User> getUsersByConditions(List<ForeverCriteria> conditions);
    
    List<List<Object[]>> exportExcel();
    
    void operateMongoDB();
    
    void saveFileInfoData(FileInfoData fileInfoData);
    
    void addFileInfoData(FileInfoData fileInfoData);
    
    List<FileInfoData> getFileInfo(List<ForeverCriteria> conditions);
    
    ForeverPage<FileInfoData> getFileInfoPage(List<ForeverCriteria> conditions);
    
    List<FileInfoData> getFileInfoDataByIdOrName(List<ForeverCriteria> conditions);
    
    List<NewsInfoData> getNewsInfos();

}
