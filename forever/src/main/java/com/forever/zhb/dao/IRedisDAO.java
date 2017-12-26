package com.forever.zhb.dao;

import java.util.List;

public interface IRedisDAO {
	
	void addRedis(String name,String age);
	
	Object getRedis(String key);
	
	void addList(String key,List<?> value);
	
	List<?> getList(String key);

}
