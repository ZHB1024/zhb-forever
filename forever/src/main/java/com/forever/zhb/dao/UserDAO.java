package com.forever.zhb.dao;

import com.forever.zhb.pojo.UserInfoData;
import java.util.List;

public interface UserDAO {

    void saveOrUpdate(UserInfoData userInfoData);

    UserInfoData getUserInfoByName(String name);

    UserInfoData getUserInfoById(String id);

    List<UserInfoData> getUserInfos(String realName,String deleteFlag,int start,int pageSize);

    int countUserInfos(String realName,String deleteFlag);

    void deleteUserById(String id);

}
