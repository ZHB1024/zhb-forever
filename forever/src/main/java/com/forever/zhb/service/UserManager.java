package com.forever.zhb.service;

import com.forever.zhb.pojo.UserInfoData;
import java.util.List;
import com.forever.zhb.page.Page;

public interface UserManager {

    void saveOrUpdate(UserInfoData userInfoData);

    UserInfoData getUserInfoByName(String name);

    UserInfoData getUserInfoById(String id);

    Page<UserInfoData> getUserInfos(int start,int pageSize);

}
