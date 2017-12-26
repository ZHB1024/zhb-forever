package com.forever.zhb.timer.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTestTask extends BaseTask {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doRun() throws Exception {
		logger.info("-----执行定时任务-----");
	}

}
