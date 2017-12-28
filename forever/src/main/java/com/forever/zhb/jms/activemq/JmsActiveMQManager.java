package com.forever.zhb.jms.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public interface JmsActiveMQManager {
	
	/**
	 * 向指定队列发送消息
	 * @param destination
	 * @param msg
     * @return 
	 */
	void sendQueueDestinationMsg(Destination destination, final String msg);
	
	/**
	 * 向指定队列发送消息
	 * @param destinationName
	 * @param msg
     * @return 
	 */
	void sendQueueDestinationNameMsg(String destinationName, final String msg);
	
	/**
	 * 向默认队列发送消息
	 * @param msg
     * @return 
	 */
	void sendQueueMessage(final String msg);
	
	/**
	 * 向列发送消息
	 * @param destinationName
	 * @param msg
	 * @return 
	 */
	void sendQueueRemoteMsg(String destinationName, byte[] msg) ;
	
	/**
	 * 接收消息
	 * @param destination
     * @return TextMessage
	 */
	TextMessage receiveQueueMessage(Destination destination);
	/**
	 * 接收消息
	 * @param destinationName
     * @return TextMessage
	 */
	TextMessage receiveQueueMessage(String destinationName);
	
	com.google.protobuf.Message receiveQueueRemoteMsgByDesNamePath(String destinationName,String path)throws Exception;

/*------Quene end---------------------------------------*/
	
	
/*------Topic begin---------------------------------------*/
	/**
	 * 向指定topic发送消息
	 * @param destinationName
	 * @param msg
     * @return 
	 */
	void sendTopicMessage(String destinationName, final String msg);
	/**
	 * 接收消息
	 * @param destinationName
     * @return TextMessage
	 */
	TextMessage receiveTopicMessage(String destinationName);
/*------Topic end---------------------------------------*/	

}
