package com.forever.zhb.utils;

import java.io.File;

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

}
