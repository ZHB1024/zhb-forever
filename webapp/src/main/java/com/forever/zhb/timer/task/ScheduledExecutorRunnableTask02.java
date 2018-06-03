package com.forever.zhb.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExecutorRunnableTask02 implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ScheduledExecutorRunnableTask02.class);
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        logger.info("------begin ScheduledExecutorRunnableTask02 Runnable -------");
        logger.info(Thread.currentThread().getName());
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
        logger.info("------end ScheduledExecutorRunnableTask02 Runnable -------");

    }

}
