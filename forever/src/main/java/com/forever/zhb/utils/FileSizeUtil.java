package com.forever.zhb.utils;

import java.math.BigDecimal;

public class FileSizeUtil {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    public static String fileSizeFilter(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + " 字节";
        }
        if (fileSize >= 1024 && fileSize < 1000 * 1024) {
            return div(fileSize, 1024, DEF_DIV_SCALE) + " K";
        }
        if (fileSize >= 1000 * 1024 && fileSize < 1000 * 1024 * 1024) {
            return div(fileSize, 1024 * 1024, DEF_DIV_SCALE) + " M";
        }
        return fileSize + " 字节";
    }

    /**
     * 相对精确的除法运算 
     * 精度为 scale, 采用四舍五入
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
