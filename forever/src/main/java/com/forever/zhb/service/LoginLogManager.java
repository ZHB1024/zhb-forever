package com.forever.zhb.service;

import java.util.List;

import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.LoginLogInfoData;

public interface LoginLogManager {
	
	void save(LoginLogInfoData loginLogInfoData);
	
	List<LoginLogInfoData> getLoginLogInfoByUserName(String userName);
	
	Page<LoginLogInfoData> getLoginLogInfoByUserNamePage(String userName,int start,int pageSize);
	
	List<LoginLogInfoData> getLoginLogInfo();
	
	Page<LoginLogInfoData> getLoginLogInfoPage(int start,int pageSize);
	

}
