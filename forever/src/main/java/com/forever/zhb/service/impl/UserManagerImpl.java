package com.forever.zhb.service.impl;

import com.forever.zhb.dao.UserDAO;
import com.forever.zhb.page.Page;
import com.forever.zhb.page.PageUtil;
import com.forever.zhb.pojo.LoginLogInfoData;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.UserManager;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagerImpl implements UserManager {

    private final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    private UserDAO userDao;


    @Override
    public void saveOrUpdate(UserInfoData userInfoData){
        userDao.saveOrUpdate(userInfoData);
    }

    @Override
    public UserInfoData getUserInfoByName(String name){
        return userDao.getUserInfoByName(name);
    }

    @Override
    public UserInfoData getUserInfoById(String id){
        return userDao.getUserInfoById(id);
    }

    @Override
    public Page<UserInfoData> getUserInfos(int start,int pageSize){
        int count = userDao.countUserInfos();
        if (0 == count) {
            return Page.EMPTY_PAGE;
        }
        if (start >= count) {
            start = 0;
        }
        List<UserInfoData> datas = userDao.getUserInfos(start, pageSize);
        return PageUtil.getPage(datas.iterator(), start, pageSize, count);
    }


    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
}
