package com.forever.zhb.timer.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.forever.zhb.timer.task.ScheduledExecutorRunnableTask;

public class ScheduledExecutorTimerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(new ScheduledExecutorRunnableTask(), 0, 5, TimeUnit.MINUTES);
		Executors.newCachedThreadPool();

	}

	@Override
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
	}

}
