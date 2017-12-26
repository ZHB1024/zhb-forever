package com.forever.zhb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

	private static final SimpleDateFormat FORMATTIME = new SimpleDateFormat("yyyyMMddHHmmss");

    private Calendar nowTime = Calendar.getInstance();

    private String getTime = FORMATTIME.format(this.nowTime.getTime());

    public String getYear() {
        return this.getTime.substring(0, 4);
    }

    public String getMonth() {
        return this.getTime.substring(4, 6);
    }

    public String getDay() {
        return this.getTime.substring(6, 8);
    }

    public String getHour() {
        return this.getTime.substring(8, 10);
    }

    public String getMinute() {
        return this.getTime.substring(10, 12);
    }

    public String getSecond() {
        return this.getTime.substring(12, 14);
    }

    public String getWeekDay() {
        int weekDay = this.nowTime.get(7);
        if (weekDay > 1) {
            return "" + (weekDay - 1);
        }
        return "7";
    }

    public String getDate() {
        return this.getTime.substring(0, 8);
    }

    public String getDateAll() {
        return this.getTime;
    }

    public String getTime() {
        return this.getTime;
    }

    public static String changeTime(String year, String month, String day) {
        try {
            if ((!(ValidatorUtil.isNumber(year)))||(!(ValidatorUtil.isNumber(month)))||(!(ValidatorUtil.isNumber(day)))) {
            	TimeUtil timeUtil = new TimeUtil();
                year = timeUtil.getYear();
                month = timeUtil.getMonth();
                day = timeUtil.getDay();
                return year + month + day;
			}

            if ((Integer.parseInt(year) < 2004) || (Integer.parseInt(month) < 1) || (Integer.parseInt(month) > 12)
                    || (Integer.parseInt(day) < 1) || (Integer.parseInt(day) > 31)) {
                TimeUtil timeUtil = new TimeUtil();
                year = timeUtil.getYear();
                month = timeUtil.getMonth();
                day = timeUtil.getDay();
                return year + month + day;
            }
            return year + toAddZero(Integer.parseInt(month)) + toAddZero(Integer.parseInt(day));
        } catch (IndexOutOfBoundsException e) {
            TimeUtil timeUtil = new TimeUtil();
            year = timeUtil.getYear();
            month = timeUtil.getMonth();
            day = timeUtil.getDay();
            return year + month + day;
        } catch (NumberFormatException ex) {
            TimeUtil timeUtil = new TimeUtil();
            year = timeUtil.getYear();
            month = timeUtil.getMonth();
            day = timeUtil.getDay();
        }
        return year + month + day;
    }

    public static String getTime(Calendar calendar, String format) {
        SimpleDateFormat formatTime = new SimpleDateFormat(format);
        return formatTime.format(calendar.getTime());
    }

    public static String getTime(String format) {
        Calendar nowTime = Calendar.getInstance();
        return getTime(nowTime, format);
    }

    public static String alertDate(String date, int discrepancy) {
        Date currentDay = new Date();
        SimpleDateFormat NowTimeformatter = new SimpleDateFormat("yyyyMMdd");
        String today = NowTimeformatter.format(currentDay);
        try {
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6, 8);
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month),
                    Integer.parseInt(day));

            gc.add(5, discrepancy);
            year = String.valueOf(gc.get(1));
            month = String.valueOf(gc.get(2));
            day = String.valueOf(gc.get(5));
            if (Integer.parseInt(month) <= 9)
                month = "0" + month;
            if (Integer.parseInt(day) <= 9)
                day = "0" + day;
            String deadline = year + month + day;
            if (deadline.compareTo(today) < 0) {
                date = "<span style=color:#FF0000>" + date + "</span>";
            }
            return date;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return date;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static int getTotalDays(int year, int month) {
        int totalDays = 30;
        if ((1 == month) || (3 == month) || (5 == month) || (7 == month) || (8 == month) || (10 == month)
                || (12 == month))
            totalDays = 31;
        else if (2 == month) {
            if (((0 == year % 4) && (0 != year % 100)) || (0 == year % 400))
                totalDays = 29;
            else {
                totalDays = 28;
            }
        }
        return totalDays;
    }

    public static boolean include(String nowTime, String startTime, String endTime) {
        try {
            int temp1 = Integer.parseInt(nowTime);
            int temp2 = Integer.parseInt(startTime);
            int temp3 = Integer.parseInt(endTime);

            return ((temp1 >= temp2) && (temp1 <= temp3));
        } catch (Exception e) {
        }

        return false;
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        return FORMATTIME.format(date);
    }

    public static String toQNC(int year) {
        if (year < 100) {
            year += 1900;
        }
        return toString(year);
    }

    public static String toAddZero(int number) {
        String str = "";
        str = toString(number);
        if (number < 10) {
            str = "0" + str;
        }
        return str;
    }

    public static String toString(int Integer_str) {
        return Integer.toString(Integer_str);
    }

    public static String showTime(long time) {
        long seconds = time / 1000L;
        long minutes = seconds / 60L;
        long hour = minutes / 60L;
        int minute = (int) (minutes % 60L);
        int second = (int) (seconds % 60L);
        int millisecond = (int) (time % 1000L);
        return toAddZero((int) hour) + ":" + toAddZero(minute) + ":" + toAddZero(second);
    }

    public static Calendar getCalendar(String date) {
        Calendar calendar = Calendar.getInstance();
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);
        return calendar;
    }
    
}
