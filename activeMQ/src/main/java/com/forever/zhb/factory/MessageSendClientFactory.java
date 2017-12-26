package com.forever.zhb.factory;

import com.forever.zhb.Constants;
import com.forever.zhb.send.MessageSender;
import com.forever.zhb.spring.bean.locator.SpringBeanLocator;

public class MessageSendClientFactory {
	
	public static MessageSender getSearchServiceClientBean() {
        Object bean = SpringBeanLocator.getInstance(
                Constants.JMS_CONF).getBean(
                Constants.MESSAGE_SENDER_CLIENT);
        return (MessageSender) bean;
    }

}
