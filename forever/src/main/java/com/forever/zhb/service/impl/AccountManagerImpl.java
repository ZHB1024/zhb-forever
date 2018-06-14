package com.forever.zhb.service.impl;

import com.forever.zhb.dao.AccountDAO;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.AccountManager;
import com.forever.zhb.utils.PasswordUtil;
import java.util.Calendar;

public class AccountManagerImpl implements AccountManager {

    private AccountDAO accountDao;


    @Override
    public void init(UserInfoData user){
        LoginInfoData data = new LoginInfoData();
        data.setPassword(PasswordUtil.encrypt(user.getName(),PasswordUtil.DEFAULT_PASSWORD,PasswordUtil.getStaticSalt()));
        data.setUserInfoData(user);
        data.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        data.setCreateTime(Calendar.getInstance());
        accountDao.addOrUpdate(data);
        System.out.println(data.getId());
        System.out.println(data.getPassword());
    }

    @Override
    public void addOrUpdate(LoginInfoData loginInfoData) {
        accountDao.addOrUpdate(loginInfoData);
    }

    @Override
    public LoginInfoData getAccountByName(String name) {
        return accountDao.getAccountByName(name);
    }




    public void setAccountDao(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }
}
