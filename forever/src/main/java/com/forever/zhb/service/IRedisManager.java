package com.forever.zhb.service;

import java.util.List;
import java.util.Set;

public interface IRedisManager {
	
	void addRedis(String name,String age);
	
	Object getRedis(String key);
	
   void addList(String key,List<?> value);
	
	List<?> getList(String key);
	
	void addSet(String key,Set<?> value);
	
	Set<?> getSet(String key);
	
	

}
