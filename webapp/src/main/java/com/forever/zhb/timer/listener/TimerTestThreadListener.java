package com.forever.zhb.timer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.forever.zhb.timer.thread.TimerTestThread;

public class TimerTestThreadListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent arg0) {
		int runTimeInterval=Integer.parseInt(arg0.getServletContext().getInitParameter("runTimeInterval"));
        TimerTestThread timerThread =new TimerTestThread(runTimeInterval);
        timerThread.start();
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
