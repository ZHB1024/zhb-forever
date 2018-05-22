package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.thread.Message;
import com.forever.zhb.thread.MessageLock;
import com.forever.zhb.thread.threads.HandleRunnable;
import com.forever.zhb.thread.threads.MailThread;
import com.forever.zhb.thread.threads.SmsThread;
import com.forever.zhb.thread.threads.SubmitCallable;
import com.forever.zhb.utils.MessageUtil;
import com.forever.zhb.utils.StringUtil;

@Controller
@RequestMapping("/threadController")
public class ThreadController extends BasicController {
    
    private Logger logger = LoggerFactory.getLogger(ThreadController.class);
    
    
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = MessageUtil.getMessage("0001", new Object[] { "张会彬" });
        logger.info(message);
        return "test.body.index";
    }
    
 
//------------------------------------------------------------------------------------------------------------------

    
  //----------------extends Thread-----------------------------------------------
    public static void extendsThread(){
    	Message message = new Message("message");
        MessageLock messageLock = new MessageLock("messageLock");
        
        SmsThread smsThread = new SmsThread(message,messageLock);
        MailThread mailThread = new MailThread(message,messageLock);
        
        smsThread.start();
        mailThread.start();
    }
    
  //----------------implements Runnable----------------------------------------------- 
    public static void implementsRunnable(){
    	HandleRunnable handleRunnable = new HandleRunnable("runnable111");
        Thread thread1 = new Thread(handleRunnable);
        Thread thread2 = new Thread(handleRunnable);
        thread1.start();
        thread2.start();
    }
    
    
  //----------------ExecutorService Future---------------------------------------   
    public static void executorService() throws InterruptedException, ExecutionException{
    	int taskSize = 5;
        List<Future> results = new ArrayList<>();
        
        ExecutorService executorService = Executors.newFixedThreadPool(taskSize);
        for (int i = 0; i < taskSize; i++) {  
            Callable c = new SubmitCallable(i + " ");  
            // 执行任务并获取Future对象  
            Future f = executorService.submit(c);  
            results.add(f);  
        }  
        
        // 关闭线程池  
        executorService.shutdown();  
  
        // 获取所有并发任务的运行结果  
        for (Future f : results) {  
            // 从Future对象上获取任务的返回值，并输出到控制台  
            System.out.println(">>>" + f.get().toString());  
        }  
    }
    
    
  //----------------------------synchronized-----------阻塞------------------
    public static void synchronizedTest(){
    	Message message = new Message("test");
    	Thread thread01 = new Thread(new Runnable() {
			@Override
			public void run() {
				message.smsSender();
			}
		});
    	
    	Thread thread02 = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			message.mailSender();
    		}
    	});
    	
    	thread01.start();
    	thread02.start();
    }
    
  //--------------lock-----------------非阻塞---------------------------
    public static void lockTest(){
    	MessageLock messageLock = new MessageLock("lockTest");
    	Thread thread01 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					messageLock.smsSender();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
    	
    	Thread thread02 = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
					messageLock.mailSender();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	});
    	
    	thread01.start();
    	thread02.start();
    	/*try {
			Thread.currentThread().sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	
    }
    
  //----------------countDownLatch--------------------------------------- 
    public static void countDownLatch(){
    	CountDownLatch countDownLatch = new CountDownLatch(1);
    	Lock lock = new ReentrantLock();
    	AtomicInteger num = new AtomicInteger(0);
    	for(int i = 0 ; i < 3 ; i++){
    		Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println( Thread.currentThread().getName() + "进入了run");  
						countDownLatch.await();//等待countDownLatch为0
						System.out.println( Thread.currentThread().getName() + "开始执行,time: " + System.currentTimeMillis());
						if(lock.tryLock()){
							try{
								if (num.get() == 0) {
									System.out.println( Thread.currentThread().getName() + "进入" );
									int temp =num.incrementAndGet();
									if (temp == 1) {
										System.out.println( Thread.currentThread().getName() + ":" + temp);  
									}
								}
							}finally {
								lock.unlock();
							}
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
    		thread.start();
    	}
    	try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	countDownLatch.countDown();//将countDownLatch减为0，上面3个线程同时开始执行
    	try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	System.out.println("num:" + num);
    	
    }
    
    //-----------------CyclicBarrier --------------------------------
    public static void cyclicBarrier(){
    	CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    	for(int i=0; i<3; i++){
    		Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + "进入,time:" + System.currentTimeMillis() + " 等待 .......");
						cyclicBarrier.await();//等待线程个数等于 CyclicBarrier初始化的3
						System.out.println(Thread.currentThread().getName() + ",time:" + System.currentTimeMillis());
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
    		thread.start();
    	}
    	try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    //-------------semaphore---------------------------------------
    public static void semaphore(){
    	Semaphore semaphore = new Semaphore(2);
    	for(int i=0; i<3; i++){
    		Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					boolean flag = false;
					try {
						semaphore.acquire();//获取信号量，没有则等待
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						System.out.println(Thread.currentThread().getName() + "进入,time:" + System.currentTimeMillis() );
						flag = true;
					} finally {
						System.out.println(Thread.currentThread().getName() + "离开,time:" + System.currentTimeMillis());
						if (flag) {
							semaphore.release();//释放信号量
						}
					}
					
				}
			});
    		thread.start();
    	}
    	try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    //------------------------blockingQueue-------------------
    public static void blockingQueue(){
    	ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(8);
    	Thread thread01 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("向队列里添加------");
				for(int i=0; i< 10;i++){
					while (!queue.offer(i+"num")) {
						System.out.println("队列已满------等待消费-------");
						try {
							Thread.currentThread().sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(i+"num," + "添加成功------");
				}
			}
		});
    	Thread thread02 = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			System.out.println("从队列里取------");
    			for(int i=0; i< 10;i++){
    				String value = null;
    				while(StringUtil.isBlank(value=queue.poll())){
    					System.out.println("队列已空------等待添加-------------");
    					try {
							Thread.currentThread().sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    				}
    				System.out.println("取出的值为： " + value);
    			}
    		}
    	});
    	
    	thread02.start();
    	thread01.start();
    	
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	//extendsThread();
    	//implementsRunnable();
    	//executorService();
    	//countDownLatch();
    	//cyclicBarrier();
    	//cyclicBarrier();
    	//semaphore();
    	//blockingQueue();
    	//synchronizedTest();
    	lockTest();
        
    }

}
