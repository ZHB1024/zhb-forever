package com.forever.zhb.service;

import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.UserInfoData;

public interface UserManager {

    /*userinfo*/

    void initUserAccountInfo(UserInfoData userInfoData);

    void saveOrUpdate(UserInfoData userInfoData);

    UserInfoData getUserInfoByName(String name);

    UserInfoData getUserInfoById(String id);

    Page<UserInfoData> getUserInfos(int start,int pageSize);


}
