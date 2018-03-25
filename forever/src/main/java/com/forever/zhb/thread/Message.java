package com.forever.zhb.thread;

public class Message {
    
    String name ;
    
    public Message(String name) {
        this.name = name;
    }
    
    public synchronized void smsSender() {
        System.out.println(name + "----smsSender-----------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void mailSender() {
        System.out.println(name + "----mailSender-----------");
    }

}