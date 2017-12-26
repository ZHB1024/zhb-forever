package com.forever.zhb.send;

import org.springframework.jms.core.JmsTemplate;

public class MessageSender {
	
	public JmsTemplate jmsTemplate;    
    
    /**   
     * send message   
     */    
    public void sendMessage(){    
        jmsTemplate.convertAndSend("hello jms!");    
    }    
    public void setJmsTemplate(JmsTemplate jmsTemplate) {    
        this.jmsTemplate = jmsTemplate;    
    }    

}
