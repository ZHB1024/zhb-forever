package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.mybatis.pojo.User;
import com.forever.zhb.mybatis.service.UserService;

@Controller
@RequestMapping("/htgl/mybatisController")
public class MybatisController {
	
	private Logger log = LoggerFactory.getLogger(MybatisController.class);
	
	@Resource(name="userService")
    private UserService userService;
    
    /*to修改账号*/
    @RequestMapping("/getUsers")
    public String getUsers(HttpServletRequest request,HttpServletResponse response){
        User user = this.userService.getUserById(1);  
        List<User> users = new ArrayList<User>();
        users.add(user);
        request.setAttribute("users", users);
        return "htgl.mybatis.index";
    }

}
