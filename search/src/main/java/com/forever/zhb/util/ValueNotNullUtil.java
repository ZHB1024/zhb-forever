package com.forever.zhb.util;

import org.apache.commons.lang3.StringUtils;

public class ValueNotNullUtil {
	
	public static String valueNotNull(String value){
		if (StringUtils.isBlank(value)) {
			return "--";
		}
		
		return value;
	}

}
