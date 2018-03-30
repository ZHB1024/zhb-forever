package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.service.IRedisManager;
import com.forever.zhb.vo.RedisListVO;

@Controller
@RequestMapping("/htgl/redisController")
public class RedisController {

    @Resource(name="redisManager")
    private IRedisManager redisManager;

    @RequestMapping(value = "/toRedis",method = RequestMethod.GET)
    public String toRedis(HttpServletRequest request, HttpServletResponse response) {
        return "htgl.redis.index";
    }

    @RequestMapping(value = "/addRedis",method = RequestMethod.POST)
    public String addRedis(HttpServletRequest request, HttpServletResponse response) {
        String country = request.getParameter("country");
        List<String> countries = new ArrayList<String>();
        List<?> list = redisManager.getList("countries");
        if (null != list && !list.isEmpty()) {
            countries.addAll((List<String>) list);
            countries.add(country);
        } else {
            countries.add(country);
            countries.add("China");
            countries.add("America");
        }
        redisManager.addList("countries", countries);
        List<?> result = redisManager.getList("countries");
        /*
         * redisManager.addRedis("wode", "hello"); List<String> result = new
         * ArrayList<String>(); Object value = redisManager.getRedis("wode"); if (null
         * != value) { result.add(value.toString()); }
         */
        Set<String> sets = new TreeSet<String>();
        sets.add("11");
        sets.add("33");
        sets.add("22");
        redisManager.addSet("number", sets);
        Set<?> setTemps = redisManager.getSet("number");
        Set<?> setTemps2 = redisManager.getSet("number");
        RedisListVO vo = new RedisListVO();
        vo.setKey("countries");
        vo.setValues(result);
        request.setAttribute("redis", vo);
        return "htgl.redis.result";
    }

}
