package com.forever.zhb.controller.annotation;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.utils.MessageUtil;

@Controller
@RequestMapping("/blockQueueController")
public class BlockQueueController extends BasicController {
	
	private Logger logger = LoggerFactory.getLogger(BlockQueueController.class);

	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = MessageUtil.getMessage("0001", new Object[] { "张会彬" });
		logger.info(message);
		return "test.body.index";
	}
	
	public static void main(String[] args) {
		
	}

}
