package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.thread.Message;
import com.forever.zhb.thread.MessageLock;
import com.forever.zhb.thread.threads.MailThread;
import com.forever.zhb.thread.threads.SmsThread;
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
    
    
    
    public static void main(String[] args) {
        
        Message message = new Message("message");
        MessageLock messageLock = new MessageLock("messageLock");
        
        SmsThread smsThread = new SmsThread(message,messageLock);
        MailThread mailThread = new MailThread(message,messageLock);
        
        smsThread.start();
        mailThread.start();
        
    }

}
