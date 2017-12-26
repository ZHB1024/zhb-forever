package com.forever.zhb.jms.activemq;

import javax.jms.Destination;
import javax.jms.TextMessage;

public interface IJmsActiveMQManager {
	
	void sendMessage(Destination destination, final String msg);
	
	void sendMessage(final String msg);
	
	TextMessage receive(Destination destination);

}
