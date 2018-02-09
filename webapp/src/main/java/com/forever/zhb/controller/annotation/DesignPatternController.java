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



@Controller
@RequestMapping("/designPatterController")
public class DesignPatternController {
	
	private Logger logger = LoggerFactory.getLogger(DesignPatternController.class);
	
	/*代理*/
	@RequestMapping(value = "/proxy",method = RequestMethod.GET)
	public void getLoginLogInfos(HttpServletRequest request,HttpServletResponse response){
		Person person = (Person)new HomeLink().bind(new Master("校长"));
		person.searchHouse();
	}
	
	
	

}
