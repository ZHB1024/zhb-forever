package com.forever.zhb.utils;

import java.util.Calendar;

public class DateUtil {
    
    private static long ONEDAYINMILLIS = 24*60*60*1000;// 一天的毫秒数
    public static final String TODAY = "今天";
    public static final String YESTERDAY = "昨天";
    public static final String THISWEEK = "本周";
    public static final String LONGAGO = "更早";
    
    public static String tranDate(Calendar calendar) {
        String date = TimeUtil.getTime(calendar, "yyyyMMddHHmmss");
        // 今天
        String today = new TimeUtil().getTime("yyyyMMdd");
        // 昨天
        Calendar yesterdayC = Calendar.getInstance();
        yesterdayC.setTimeInMillis(yesterdayC.getTimeInMillis() - (1*ONEDAYINMILLIS));
        String yesterday = TimeUtil.getTime(yesterdayC, "yyyyMMdd");
        Calendar nowTime = Calendar.getInstance();
        int weekDay = nowTime.get(Calendar.DAY_OF_WEEK) -1;
        Calendar startDateC = Calendar.getInstance();
        Calendar endDateC = Calendar.getInstance();
        startDateC.setTimeInMillis(startDateC.getTimeInMillis() - (weekDay-1)*ONEDAYINMILLIS);
        String startDate = TimeUtil.getTime(startDateC, "yyyyMMdd");
        endDateC.setTimeInMillis(endDateC.getTimeInMillis() + ((7-weekDay)*ONEDAYINMILLIS));
        String endDate = TimeUtil.getTime(endDateC, "yyyyMMdd");
        if (include(date, today + "000000", today + "245959")) {
            return TODAY;
        } else if (include(date, yesterday + "000000", yesterday + "245959")) {
            return YESTERDAY;
        }  else if (include(date, startDate + "000000", endDate + "245959")) {
            return THISWEEK;
        } else {
            return LONGAGO;
        }
    }
    
    /**
     * 判断nowTime 是否位于 startTime 和 endTime 之间 参数是数值型 String 参数
     * 
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    private static boolean include(String nowTime, String startTime, String endTime) {
        try {
            long temp1 = Long.parseLong(nowTime);
            long temp2 = Long.parseLong(startTime);
            long temp3 = Long.parseLong(endTime);
            if (temp1 >= temp2 && temp1 <= temp3) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // YYYY-MM-DD HH24:MI:SS
    public static Calendar getTime(String time) {
        Calendar calendar = Calendar.getInstance();
        String year = time.substring(0,4);
        String month = time.substring(5,7);
        String day = time.substring(8,10);
        String hour = time.substring(11,13);
        String minute = time.substring(14,16);
        String second = time.substring(17,19);
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
        return calendar;
    }
    
    
}
