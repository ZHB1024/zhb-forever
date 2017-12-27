package com.forever.zhb.jms.activemq.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.forever.zhb.jms.activemq.IJmsActiveMQManager;

@Service
public class JmsActiveMQManagerImpl implements IJmsActiveMQManager {
	
	private Logger logger = LoggerFactory.getLogger(JmsActiveMQManagerImpl.class);

	@Resource(name="jmsTemplate")
	private JmsTemplate jmsTemplate;

	/**
	 * 向指定队列发送消息
	 */
	public void sendMessage(Destination destination, final String msg) {
		logger.info("向队列" + destination.toString() + "发送了消息------------" + msg);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	/**
	 * 向默认队列发送消息
	 */
	public void sendMessage(final String msg) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		logger.info("向队列" + destination + "发送了消息------------" + msg);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});

	}

	/**
	 * 接收消息
	 */
	public TextMessage receive(Destination destination) {
		Message m = jmsTemplate.receive(destination);
		TextMessage tm = null;
		if (null != m) {
			tm = (TextMessage)m;
			try {
				logger.info("从队列" + destination.toString() + "收到了消息：\t" + tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return tm;
	}

	/*public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}*/
	
}
