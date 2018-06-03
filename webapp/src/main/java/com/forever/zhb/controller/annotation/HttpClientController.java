package com.forever.zhb.controller.annotation;

import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/httpClientController")
public class HttpClientController {
	
	private Logger logger = LoggerFactory.getLogger(HttpClientController.class);
	
	@Resource(name="foreverManager")
    private IForeverManager foreverManager;
	
	@RequestMapping("/toHttpClient")
    public String toHttpClient(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("active7", true);
		request.setAttribute("url", "http://localhost:8080/httpClientController/contentTest");
        request.setAttribute("keys", "name=zhanghuibin;userid=123456");
        return "htgl.httpClient.index";
    }
	
	@RequestMapping("/contentTest")
	public void contentTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String groupId = request.getParameter("groupId");
		String decGroupId = AESUtil.decrypt(groupId, AESUtil.findKeyById(""));
        JSONObject jo = new JSONObject();
		
		List<String> students = new ArrayList<String>();
		students.add("张会彬");
		students.add(decGroupId);
		jo.put("students", students);
		
		String encryRes = AESUtil.encrypt(jo.toString(), AESUtil.findKeyById(""));
		
        response.setHeader("Content-Type", "application/json; charset=utf-8");// 中文显示
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.append(encryRes);
        pw.close();
    }
	
	/** 
     * post方式提交表单
     */  
	@RequestMapping("/httpClient1")
    public String httpClient1(HttpServletRequest request,HttpServletResponse response) {  
		request.setAttribute("active7", true);
		String url = request.getParameter("url");
		String keys = request.getParameter("keys");
		if (StringUtils.isBlank(url)) {
			request.setAttribute("errorMsg", "请输入地址");
			return "htgl.httpClient.index";
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
		return "htgl.httpClient.index";
    }  
  
    /** 
     * 发送 post请求,AES加密，json
     * @throws Exception 
     */  
	@RequestMapping("/httpClient2")
    public String httpClient2(HttpServletRequest request,HttpServletResponse response) throws Exception {  
		request.setAttribute("active7", true);
		String url = request.getParameter("url");
		String keys = request.getParameter("keys");
		if (StringUtils.isBlank(url)) {
			request.setAttribute("errorMsg", "请输入地址");
			return "htgl.httpClient.index";
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
                    JSONObject jsonRes = new JSONObject(encryResult);
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
		return "htgl.httpClient.index";
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
