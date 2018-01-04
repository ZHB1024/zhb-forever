package com.forever.zhb.controller.annotation;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/htgl/kafkaController")
public class KafkaController {
	
	private Logger logger = LoggerFactory.getLogger(KafkaController.class);
	
	@Resource(name="KafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;  
	
	@RequestMapping(value="sendTest",method=RequestMethod.GET)
	public String sendTest(HttpServletRequest request,HttpServletResponse response,String id){
		logger.info(id);
		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send("defaultTopic", "name", "张会彬");
		if (null != result) {
			try {
				SendResult<String, String> sends = result.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return "htgl.kafka.index";
	}

}
