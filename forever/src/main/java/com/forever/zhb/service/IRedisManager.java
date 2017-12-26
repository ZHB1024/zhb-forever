package com.forever.zhb.service;

import java.util.List;

public interface IRedisManager {
	
	void addRedis(String name,String age);
	
	Object getRedis(String key);
	
   void addList(String key,List<?> value);
	
	List<?> getList(String key);

}
