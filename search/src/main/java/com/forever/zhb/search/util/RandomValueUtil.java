package com.forever.zhb.search.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class RandomValueUtil {
	
	private static Random random = new Random();
	
	private static final String[] names = {"张三","李四","王五","麻子","习近平"
												,"毛泽东","周恩来","邓小平","江泽民","朱镕基"
												,"彭丽媛","李开复","雷军","小米","apple"
												,"任正非","华为","中兴","马云","马化腾"};
	private static final String[] sexs = {"男","女"};
	
	
	public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = StringUtils.replace(uuid, "-", "");
        return uuid;
    }

	
	public static String randomName(int i){
		int index = random.nextInt(19);
		return names[index] + i;
	}
	
	public static int randomAge(){
		return random.nextInt(130);
	}
	
	public static String randomSex(){
		int index = random.nextInt(1);
		return sexs[index];
	}

}
