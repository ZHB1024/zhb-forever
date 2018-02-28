package com.forever.zhb.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String getPropertyPath(String propertyPath, String defaultPropertyFileName) {
		if ((StringUtils.isBlank(propertyPath)) || (StringUtils.isBlank(defaultPropertyFileName))) {
			return null;
		}

		String propertyFilePath = System.getProperties().getProperty(propertyPath);
		if (StringUtils.isBlank(propertyFilePath)) {
			propertyFilePath = System.getenv(propertyPath);
		}

		StringBuilder pathBuilder = new StringBuilder(propertyFilePath);
		if ((StringUtils.isNotBlank(defaultPropertyFileName))
				&& (!(StringUtils.endsWithIgnoreCase(propertyFilePath, defaultPropertyFileName)))) {
			pathBuilder.append(File.separatorChar).append(defaultPropertyFileName);
		}
		return pathBuilder.toString();
	}
	
	/**
     * 获取指定字符串出现的次数
     * 
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
	public static  int countNumber(String srcText, String findText){
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

}
