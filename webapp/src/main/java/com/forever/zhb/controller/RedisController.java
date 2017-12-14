package com.forever.zhb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.forever.zhb.service.IRedisManager;
import com.forever.zhb.vo.RedisListVO;

public class RedisController extends MultiActionController {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private IRedisManager redisManager;
	
	public String toRedis(HttpServletRequest request,HttpServletResponse response){
        return "/htgl/redis/toRedis";
    }
	
	public String addRedis(HttpServletRequest request,HttpServletResponse response){
		String country = request.getParameter("country");
		List<String> countries = new ArrayList<String>();
		List<?> list = redisManager.getList("countries");
		if (null != list && !list.isEmpty()) {
			countries.addAll((List<String>)list);
			countries.add(country);
		}else{
			countries.add(country);
			countries.add("China");
			countries.add("America");
		}
		redisManager.addList("countries", countries);
		List<?> result = redisManager.getList("countries");
		/*redisManager.addRedis("wode", "hello");
		List<String> result = new ArrayList<String>();
		Object value = redisManager.getRedis("wode");
		if (null != value) {
			result.add(value.toString());
		}*/
		RedisListVO vo = new RedisListVO();
		vo.setKey("countries");
		vo.setValues(result);
		request.setAttribute("redis", vo);
        return "/htgl/redis/result";
    }
	
	

	
	
	
	public IRedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(IRedisManager redisManager) {
		this.redisManager = redisManager;
	}

}
