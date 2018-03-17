package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.design.pattern.proxy.HomeLink;
import com.forever.zhb.design.pattern.proxy.Master;
import com.forever.zhb.design.pattern.proxy.Person;
import com.forever.zhb.design.pattern.proxy.cglib.ProxyFactory;
import com.forever.zhb.design.pattern.proxy.cglib.User;



@Controller
@RequestMapping("/designPatterController")
public class DesignPatternController {
    
    private Logger logger = LoggerFactory.getLogger(DesignPatternController.class);
    
    /*动态代理*/
    @RequestMapping(value = "/proxy",method = RequestMethod.GET)
    public void getLoginLogInfos(HttpServletRequest request,HttpServletResponse response){
        Person person = (Person)new HomeLink().bind(new Master("校长"));
        person.searchHouse();
    }
    
    /*cglib代理*/
    @RequestMapping(value = "/cglibProxy",method = RequestMethod.GET)
    public void cglibProxy(HttpServletRequest request,HttpServletResponse response){
        User user = (User)new ProxyFactory(new User()).getProxyInstance();
        user.save();
    }
    
    
    

}
