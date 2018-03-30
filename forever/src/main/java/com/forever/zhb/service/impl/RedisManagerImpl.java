package com.forever.zhb.service.impl;

import java.util.List;
import java.util.Set;

import com.forever.zhb.dao.IRedisDAO;
import com.forever.zhb.service.IRedisManager;

public class RedisManagerImpl implements IRedisManager{
	
	private IRedisDAO redisDao;

	public void addRedis(String name,String age) {
		redisDao.addRedis(name,age);
	}
	
	public Object getRedis(String key){
		return redisDao.getRedis(key);
	}
	
	public void addList(String key, List<?> value) {
		redisDao.addList(key, value);
	}

	public List<?> getList(String key) {
		return redisDao.getList(key);
	}
	
	
	
	public IRedisDAO getRedisDao() {
		return redisDao;
	}

	public void setRedisDao(IRedisDAO redisDao) {
		this.redisDao = redisDao;
	}

	@Override
	public void addSet(String key, Set<?> value) {
		this.redisDao.addSet(key, value);
	}

	@Override
	public Set<?> getSet(String key) {
		return this.redisDao .getSet(key);
	}
}
