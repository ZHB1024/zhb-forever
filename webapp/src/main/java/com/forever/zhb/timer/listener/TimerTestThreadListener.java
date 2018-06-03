package com.forever.zhb.timer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.forever.zhb.timer.thread.TimerTestThread;

public class TimerTestThreadListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent arg0) {
		int runTimerInterval=Integer.parseInt(arg0.getServletContext().getInitParameter("runTimerInterval"));
        TimerTestThread timerThread =new TimerTestThread(runTimerInterval);
        timerThread.start();
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
