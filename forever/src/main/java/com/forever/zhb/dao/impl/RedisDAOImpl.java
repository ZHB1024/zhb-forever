package com.forever.zhb.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.forever.zhb.dao.IRedisDAO;
import com.forever.zhb.dao.base.RedisTemplateBase;

public class RedisDAOImpl extends RedisTemplateBase implements IRedisDAO {
	
	protected Log log = LogFactory.getLog(this.getClass());

	public void addRedis(String name,String age) {
		redisTemplateUtil.set(name, age);
		log.info(name + " : " + redisTemplateUtil.get(name));
	}
	
	public Object getRedis(String key){
		return redisTemplateUtil.get(key);
	}
	
	public void addList(String key,List<?> value){
		redisTemplateUtil.setList(key, value);
	}
	
	public List<?> getList(String key){
		return redisTemplateUtil.getList(key);
	}

	@Override
	public void addSet(String key, Set<?> value) {
		redisTemplateUtil.setSet(key, value);
	}

	@Override
	public Set<?> getSet(String key) {
		return redisTemplateUtil.getSet(key);
	}

}
