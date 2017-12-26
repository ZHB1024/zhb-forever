package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htgl/xssController")
public class XSSController {
	
	private Logger log = LoggerFactory.getLogger(XSSController.class);
	
	@RequestMapping("/testXSS")
	public String testXSS(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("first_name");
		request.setAttribute("last_name", name);
        return "htgl.xss.index";
	}
	
}
