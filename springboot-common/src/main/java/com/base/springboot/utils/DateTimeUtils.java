package com.base.springboot.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author monkjavaer
 * @date 2019/7/31 13:49
 */
public final class DateTimeUtils extends org.apache.commons.lang3.time.DateUtils{
    private static final long BASIC_TIME = getDate("1970-01-01 00:00:00").getTime();
    public  static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public  static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 将Date格式化为yyyy-MM-dd HH:mm:ss格式, 如果Date为空，返回"".
     * @param date
     * @return
     */
    public static String format(Date date) {
        String formatString = "";
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
            formatString = format.format(date);
        }
        return formatString;
    }


    /**
     * 将Date格式化为指定格式. 如果格式字符串为空或者date为空，那么返回空字符串"".
     *
     * @param date
     * @param formatString
     * @return String
     */
    public static String format(Date date, String formatString) {
        String result = "";
        if (StringUtils.isBlank(formatString)) {
            formatString = DEFAULT_TIME_FORMAT;
        }
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            result = format.format(date);
        }
        return result;
    }

    /**
     * 将日期时间格式化为yyyy-MM-dd, 如果Date为空，返回"".
     *
     * @param  date
     * @return String
     */
    public static String simpleFormat(Date date) {
        String formatString = "";
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            formatString = format.format(date);
        }
        return formatString;
    }

    /**
     * 接受三种格式的日期字符串，并将其转换为Date类型，如果字符串格式不符合以下三种格式，返回null. <br/>
     * 1. yyyy-MM-dd HH:mm:ss 2. yyyy-MM-dd 3. HH:mm:ss
     *
     * @param dateString
     * @return
     */
    public static Date getDate(String dateString) {
        Date date = null;
        String format = "";
        if (StringUtils.isNotBlank(dateString)) {
            if (8 == StringUtils.length(dateString.trim())) {
                format = "HH:mm:ss";
            } else if (10 == StringUtils.length(dateString.trim())) {
                format = DEFAULT_DATE_FORMAT;
            } else {
                format = DEFAULT_TIME_FORMAT;
            }
            try {
                date = org.apache.commons.lang3.time.DateUtils.parseDate(dateString, format);
            } catch (ParseException e) {
                // ignore
            }
        }
        return date;
    }

    /**
     * 将原时间加上时长后生成新的时间
     *
     * @param date 原时间
     * @param duration 时长，格式必须为HH:mm:ss
     * @return 相加后的时间
     */
    public static Date add(Date date, String duration) {
        long beforeTime = date.getTime();
        long addTime = getDate(duration).getTime() - BASIC_TIME;
        long afterTime = beforeTime + addTime;
        return new Date(afterTime);
    }

    /**
     * 将原时间减去时长后生成新的时间
     *
     * @param date 原时间
     * @param duration 时长，格式必须为HH:mm:ss
     * @return 相减后的时间
     */
    public static Date subtract(Date date, String duration) {
        long beforeTime = date.getTime();
        long addTime = getDate(duration).getTime() - BASIC_TIME;
        long afterTime = beforeTime - addTime;
        return new Date(afterTime);
    }

    /**
     * 获取给定日期所在星期的星期一，一周开始日期
     *
     * @param dateString 格式为"yyyy-MM-dd HH:mm:ss"的字符串
     * @return java.util.Date
     */
    public static Date getFirstDayOfWeek(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateString);
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
        cal.add(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.MINUTE, 0);
        cal.add(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取给定日期所在星期的星期日，一周结束日期
     *
     * @param dateString 格式为"yyyy-MM-dd HH:mm:ss"的字符串
     * @return java.util.Date
     */
    public static Date getLastDayOfWeek(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateString);
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
     * <b>方法说明：</b>
     * <ul>
     * 获取给定日期在一周内的星期序号，从周一开始
     * </ul>
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            return 7;
        } else {
            return --dayWeek;
        }
    }

    /**
     * 获取给定日期所在月的第一天
     *
     * @param dateString 格式为"yyyy-MM-dd HH:mm:ss"的字符串
     * @return java.util.Date
     */
    public static Date getFirstDayOfMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateString);
            cal.setTime(time);
        } catch (ParseException e) {
            // ignore
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取给定日期所在月的最后一天
     *
     * @param dateString 格式为"yyyy-MM-dd HH:mm:ss"的字符串
     * @return java.util.Date
     */
    public static Date getLastDayOfMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date time = sdf.parse(dateString);
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
     * <b>方法说明：</b>
     * <ul>
     * 在当前日期上加i天
     * </ul>
     * @param date
     * @return
     */
    public static Date addOneDay(Date date, int i) {
        if (0 == i) {
            return date;
        } else {
            return org.apache.commons.lang3.time.DateUtils.addDays(date, i);
        }
    }
}
