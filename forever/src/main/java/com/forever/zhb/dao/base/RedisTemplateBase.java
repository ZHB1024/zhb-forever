package com.forever.zhb.dao.base;

import org.springframework.data.redis.core.RedisTemplate;

import com.forever.zhb.utils.redis.RedisTemplateUtil;

public class RedisTemplateBase {
	
	private RedisTemplate redisTemplate;  
	protected RedisTemplateUtil redisTemplateUtil;
  
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplateUtil = new RedisTemplateUtil();
		this.redisTemplateUtil.setRedisTemplate(this.redisTemplate);
	}  

}
