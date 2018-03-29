package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.design.pattern.chainOfResponsibility.BossLeader;
import com.forever.zhb.design.pattern.chainOfResponsibility.DirectorLeader;
import com.forever.zhb.design.pattern.chainOfResponsibility.Leader;
import com.forever.zhb.design.pattern.chainOfResponsibility.LeaveRequest;
import com.forever.zhb.design.pattern.chainOfResponsibility.ManagerLeader;
import com.forever.zhb.design.pattern.proxy.HomeLink;
import com.forever.zhb.design.pattern.proxy.Master;
import com.forever.zhb.design.pattern.proxy.Person;
import com.forever.zhb.design.pattern.proxy.cglib.ProxyFactory;
import com.forever.zhb.design.pattern.proxy.cglib.User;
import com.forever.zhb.design.pattern.singleton.Singleton;



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
    
    
    /*单例*/
    @RequestMapping(value = "/singleton",method = RequestMethod.GET)
    public void singleton(HttpServletRequest request,HttpServletResponse response){
        Singleton singleton = Singleton.getInstance();
    }
    
    /*职责链*/
    @RequestMapping(value = "/chainOfResponsibility",method = RequestMethod.GET)
    public void chainOfResponsibility(HttpServletRequest request,HttpServletResponse response){
    	Leader director = new DirectorLeader("张三");
    	Leader manager = new ManagerLeader("李四");
    	Leader boss = new BossLeader("王五");
    	
    	director.setNextLeader(manager);
    	manager.setNextLeader(boss);
    	
    	LeaveRequest leaveRequest = new LeaveRequest("小明", 10, "回家");
    	
    	director.handerRequest(leaveRequest);
    }
    
    
    
    
    public static void main(String[] args) {
    	Leader director = new DirectorLeader("张三");
    	Leader manager = new ManagerLeader("李四");
    	Leader boss = new BossLeader("王五");
    	
    	director.setNextLeader(manager);
    	manager.setNextLeader(boss);
    	
    	LeaveRequest leaveRequest = new LeaveRequest("小明", 10, "回家");
    	
    	director.handerRequest(leaveRequest);
    }
    
    

}
