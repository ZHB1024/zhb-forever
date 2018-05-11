package com.forever.zhb.utils;

public class PersonalInfoUtil {
	
	public static String getXb(String sfzh) {
        String xb = null;
        if (!ValidatorUtil.isNull(sfzh)) {
            if (sfzh.length() == 15) {
                String aa = sfzh.substring(14);
                if (Integer.parseInt(aa) % 2 == 0) {
                    xb = "女";
                }else{
                   xb = "男";
                }
            } else if (sfzh.length() == 18) {
                String bb = sfzh.substring(16,17);
                if (Integer.parseInt(bb) % 2 == 0) {
                    xb = "女";
                }else{
                   xb = "男";
                }
            }
        }
        return xb;
    }

}
