package com.forever.zhb.dao;

import java.util.List;
import java.util.Set;

public interface IRedisDAO {
	
	void addRedis(String name,String age);
	
	Object getRedis(String key);
	
	void addList(String key,List<?> value);
	
	List<?> getList(String key);
	
	void addSet(String key,Set<?> value);
	
	Set<?> getSet(String key);

}
