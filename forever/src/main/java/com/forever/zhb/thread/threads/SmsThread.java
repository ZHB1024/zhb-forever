package com.forever.zhb.thread.threads;

import com.forever.zhb.thread.Message;
import com.forever.zhb.thread.MessageLock;

public class SmsThread extends Thread {
    
    private Message message;
    private MessageLock messageLock;
    
    
    public SmsThread(Message message,MessageLock lock) {
        this.message = message;
        this.messageLock = lock;
    }
    
    
    @Override
    public void run() {
        //message.smsSender();
        try {
			messageLock.smsSender();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
