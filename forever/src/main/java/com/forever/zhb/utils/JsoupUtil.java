package com.forever.zhb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {

	private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);
	
	public static String IMAGE_BASE_SAVE_PATH ;
	
	static {
        String property = System.getenv("propertyPath");
        if (StringUtils.isNotBlank(property)) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(property);
                Properties properties = new Properties();
                properties.load(fis);
                IMAGE_BASE_SAVE_PATH = properties.getProperty("sys.url.download.save.base.path");
            }catch(Exception e) {
                IMAGE_BASE_SAVE_PATH = "E:\\jsoup-image";
                e.printStackTrace();
                logger.info("JsoupUtil load property fail ......");
            }
        }else {
            IMAGE_BASE_SAVE_PATH = "E:\\jsoup-image";
            logger.info("环境变量未配置propertyPath");
        }
        
    }
	
	public static String getBaseSavePath() {
	    return IMAGE_BASE_SAVE_PATH;
	}

	public static Document getDocumentByUrl(String url) {
		if (StringUtil.isBlank(url)) {
			return null;
		}
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("init document fail");
		}
		return document;
	}
	
	public static String getUrl() {
	    String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.download.picture.url");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
	    return null;
	}
	
	public static String getTotalPage() {
	    String propertyPath = System.getenv("propertyPath");
	    if (null == propertyPath) {
	        logger.info("环境变量未配置propertyPath");
	    }else{
	        FileInputStream in = null;
	        try {
	            in = new FileInputStream(propertyPath);
	            Properties properties = new Properties();
	            properties.load(in);
	            return properties.getProperty("sys.download.picture.total.page");
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("JsoupUtil load property fail.....");
	        }
	    }
	    return null;
	}
	public static String getTotalThread() {
	    String propertyPath = System.getenv("propertyPath");
	    if (null == propertyPath) {
	        logger.info("环境变量未配置propertyPath");
	    }else{
	        FileInputStream in = null;
	        try {
	            in = new FileInputStream(propertyPath);
	            Properties properties = new Properties();
	            properties.load(in);
	            return properties.getProperty("sys.download.picture.total.thread");
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("JsoupUtil load property fail.....");
	        }
	    }
	    return null;
	}
	
	public static String getPersonalizedSavePath() {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.url.download.save.personalized.path");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
        return null;
    }


}
