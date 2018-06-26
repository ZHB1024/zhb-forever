package com.forever.zhb.dao;

import com.forever.zhb.pojo.UserInfoData;
import java.util.List;

public interface UserDAO {

    void saveOrUpdate(UserInfoData userInfoData);

    UserInfoData getUserInfoByName(String name);

    UserInfoData getUserInfoById(String id);

    List<UserInfoData> getUserInfos(int start,int pageSize);

    int countUserInfos();

    void deleteUserById(String id);

}
