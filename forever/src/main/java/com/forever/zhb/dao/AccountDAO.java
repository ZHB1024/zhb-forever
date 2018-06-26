package com.forever.zhb.dao;

import com.forever.zhb.pojo.LoginInfoData;

public interface AccountDAO {

    void addOrUpdate(LoginInfoData loginInfoData);

    LoginInfoData getAccountByName(String name);

    void deleteAccountByUserId(String userId);

}
