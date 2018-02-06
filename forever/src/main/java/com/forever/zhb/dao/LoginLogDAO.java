package com.forever.zhb.dao;

import java.util.List;

import com.forever.zhb.pojo.LoginLogInfoData;

public interface LoginLogDAO {
	
	void save(LoginLogInfoData loginLogInfoData);
	
	List<LoginLogInfoData> getLoginLogInfoByUserName(String userName);
	
	int countLoginLogInfoByUserName(String userName);
	
	List<LoginLogInfoData> getLoginLogInfoByUserNamePage(String userName,int start,int pageSize);
	
	List<LoginLogInfoData> getLoginLogInfo();
	
	int countLoginLogInfo();
	
	List<LoginLogInfoData> getLoginLogInfoPage(int start,int pageSize);
	
}
