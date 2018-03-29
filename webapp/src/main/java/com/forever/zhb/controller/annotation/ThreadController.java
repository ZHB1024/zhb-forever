package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    
    
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {

//----------------extends Thread-----------------------------------------------    	
        Message message = new Message("message");
        MessageLock messageLock = new MessageLock("messageLock");
        
        SmsThread smsThread = new SmsThread(message,messageLock);
        MailThread mailThread = new MailThread(message,messageLock);
        
        smsThread.start();
        mailThread.start();
        
//----------------implements Runnable----------------------------------------------- 
        HandleRunnable handleRunnable = new HandleRunnable("runnable111");
        Thread thread1 = new Thread(handleRunnable);
        Thread thread2 = new Thread(handleRunnable);
        thread1.start();
        thread2.start();
        
        
//----------------ExecutorService Future---------------------------------------
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

}
