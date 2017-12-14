package com.forever.zhb.controller.annotation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.service.IForeverManager;

@Controller
@RequestMapping("/spiderController")
public class SpiderController {
	
	@Resource(name="foreverManager")
    private IForeverManager foreverManager;
	
	@RequestMapping("/toSpider")
    public String toSpider(HttpServletRequest request,HttpServletResponse response){
        return "htgl.spider.spider";
    }
	
	@RequestMapping("/spider")
	public String spider(HttpServletRequest request,HttpServletResponse response){
		String url = request.getParameter("url");
		if (StringUtils.isBlank(url)) {
			request.setAttribute("errorMsg", "请输入地址");
			return "htgl.spider.spider";
		}
		String content = "";
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = getMethod.getResponseBody();
				content = new String(responseBody);
			}else{
				content = "获取失败！" + getMethod.getStatusLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			getMethod.releaseConnection();
		}
		
		request.setAttribute("content", content);
		return "htgl.spider.spider";
	}

}
