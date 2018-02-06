package com.forever.zhb.controller.annotation;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.Constants;
import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.LoginLogInfoData;
import com.forever.zhb.service.LoginLogManager;

@Controller
@RequestMapping("/htgl/loginLogInfosController")
public class LoginLogInfosController {
	
	private Logger logger = LoggerFactory.getLogger(HttpClientController.class);
	
	@Resource(name="loginLogManager")
    private LoginLogManager loginLogManager;
	
	@RequestMapping(value = "/getLoginLogInfos",method = RequestMethod.GET)
	public String getLoginLogInfos(HttpServletRequest request,HttpServletResponse response){
		String start = request.getParameter("start");
		if (StringUtils.isBlank(start)) {
            start = "0";
        }
		Page<LoginLogInfoData> loginLogInfos = loginLogManager.getLoginLogInfoPage(Integer.parseInt(start),Constants.PAGE_SIZE);
		request.setAttribute("page", loginLogInfos);
		return "htgl.log.login.index";
	}
	

}
