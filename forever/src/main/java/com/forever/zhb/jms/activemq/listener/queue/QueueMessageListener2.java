package com.forever.zhb.jms.activemq.listener.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueMessageListener2 implements MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(QueueMessageListener2.class);

	@Override
	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
        try {
        	logger.info("QueueMessageListener2监听到了文本消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

}
