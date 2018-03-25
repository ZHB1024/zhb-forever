package com.forever.zhb.thread.threads;

import com.forever.zhb.thread.Message;
import com.forever.zhb.thread.MessageLock;

public class MailThread extends Thread {

    Message message;
    private MessageLock messageLock;

    public MailThread(Message message,MessageLock lock) {
        this.message = message;
        this.messageLock = lock;
    }

    @Override
    public void run() {
        //message.mailSender();
        try {
            messageLock.mailSender();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
