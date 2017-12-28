package com.forever.zhb.jms.activemq.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.forever.zhb.Constants;
import com.forever.zhb.jms.activemq.JmsActiveMQManager;
import com.forever.zhb.proto.ProtoResult;
import com.forever.zhb.proto.support.ProtoConverter;

@Service
public class JmsActiveMQManagerImpl implements JmsActiveMQManager {
	
	private Logger logger = LoggerFactory.getLogger(JmsActiveMQManagerImpl.class);

	@Resource(name="jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;
	
	@Resource(name="jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;

/*------Quene begin---------------------------------------*/
	public void sendQueueDestinationMsg(Destination destination, final String msg) {
		logger.info("向队列" + destination.toString() + "发送了消息------------" + msg);
		jmsQueueTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
	
	public void sendQueueDestinationNameMsg(String destinationName, final String msg) {
		logger.info("向队列" + destinationName + "发送了消息------------" + msg);
		jmsQueueTemplate.send(destinationName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
	
	public void sendQueueMessage(final String msg) {
		String destination = jmsQueueTemplate.getDefaultDestination().toString();
		logger.info("向队列" + destination + "发送了消息------------" + msg);
		jmsQueueTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
	
	public void sendQueueRemoteMsg(String destinationName, byte[] msg) {
		logger.info("向队列" + destinationName + "发送了消息------------" + msg.toString());
		ProtoResult pr = new ProtoResult();
		pr.setProtoBytes(msg);
		jmsQueueTemplate.send(destinationName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(pr);
			}
		});
	}

	public TextMessage receiveQueueMessage(Destination destination) {
		Message m = jmsQueueTemplate.receive(destination);
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
	
	public TextMessage receiveQueueMessage(String destinationName) {
		Message m = jmsQueueTemplate.receive(destinationName);
		TextMessage tm = null;
		if (null != m) {
			tm = (TextMessage)m;
			try {
				logger.info("从队列" + destinationName + "收到了消息：\t" + tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return tm;
	}
	
	public com.google.protobuf.Message receiveQueueRemoteMsgByDesNamePath(String destinationName,String path) throws Exception {
		Message m = jmsQueueTemplate.receive(destinationName);
		if (null == m) {
			return null;
		}
		ObjectMessage om  = (ObjectMessage)m;
		Object object = om.getObject();
		if (null == object) {
			return null;
		}
		ProtoResult pr = (ProtoResult)object;
		ProtoConverter pci = new ProtoConverter();
		return pci.converFromProto(path, pr);
	}
	
/*------Quene end---------------------------------------*/

	
	
/*------Topic begin---------------------------------------*/
	@Override
	public void sendTopicMessage(String destinationName, String msg) {
		logger.info("向topic" + destinationName + "发送了消息------------" + msg);
		jmsTopicTemplate.send(destinationName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	@Override
	public TextMessage receiveTopicMessage(String destinationName) {
		Message m = jmsTopicTemplate.receive(destinationName);
		TextMessage tm = null;
		if (null != m) {
			tm = (TextMessage)m;
			try {
				logger.info("从topic" + destinationName + "收到了消息：\t" + tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return tm;
	}	
	
/*------Topic end---------------------------------------*/

}
