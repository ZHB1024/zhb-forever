package com.forever.zhb.controller.annotation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.SealedObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.AESUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/htgl/spiderController")
public class SpiderController {
	
	private Logger logger = LoggerFactory.getLogger(SpiderController.class);
	
	@Resource(name="foreverManager")
    private IForeverManager foreverManager;
	
	@RequestMapping("/toSpider")
    public String toSpider(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("active7", true);
		request.setAttribute("url", "http://localhost:8080/testController/contentTest");
        request.setAttribute("keys", "name=zhanghuibin;userid=123456");
        return "htgl.spider.index";
    }
	
	/** 
     * post方式提交表单
     */  
	@RequestMapping("/spider1")
    public String postForm(HttpServletRequest request,HttpServletResponse response) {  
		request.setAttribute("active7", true);
		String url = request.getParameter("url");
		String keys = request.getParameter("keys");
		if (StringUtils.isBlank(url)) {
			request.setAttribute("errorMsg", "请输入地址");
			return "htgl.spider.index";
		}
		String content = "";
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("groupId", keys));  
        formparams.add(new BasicNameValuePair("name", "root"));  
        formparams.add(new BasicNameValuePair("password", "123456"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            logger.info("executing request " + httppost.getURI());  
            CloseableHttpResponse chr = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = chr.getEntity();  
                if (entity != null) {  
                    logger.info("--------------------------------------");  
                    content = EntityUtils.toString(entity, "UTF-8");
                    logger.info("Response content: " + content);  
                    logger.info("--------------------------------------");  
                }  
            } finally {  
            	chr.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        request.setAttribute("content", content);
        request.setAttribute("url", url);
        request.setAttribute("keys", keys);
		return "htgl.spider.index";
    }  
  
    /** 
     * 发送 post请求,AES加密，json
     * @throws Exception 
     */  
	@RequestMapping("/spider2")
    public String post(HttpServletRequest request,HttpServletResponse response) throws Exception {  
		request.setAttribute("active7", true);
		String url = request.getParameter("url");
		String keys = request.getParameter("keys");
		if (StringUtils.isBlank(url)) {
			request.setAttribute("errorMsg", "请输入地址");
			return "htgl.spider.index";
		}
		String content = "";
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("groupId", AESUtil.encrypt(keys, AESUtil.findKeyById(""))));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            logger.info("executing request " + httppost.getURI());  
            CloseableHttpResponse chr = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = chr.getEntity();  
                if (entity != null) {  
                    logger.info("--------------------------------------");  
                    System.out.println(entity.getContentLength());
                    System.out.println(entity.getContentType());
                    InputStream in =  entity.getContent();
                    content = readResponse(in);
                    /*JSONObject jsonObject = JSONObject.fromObject(content);
                    Object o = jsonObject.get("students");
                    JSONObject js = (JSONObject)jsonObject.get("students");
                    System.out.println(js.get("12345"));*/
                    String encryResult = AESUtil.decrypt(content, AESUtil.findKeyById(""));
                    JSONObject jsonRes = JSONObject.fromObject(encryResult);
                    if (null != jsonRes) {
                    	Object object = jsonRes.get("students");
                        if (null != object) {
                        	for (String name : (List<String>)object) {
    							System.out.println(name);
    						}
    					}
					}
                    
                    System.out.println(content);
                    logger.info("--------------------------------------");  
                }  
            } finally {  
            	chr.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        request.setAttribute("content", content);
        request.setAttribute("url", url);
        request.setAttribute("keys", keys);
		return "htgl.spider.index";
    }  
  
  	private String readResponse(InputStream in) {  
  	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
  	    String line = "";  
  	    String temp = null;
  	    try {
			while ((temp = reader.readLine()) != null) {
				line += temp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
  	    return line;
  	}  

}
