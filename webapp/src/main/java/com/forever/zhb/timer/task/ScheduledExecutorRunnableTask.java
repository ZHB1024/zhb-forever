package com.forever.zhb.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExecutorRunnableTask implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(ScheduledExecutorRunnableTask.class);

	@Override
	public void run() {
		logger.info("------this ScheduledExecutor Runnable -------");
	}

}
