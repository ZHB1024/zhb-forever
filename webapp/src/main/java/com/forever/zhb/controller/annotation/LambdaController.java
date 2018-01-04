package com.forever.zhb.controller.annotation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/htgl/lambdaController")
public class LambdaController {
	
	private Logger logger = LoggerFactory.getLogger(LambdaController.class);
	
	private org.apache.log4j.Logger logger1 = org.apache.log4j.Logger.getLogger(getClass());
	
	@RequestMapping(value="mapTest",method=RequestMethod.GET)
	public String mapTest(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("color", "red");
		map.put("name", "hello word");
		
		map.forEach((k,v)->{
			logger.info("key: {},value: {}",new Object[]{k,v});
		});
		
		return "htgl.lambda.index";
	}

}
