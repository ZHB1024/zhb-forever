package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
	    /*String url = JsoupUtil.getUrl();
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
	    es.shutdown();*/
	    String url = "https://movie.douban.com/explore#!type=movie&tag=热门&sort=rank&page_limit=20&page_start=0";
		//Document doc = JsoupUtil.getDocumentByUrl(url);

        /**HtmlUnit请求web页面*/
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page = null;
        try {
            page = wc.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        String pageXml = page.asXml(); //以xml的形式获取响应文本

        /**jsoup解析文档*/
        Document doc = Jsoup.parse(pageXml);
        System.out.println(doc);

		return "htgl.spider.index";
	}
	
}
