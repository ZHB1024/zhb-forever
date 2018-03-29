package com.forever.zhb.controller.annotation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.thread.threads.HandleRunnable;
import com.forever.zhb.utils.MessageUtil;

@Controller
@RequestMapping("/threadPoolController")
public class ThreadPoolController {

	private Logger logger = LoggerFactory.getLogger(ThreadPoolController.class);

	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = MessageUtil.getMessage("0001", new Object[] { "张会彬" });
		logger.info(message);
		return "test.body.index";
	}

	// execute() 无返回值
	// 表示往线程池添加线程，有可能会立即运行，也有可能不会。无法预知线程何时开始，何时线束。

	// submit() 有返回值
	// 适应于生产者-消费者模式，通过和Future结合一起使用，可以起到如果线程没有返回结果，就阻塞当前线程等待线程 池结果返回。

	// shutdown()
	// 通常放在execute后面。如果调用 了这个方法，一方面，表明当前线程池已不再接收新添加的线程，新添加的线程会被拒绝执行。
	// 另一方面，表明当所有线程执行完毕时，回收线程池的资源。注意，它不会马上关闭线程池！

	// shutdownNow()
	// 不管当前有没有线程在执行，马上关闭线程池！这个方法要小心使用，要不可能会引起系统数据异常！

	/*
	 * 总结：
	 * ThreadPoolExecutor中，包含了一个任务缓存队列和若干个执行线程，任务缓存队列是一个大小固定的缓冲区队列，用来缓存待执行的任务，
	 * 执行线程用来处理待执行的任务。每个待执行的任务，都必须实现Runnable接口，执行线程调用其run()方法，完成相应任务。
	 * ThreadPoolExecutor对象初始化时，不创建任何执行线程，当有新任务进来时，才会创建执行线程。
	 * 构造ThreadPoolExecutor对象时，需要配置该对象的核心线程池大小和最大线程池大小：
	 * 当目前执行线程的总数小于核心线程大小时，所有新加入的任务，都在新线程中处理
	 * 当目前执行线程的总数大于或等于核心线程时，所有新加入的任务，都放入任务缓存队列中
	 * 当目前执行线程的总数大于或等于核心线程，并且缓存队列已满，同时此时线程总数小于线程池的最大大小，那么创建新线程，加入线程池中，协助处理新的任务。
	 * 当所有线程都在执行，线程池大小已经达到上限，并且缓存队列已满时，就rejectHandler拒绝新的任务
	 */

	public static void main(String[] args) {
		System.out.println("Main: Starting at: " + new Date());

		// 单个线程
		// singleThreadExecutor();

		// 缓冲池
		// cacaheThreadPool();

		// 固定数目的线程池
		// fixedThreadPool();

		// 计划线程池,有几种方式，需进行调整
		//scheduledThreadPool();

		System.out.println("Main: Finished all threads at" + new Date());
	}

	// 单个线程
	private static void singleThreadExecutor() {
		// 创建只能运行一条线程的线程池。它能保证线程的先后顺序执行，并且能保证一条线程执行完成后才开启另一条新的线程
		ExecutorService exec = Executors.newSingleThreadExecutor(); // 等价于
																	// Executors.newFixedThreadPool(1);
		for (int i = 0; i < 10; i++) {
			exec.execute(new HandleRunnable(String.valueOf(i)));
		}
		exec.shutdown(); // 执行到此处并不会马上关闭线程池
	}

	// 缓冲线程池
	private static void cacaheThreadPool() {
		// 1、主线程的执行与线程池里的线程分开，有可能主线程结束了，但是线程池还在运行
		// 2、放入线程池的线程并不一定会按其放入的先后而顺序执行

		// 创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
		ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			exec.execute(new HandleRunnable(String.valueOf(i)));
		}

		// 执行到此处并不会马上关闭线程池,但之后不能再往线程池中加线程，否则会报错
		exec.shutdown();
	}

	// 固定数目的线程池
	private static void fixedThreadPool() {
		// 固定大小的线程池，大小为5.也就说同一时刻最多只有5个线程能运行。并且线程执行完成后就从线程池中移出。
		// 它也不能保证放入的线程能按顺序执行。这要看在等待运行的线程的竞争状态了。

		ExecutorService exec = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			exec.execute(new HandleRunnable(String.valueOf(i)));
		}
		exec.shutdown(); // 执行到此处并不会马上关闭线程池
	}

	// 计划线程池
	private static void scheduledThreadPool() {
		// 计划线程池类，它能设置线程执行的先后间隔及执行时间等，功能比上面的三个强大了一些
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10); // 创建大小为10的线程池
		for (int i = 0; i < 10; i++) {
			// 延迟10秒执行
			executor.schedule(new HandleRunnable(String.valueOf(i)), 10, TimeUnit.SECONDS);

			// 初始化延迟0ms开始执行，每隔2000ms重新执行一次任务 ,如果上次的线程还没有执行完成，那么会阻塞下一个线程的执行
			executor.scheduleAtFixedRate(new HandleRunnable(String.valueOf(i)), 0, 2000, TimeUnit.MILLISECONDS);
			
			// 每天晚上9点执行一次,每天定时安排任务进行执行
			long oneDay = 24 * 60 * 60 * 1000;
			long initDelay = getTimeMillis("21:00:00") - System.currentTimeMillis();
			initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
			executor.scheduleAtFixedRate(new HandleRunnable(String.valueOf(i)), initDelay, oneDay,
					TimeUnit.MILLISECONDS);

			// 以固定延迟时间进行执行,本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
			executor.scheduleWithFixedDelay(new HandleRunnable(String.valueOf(i)), 0, 2000, TimeUnit.MILLISECONDS);
		}
		executor.shutdown(); // 执行到此处并不会马上关闭线程池
	}

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */
	private static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
