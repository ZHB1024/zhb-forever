package com.forever.zhb.timer.task;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTaskTest01 extends TimerTask {
	
	protected Logger logger = LoggerFactory.getLogger(TimerTaskTest01.class);

	@Override
	public void run() {
		logger.info("-----开始执行TimerTaskTest01定时任务-----");
		logger.info(Thread.currentThread().getName());
		logger.info("-----结束执行TimerTaskTest01定时任务-----");
	}
/*	@Override
	public void doRun() throws Exception {
	    logger.info("-----开始执行TimerTaskTest01定时任务-----");
	    logger.info(Thread.currentThread().getName());
	    logger.info("-----结束执行TimerTaskTest01定时任务-----");
	}
*/
}
