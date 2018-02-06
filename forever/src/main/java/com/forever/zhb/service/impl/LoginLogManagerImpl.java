package com.forever.zhb.service.impl;

import java.util.List;

import com.forever.zhb.dao.LoginLogDAO;
import com.forever.zhb.page.Page;
import com.forever.zhb.page.PageUtil;
import com.forever.zhb.pojo.LoginLogInfoData;
import com.forever.zhb.service.LoginLogManager;

public class LoginLogManagerImpl implements LoginLogManager {
	
	private LoginLogDAO loginLogDao;
	
    public void setLoginLogDao(LoginLogDAO loginLogDao) {
		this.loginLogDao = loginLogDao;
	}

	@Override
	public void save(LoginLogInfoData loginLogInfoData) {
		loginLogDao.save(loginLogInfoData);
	}

	@Override
	public List<LoginLogInfoData> getLoginLogInfoByUserName(String userName) {
		return loginLogDao.getLoginLogInfoByUserName(userName);
	}
	
	@Override
	public Page<LoginLogInfoData> getLoginLogInfoByUserNamePage(String userName,int start,int pageSize) {
		int count = loginLogDao.countLoginLogInfoByUserName(userName);
		if (0 == count) {
			return Page.EMPTY_PAGE;
		}
		if (start >= count) {
			start = 0;
		}
		List<LoginLogInfoData> loginLogInfoDatas = loginLogDao.getLoginLogInfoByUserNamePage(userName,start,pageSize);
		
		return PageUtil.getPage(loginLogInfoDatas.iterator(), start, pageSize, count);
	}

	@Override
	public List<LoginLogInfoData> getLoginLogInfo() {
		return loginLogDao.getLoginLogInfo();
	}
	
	@Override
	public Page<LoginLogInfoData> getLoginLogInfoPage(int start,int pageSize) {
		int count = loginLogDao.countLoginLogInfo();
		if (0 == count) {
			return Page.EMPTY_PAGE;
		}
		if (start >= count) {
			start = 0;
		}
		List<LoginLogInfoData> loginLogInfoDatas = loginLogDao.getLoginLogInfoPage(start,pageSize);
		return PageUtil.getPage(loginLogInfoDatas.iterator(), start, pageSize, count);
	}

}
