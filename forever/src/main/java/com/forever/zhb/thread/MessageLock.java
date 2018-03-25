package com.forever.zhb.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MessageLock {

    private ReentrantLock lock = new ReentrantLock();
    
    private String name;

    public MessageLock(String name) {
        this.name = name;
    }

    public void smsSender() {
        lock.lock();
        System.out.println(name + "--锁住5s--smsSender-----------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void mailSender() throws InterruptedException {
        if(lock.tryLock(2000,TimeUnit.MILLISECONDS)) {
            try {
                System.out.println(name + "-获取到锁了---mailSender-----------");
            }finally {
                lock.unlock();
            }
        }else {
            System.out.println(name + "--等待2s没获取到锁--mailSender-----------");
        }
        
    }

}
