package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.forever.zhb.jms.activemq.IJmsActiveMQManager;


@Controller
@RequestMapping("/htgl/jmsActiveMQController")
public class JmsActiveMQController {
	
	private Logger log = LoggerFactory.getLogger(JmsActiveMQController.class);
	
	/*MessageSender messageSender = MessageSendClientFactory.getSearchServiceClientBean();
	
	@RequestMapping("/jMSTest")
	public void JMSTest(HttpServletRequest request,HttpServletResponse response){
		if (null != messageSender) {
			messageSender.sendMessage();    
		}
	}*/
	
	// 队列名zhb.demo
		@Resource(name = "demoQueueDestination")
		private Destination demoQueueDestination;

		// 队列消息生产者
		@Resource(name = "jmsActiveMQManagerImpl")
		private IJmsActiveMQManager producer;
		
		@RequestMapping(value="/index",method=RequestMethod.GET)
	    public String index(HttpServletRequest request,HttpServletResponse response){
	        log.info("------------welcome");        
	        return "htgl.jms.activemq.index";
	    }

		@RequestMapping(value = "/producer", method = RequestMethod.GET)
		public String producer(HttpServletRequest request,HttpServletResponse response) {
			log.info("------------go producer");
			
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = dateFormat.format(now);
			log.info(time);
			
			request.setAttribute("time", time);
			return "htgl.jms.activemq.producer";
		}

		@RequestMapping(value = "/onsend", method = RequestMethod.POST)
		public String producer(@RequestParam("message") String message,HttpServletRequest request,HttpServletResponse response) {
			log.info("------------send to jms");
			producer.sendMessage(demoQueueDestination, message);
			return "htgl.jms.activemq.index";
		}

		@RequestMapping(value = "/receive", method = RequestMethod.GET)
		public String queue_receive(HttpServletRequest request,HttpServletResponse response) throws JMSException {
			log.info("------------receive message");
			TextMessage tm = producer.receive(demoQueueDestination);
			request.setAttribute("textMessage", tm.getText());
			return "htgl.jms.activemq.receiver";
		}

		/*
		 * ActiveMQ Manager Test
		 */
		@RequestMapping(value = "/jms", method = RequestMethod.GET)
		public String jmsManager(HttpServletRequest request,HttpServletResponse response) throws IOException {
			log.info("------------jms manager");
			JMXServiceURL url = new JMXServiceURL("");
			JMXConnector connector = JMXConnectorFactory.connect(url);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			return "htgl.jms.activeMQ.index";
		}

}
