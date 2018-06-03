package com.forever.zhb.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExecutorRunnableTask01 implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(ScheduledExecutorRunnableTask01.class);

	@Override
	public void run() {
		logger.info("------begin ScheduledExecutorRunnableTask01 Runnable -------");
		logger.info(Thread.currentThread().getName());
		logger.info("------end ScheduledExecutorRunnableTask01 Runnable -------");
	}

}
