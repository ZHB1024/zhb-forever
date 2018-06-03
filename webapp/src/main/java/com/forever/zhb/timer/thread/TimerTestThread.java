package com.forever.zhb.timer.thread;

import java.util.Timer;

import com.forever.zhb.timer.task.TimerTaskTest01;
import com.forever.zhb.timer.task.TimerTaskTest02;
import com.forever.zhb.timer.task.TimerTaskTest03;

public class TimerTestThread extends Thread {
	
	private Timer timer;
	private long period;
	
	public TimerTestThread(int minute) {
		timer = new Timer();
		this.period =  minute * 1000;
	}
	
	@Override
    public void run(){
        TimerTaskTest01 task01=new TimerTaskTest01();
        TimerTaskTest02 task02=new TimerTaskTest02();
        TimerTaskTest03 task03=new TimerTaskTest03();
        timer.schedule(task01, 0, period);
        timer.schedule(task02, 0, period);
        timer.schedule(task03, 0, period);
    }

}
