package com.forever.zhb.timer.task;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTaskTest03 extends TimerTask {

    protected Logger logger = LoggerFactory.getLogger(TimerTaskTest03.class);

    @Override
    public void run() {
        logger.info("-----开始执行TimerTaskTest03定时任务-----");
        logger.info(Thread.currentThread().getName());
        logger.info("-----TimerTaskTest03抛异常，其他timerTask也无法运行-----");
        throw new RuntimeException("TimerTaskTest03");
        //logger.info("-----结束执行TimerTaskTest03定时任务-----");
    }
/*    @Override
    public void doRun() throws Exception {
        logger.info("-----开始执行TimerTaskTest03定时任务-----");
        logger.info(Thread.currentThread().getName());
        throw new RuntimeException("TimerTaskTest03");
        //logger.info("-----结束执行TimerTaskTest03定时任务-----");
    }
*/
}
