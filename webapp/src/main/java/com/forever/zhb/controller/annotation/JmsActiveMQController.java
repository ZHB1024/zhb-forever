package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/jmsActiveMQController")
public class JmsActiveMQController {
	
	private Logger log = LoggerFactory.getLogger(JmsActiveMQController.class);
	
	/*MessageSender messageSender = MessageSendClientFactory.getSearchServiceClientBean();
	
	@RequestMapping("/jMSTest")
	public void JMSTest(HttpServletRequest request,HttpServletResponse response){
		if (null != messageSender) {
			messageSender.sendMessage();    
		}
	}*/

}
