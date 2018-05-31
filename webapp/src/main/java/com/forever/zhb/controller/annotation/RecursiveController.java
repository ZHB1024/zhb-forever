package com.forever.zhb.controller.annotation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.utils.MessageUtil;

@Controller
@RequestMapping("/recursiveController")
public class RecursiveController {
	
	private Logger logger = LoggerFactory.getLogger(RecursiveController.class);
    
    
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String message = MessageUtil.getMessage("0001", new Object[] { "张会彬" });
        logger.info(message);
        return "test.body.index";
    }
    
    public static void main(String[] args) {
    	long count = 1000000000;
    	compute(count);
    	//forkJoinRecursive();
    }
    
    public static void compute(long count){
    	long begin = System.currentTimeMillis();
    	long sum = 0;
    	for(int i=1;i<=count ;i++){
    		sum += i;
    	}
    	long end = System.currentTimeMillis();
    	long time = end - begin;
    	System.out.println(sum);
    	System.out.println(time/1000);
    }
    
    public static class CountTask extends RecursiveTask<Integer>{

        private static final int THREAD_HOLD = 4;

        private int start;
        private int end;

        public CountTask(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            //如果任务足够小就计算
            boolean canCompute = (end - start) <= THREAD_HOLD;
            if(canCompute){
                for(int i=start;i<=end;i++){
                    sum += i;
                }
            }else{
                int middle = (start + end) / 2;
                CountTask left = new CountTask(start,middle);
                CountTask right = new CountTask(middle+1,end);
                //执行子任务
                left.fork();
                right.fork();
                //获取子任务结果
                int lResult = left.join();
                int rResult = right.join();
                sum = lResult + rResult;
            }
            return sum;
        }

    }
    
    public static void forkJoinRecursive(){
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1,8);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
