package com.forever.zhb.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageUtil {
	
	private Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	public static final String fileName = "/message/message.properties";
	public static Properties properties = new Properties();
	
	static{
		try {
			properties.load(MessageUtil.class.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getMessage(String key,Object[] args){
		return MessageFormat.format(properties.getProperty(key), args);
	}
	
	public static String getMessage(String key){
		return properties.getProperty(key);
	}

}
