package com.forever.zhb.timer.thread;

import java.util.Timer;

import com.forever.zhb.timer.task.TimerTestTask;

public class TimerTestThread extends Thread {
	
	private Timer timer;
	private long period;
	
	public TimerTestThread(int minute) {
		timer = new Timer();
		this.period = 60 * minute * 1000;
	}
	
	@Override
    public void run(){
        TimerTestTask task=new TimerTestTask();
        timer.schedule(task, 0, period);
    }

}
