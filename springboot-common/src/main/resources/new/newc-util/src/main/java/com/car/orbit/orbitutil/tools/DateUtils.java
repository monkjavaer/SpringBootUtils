package com.car.orbit.orbitutil.tools;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * CreateDate：2018/5/8<br/>
 * Author：monkjavaer<br/>
 * Description: tools related to java.utils.Date
 **/
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /** standard String format of date */
    private static final String DATE_STR_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_ZONE_ID = "Asia/Shanghai";

    /**
     * transfer Date to standard String format
     *
     * @param date date param
     * @return date in standard String format
     */
    public static String format(Date date) {
        String formatString = "";
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_STR_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
            formatString = format.format(date);
        }
        return formatString;
    }


    /**
     * 获得当前时间，时区为手动设置的时区
     *
     * @return 当前时间
     */
    public static Date newDate() {
        TimeZone time = TimeZone.getTimeZone(TIME_ZONE_ID);
        TimeZone.setDefault(time);
        return new Date();
    }

    /**
     * transfer standard Date String to Date
     *
     * @param dateStr date in standard String format
     * @return transferred Date
     */
    public static Date getDate(String dateStr) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, DATE_STR_FORMAT);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    /**
     * modify given days to origin date and get the response
     *
     * @param date origin date in standard String format
     * @param modifyDay modify days, positive means add, negative means minus
     * @return response in format "yyyy-MM-dd"
     */
    public static String modify(String date, int modifyDay) {
        Date srcDate = getDate(date);
        Date destDate = new Date(srcDate.getTime() + (long) modifyDay * 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        return format.format(destDate);
    }

    /**
     * get the start moment of the given day
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getStartOfDay(String dateStr) {
        String date = dateStr.split(" ")[0];
        return getDate(date + " 00:00:00");
    }

    /**
     * get the end moment of the given day
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getEndOfDay(String dateStr) {
        String date = dateStr.split(" ")[0];
        return getDate(date + " 23:59:59");
    }

    /**
     * get the start day of the week(Monday) that the given date belongs
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getFirstDayOfWeek(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateStr);
            cal.setTime(time);
        } catch (ParseException e) {
            // ignore
        }
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * get the end day of the week(Sunday) that the given date belongs
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getLastDayOfWeek(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateStr);
            cal.setTime(time);
        } catch (ParseException e) {
            // ignore
        }
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + 6);
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * get the first day of the month that the given date belongs
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getFirstDayOfMonth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateStr);
            cal.setTime(time);
        } catch (ParseException e) {
            // ignore
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * get the last day of the month that the given date belongs
     *
     * @param dateStr date in standard String format
     * @return response Date
     */
    public static Date getLastDayOfMonth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateStr);
            cal.setTime(time);
        } catch (ParseException e) {
            // ignore
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * compare two date and get their interval seconds
     *
     * @param startDate start date in standard String format
     * @param endDate end date in standard String format
     * @return interval seconds
     */
    public static Long getTwoDateDistance(String startDate, String endDate) {
        Long distance = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_STR_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
            Calendar calendar = Calendar.getInstance();
            Date start = sdf.parse(startDate);
            calendar.setTime(start);
            Long sTime = calendar.getTimeInMillis();
            Date end = sdf.parse(endDate);
            calendar.setTime(end);
            Long eTime = calendar.getTimeInMillis();
            distance = (eTime - sTime) / (1000);
        } catch (Exception e) {
            // ignore
        }
        return distance;
    }

    /**
     * 获取日期的年
     *
     * @param date
     * @return
     */
    public static int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期所属周数
     *
     * @param date
     * @return
     */
    public static int getWeekInYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置周一为一周的第一天
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int mouth = cal.get(Calendar.MONTH);
        if (mouth >= 11 && week <= 1) {
            week += 52;
        }
        return week;
    }

    /**
     * 加1周
     *
     * @param dt
     * @return
     */
    public static Date addOneWeek(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }


    /**
     * 获取"yyyy-MM-dd HH:mm:ss"前、后amount分钟时间
     *
     * @param dateStr "yyyy-MM-dd HH:mm:ss"
     * @param amount 正数表示该日期后n分钟，负数表示该日期的前n分钟
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getBeforeOrAfterMinute(String dateStr, int amount) {
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(dateStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n分钟，负数表示该日期的前n分钟
        calendar.add(Calendar.MINUTE, amount);
        Date date1 = calendar.getTime();
        return sdf.format(date1);
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式转yyyyMMddHHmmss
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static String getyyyyMMddHHmmss(String dateStr) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf1.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        Date date = sdf1.parse(dateStr);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf2.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ID));
        return sdf2.format(date);
    }

    /**
     * 计算两个日期之间间隔的小时数
     *
     * @param s1
     * @param s2
     * @return
     */
    public static double getHoursBetweenTwoDate(String s1, String s2) {
        Date startDate = DateUtils.getDate(s1);
        Date endDate = DateUtils.getDate(s2);
        long diff = endDate.getTime() - startDate.getTime();
        return ((double) diff) / 1000 / 60 / 60.0;
    }

    /**
     * 加减天数
     *
     * @param date
     * @return
     */
    public static Date addDays(String date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getDate(date));
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 加减月数
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(String date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getDate(date));
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 根据传入的日期和时间字符串，获得整数小时
     *
     * @param dateStr 日期字符串
     * @return 整数小时
     */
    public static Integer getHour(String dateStr) {
        String time = dateStr.split(" ")[1];
        return Integer.parseInt(time.split(":")[0]);
    }

    /**
     * 计算间隔秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getIntervalSeconds(String date1, String date2) {
        Date dt1 = getDate(date1);
        Date dt2 = getDate(date2);

        long seconds = (dt1.getTime() - dt2.getTime()) / 1000;
        if (seconds < 0) {
            seconds = 0 - seconds;
        }
        return seconds;
    }
}
