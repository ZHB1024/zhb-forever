package com.forever.zhb.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExecutorRunnableTask03 implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(ScheduledExecutorRunnableTask03.class);

    @Override
    public void run() {
        logger.info("------begin ScheduledExecutorRunnableTask03 Runnable -------");
        logger.info(Thread.currentThread().getName());
        throw new RuntimeException("ScheduledExecutorRunnableTask03");
       // logger.info("------end ScheduledExecutorRunnableTask03 Runnable -------");

    }

}
