package com.forever.zhb.search.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class UUIDUtil {
	
	public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = StringUtils.replace(uuid, "-", "");
        return uuid;
    }

}
