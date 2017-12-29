package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.util.Calendar;

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

import com.forever.zhb.Constants;
import com.forever.zhb.jms.activemq.ActiveMQConstants;
import com.forever.zhb.jms.activemq.JmsActiveMQManager;
import com.forever.zhb.proto.NewsProto;
import com.forever.zhb.proto.NewsProto.News;

@Controller
@RequestMapping("/htgl/jmsActiveMQController")
public class JmsActiveMQController {

	private Logger log = LoggerFactory.getLogger(JmsActiveMQController.class);

	//默认队列名zhb.demo 在activemq.xml配置
	@Resource(name = "zhbQueueDestination")
	private Destination zhbQueueDestination;

	// 队列消息生产者
	@Resource(name = "jmsActiveMQManagerImpl")
	private JmsActiveMQManager jmsActiveMQManager;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "htgl.jms.activemq.index";
	}
	
/*queue*/
	@RequestMapping(value = "/toQueueProducer", method = RequestMethod.GET)
	public String toQueueProducer(HttpServletRequest request, HttpServletResponse response) {
		return "htgl.jms.activemq.queue.producer";
	}

	@RequestMapping(value = "/sendQueueMes", method = RequestMethod.POST)
	public String sendQueueMes(@RequestParam("message") String message, HttpServletRequest request,
			HttpServletResponse response) {
		//jmsActiveMQManager.sendQueueDestinationNameMsg(ActiveMQConstants.ZHB_QUEUE_DESTINATION, message);
		NewsProto.News.Builder newsBuilder = NewsProto.News.newBuilder(); 
		newsBuilder.setId("123");
		newsBuilder.setTitle("测试");
		newsBuilder.setContent("测试一下不行呀");
		newsBuilder.setCreateTime(Calendar.getInstance().getTimeInMillis());
		News news = newsBuilder.build();
		byte[] newsByte = news.toByteArray();
		jmsActiveMQManager.sendQueueRemoteMsg(ActiveMQConstants.ZHB_QUEUE_DESTINATION, newsByte);
		return "htgl.jms.activemq.index";
	}

	@RequestMapping(value = "/receiveQueueMes", method = RequestMethod.GET)
	public String receiveQueueMes(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String textMessage = "";
		try {
			com.google.protobuf.Message mes = jmsActiveMQManager.receiveQueueRemoteMsgByDesNamePath(ActiveMQConstants.ZHB_QUEUE_DESTINATION, Constants.NEWSPROTO_PATH);
			if (null != mes) {
				NewsProto.News news2 = (NewsProto.News)mes;
				log.info("从队列" + ActiveMQConstants.ZHB_QUEUE_DESTINATION + "收到了消息：\t" + news2.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TextMessage queneMessage = jmsActiveMQManager.receiveQueueMessage(ActiveMQConstants.ZHB_QUEUE_DESTINATION);
		if (null != queneMessage) {
			textMessage = queneMessage.getText();
		}
		request.setAttribute("textMessage", textMessage);
		return "htgl.jms.activemq.queue.receiver";
	}
	
/*topic*/	
	@RequestMapping(value = "/toTopicProducer", method = RequestMethod.GET)
	public String toTopicProducer(HttpServletRequest request, HttpServletResponse response) {
		return "htgl.jms.activemq.topic.producer";
	}

	@RequestMapping(value = "/sendTopicMes", method = RequestMethod.POST)
	public String sendTopicMes(@RequestParam("message") String message, HttpServletRequest request,
			HttpServletResponse response) {
		jmsActiveMQManager.sendTopicMessage(ActiveMQConstants.ZHB_TOPIC_DESTINATION, message);
		return "htgl.jms.activemq.index";
	}

	@RequestMapping(value = "/receiveTopicMes", method = RequestMethod.GET)
	public String receiveTopicMes(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String textMessage = "";
		TextMessage topicMessage = jmsActiveMQManager.receiveTopicMessage(ActiveMQConstants.ZHB_TOPIC_DESTINATION);
		if (null != topicMessage) {
			textMessage = topicMessage.getText();
		}
		request.setAttribute("textMessage", textMessage);
		return "htgl.jms.activemq.topic.receiver";
	}
	
	

	/*
	 * ActiveMQ Manager Test
	 */
	@RequestMapping(value = "/jms", method = RequestMethod.GET)
	public String jmsManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("------------jms manager");
		JMXServiceURL url = new JMXServiceURL("");
		JMXConnector connector = JMXConnectorFactory.connect(url);
		connector.connect();
		MBeanServerConnection connection = connector.getMBeanServerConnection();
		return "htgl.jms.activeMQ.index";
	}

}
