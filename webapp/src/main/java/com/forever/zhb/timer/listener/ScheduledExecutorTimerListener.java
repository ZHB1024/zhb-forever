package com.forever.zhb.timer.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.forever.zhb.timer.task.ScheduledExecutorRunnableTask01;
import com.forever.zhb.timer.task.ScheduledExecutorRunnableTask02;
import com.forever.zhb.timer.task.ScheduledExecutorRunnableTask03;

public class ScheduledExecutorTimerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
	    int runScheduledExecutorInterval=Integer.parseInt(paramServletContextEvent.getServletContext().getInitParameter("runScheduledExecutorInterval"));
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		ScheduledExecutorRunnableTask01 task01 = new ScheduledExecutorRunnableTask01();
		ScheduledExecutorRunnableTask02 task02 = new ScheduledExecutorRunnableTask02();
		ScheduledExecutorRunnableTask03 task03 = new ScheduledExecutorRunnableTask03();
		scheduledExecutorService.scheduleAtFixedRate(task01, 0, runScheduledExecutorInterval, TimeUnit.SECONDS);
		scheduledExecutorService.scheduleAtFixedRate(task02, 0, runScheduledExecutorInterval, TimeUnit.SECONDS);
		scheduledExecutorService.scheduleAtFixedRate(task03, 0, runScheduledExecutorInterval, TimeUnit.SECONDS);
		
		//程序无关
		/*Executors.newFixedThreadPool(2);
		Executors.newSingleThreadExecutor();
		Executors.newCachedThreadPool();*/

	}

	@Override
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
	}

}
