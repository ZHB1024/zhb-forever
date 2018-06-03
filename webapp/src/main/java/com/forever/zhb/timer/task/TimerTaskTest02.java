package com.forever.zhb.timer.task;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTaskTest02 extends TimerTask {
    
    protected Logger logger = LoggerFactory.getLogger(TimerTaskTest02.class);

    @Override
    public void run() {
        logger.info("-----开始执行TimerTaskTest02定时任务-----");
        logger.info(Thread.currentThread().getName());
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("-----结束执行TimerTaskTest02定时任务-----");
    }
/*    @Override
    public void doRun() throws Exception {
        logger.info("-----开始执行TimerTaskTest02定时任务-----");
        logger.info(Thread.currentThread().getName());
        Thread.currentThread().sleep(10000);
        logger.info("-----结束执行TimerTaskTest02定时任务-----");
    }
*/
}
