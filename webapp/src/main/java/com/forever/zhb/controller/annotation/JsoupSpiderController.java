package com.forever.zhb.controller.annotation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.thread.jsoup.spider.JsoupSpiderRunnable;
import com.forever.zhb.utils.DateUtil;
import com.forever.zhb.utils.JsoupUtil;
import com.forever.zhb.utils.StringUtil;

@Controller
@RequestMapping("/htgl/jsoupSpiderController")
public class JsoupSpiderController extends BasicController {
	
	private Logger logger = LoggerFactory.getLogger(JsoupSpiderController.class);
	
	@RequestMapping(value="/toSpider",method=RequestMethod.GET)
	public String toSpider(HttpServletRequest request,HttpServletResponse response){
		
		return "htgl.spider.index";
	}

	@RequestMapping(value="/spider",method=RequestMethod.POST)
	public String spider(HttpServletRequest request,HttpServletResponse response){
		/*String url = request.getParameter("url");
		if (StringUtil.isBlank(url)) {
			return "htgl.spider.index";
		}*/
	    
	    String url = JsoupUtil.getUrl();
	    String basePath = JsoupUtil.getBaseSavePath();
	    String personalizedPath = JsoupUtil.getPersonalizedSavePath();
	    String targetSavePath = basePath + personalizedPath + DateUtil.TODAY_FORMAT;
	    
	    int totalPage = Integer.valueOf(JsoupUtil.getTotalPage());
	    int totalThread = Integer.valueOf(JsoupUtil.getTotalThread());
	    int perPage = totalPage/totalThread;
	    
	    ExecutorService es = Executors.newFixedThreadPool(totalThread);
	    int totalThreadIndex = totalThread-1;
	    for(int i=0 ;i < totalThread ;i++) {
	        if (i != totalThreadIndex) {
	            es.execute(new JsoupSpiderRunnable(url,targetSavePath,i,i*totalThread+1,i*totalThread+perPage));  
            }else {
                es.execute(new JsoupSpiderRunnable(url,targetSavePath,i,i*totalThread+1,totalPage));  
            }
	        
	    }
	    es.shutdown();
	    
	    /*if (StringUtil.isNotBlank(url)) {
	        Thread imageThread = new Thread(new JsoupSpiderRunnable(url));
	        imageThread.start();
        }*/
		
		return "htgl.spider.index";
	}
	
}
