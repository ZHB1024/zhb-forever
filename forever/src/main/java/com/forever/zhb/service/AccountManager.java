package com.forever.zhb.service;

import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.UserInfoData;

public interface AccountManager {

    void init(UserInfoData user);

    void addOrUpdate(LoginInfoData loginInfoData);

    LoginInfoData getAccountByName(String name);

}
