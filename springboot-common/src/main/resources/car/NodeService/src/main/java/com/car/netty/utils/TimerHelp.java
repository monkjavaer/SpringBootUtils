package com.base.springboot.car.NodeService.src.main.java.com.car.netty.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimerHelp {
    /**
     * 日历对象
     */
    private static Calendar calendar;

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(TimerHelp.class);

    /**
     * @param strQueryOracleTime 原始时间
     * @param lQueryPeriod       需要增加的秒数
     * @return 返回增加秒数的时间
     * @Description: 获取查询oracle结束时间点
     */
    public static String getTime(String strQueryOracleTime, long lQueryPeriod) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oDate = new Date();
        String strEndTime;
        try {
            oDate = formatter.parse(strQueryOracleTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        long lTime = oDate.getTime();

        // 转化成毫秒
        lQueryPeriod = lQueryPeriod * 1000;
        long lTmep = lTime + lQueryPeriod;
        Date oTempDate = new Date(lTmep);
        // 查询结束时间
        strEndTime = formatter.format(oTempDate);
        return strEndTime;
    }

    /**
     * @param strQueryOracleTime 原始时间
     * @param lQueryPeriod       需要减少的秒数
     * @return 返回减少秒数的时间
     * @Description:  获取查询开始时间点
     */
    public static String getEndTime(String strQueryOracleTime, long lQueryPeriod) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatternew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oDate = new Date();
        String strEndTime;
        try {
            oDate = formatter.parse(strQueryOracleTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        long lTime = oDate.getTime();

        // 转化成毫秒
        lQueryPeriod = lQueryPeriod * 1000;
        long lTmep = lTime + lQueryPeriod;
        Date oTempDate = new Date(lTmep);
        // 查询结束时间
        strEndTime = formatternew.format(oTempDate);
        return strEndTime;
    }


    /**
     * @param strQueryOracleTime 原始时间
     * @param lQueryPeriod       需要减少的秒数
     * @return 返回减少秒数的时间
     * @Description:  获取查询开始时间点
     */
    public static String getStartTime(String strQueryOracleTime, long lQueryPeriod) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatternew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oDate = new Date();
        String strEndTime;
        try {
            oDate = formatter.parse(strQueryOracleTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        long lTime = oDate.getTime();

        // 转化成毫秒
        lQueryPeriod = lQueryPeriod * 1000;
        long lTmep = lTime - lQueryPeriod;
        Date oTempDate = new Date(lTmep);
        // 查询结束时间
        strEndTime = formatternew.format(oTempDate);
        return strEndTime;
    }

    /**
     * @param strTimeOne
     * @param strTimeTwo
     * @return long 两个时间的差（秒数）
     * @Description:  计算2个时间相差的秒数
     */
    public static long calLastedTime(String strTimeOne, String strTimeTwo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oDateOne = new Date();
        Date oDateTwo = new Date();
        try {
            oDateOne = formatter.parse(strTimeOne);
            oDateTwo = formatter.parse(strTimeTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long lTimeOne = oDateOne.getTime();
        long lTimeTwo = oDateTwo.getTime();

        long lBack = (lTimeTwo - lTimeOne) / 1000;

        return lBack;
    }

    /**
     * @param strTime
     * @return long 两个时间的差（秒数）
     * @Description:  时间string格式转换成Date
     */
    public static Date stringToDate(String strTime, String strFormat) throws Exception {

        SimpleDateFormat formatter = null;
        if(null == strFormat)
        {
             formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        else
        {
             formatter = new SimpleDateFormat(strFormat);
        }

        Date oDate = new Date();
        try
        {
            oDate = formatter.parse(strTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return oDate;
    }

    /**
     * @param strDateFormat
     * @return
     * @Description:  获取系统当前时间
     */
    public static String getTimeNow(String strDateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
        Date oDate = new Date();
        return formatter.format(oDate);
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时 (当前时间的秒值)
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟(当前时间的分钟)
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     * @return 返回秒钟(当前时间的秒值)
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date
     * @return 返回毫秒 (当前时间的秒值)
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回毫
     *
     * @param strTime
     * @return 返回毫 (当前时间的秒值)
     */
    public static long getMillisV2(String strTime, String strDateFormat) {
        Date oDate = new Date();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
            oDate = formatter.parse(strTime);
        }
        catch (ParseException pe)
        {
            LOGGER.error("异常信息:" + pe.getMessage(), pe);
        }

        return oDate.getTime();
    }

    /**
     * 根据时间字符串获取秒数
     *
     * @param strDate
     * @return long 返回
     */
    public static long getSecond(String strDate) {
        Date oDate = new Date();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            oDate = formatter.parse(strDate);
        } catch (ParseException pe) {
            LOGGER.error("异常信息:" + pe.getMessage(), pe);
        }

        return oDate.getTime() / 1000;
    }

    /**
     * 根据long 型时间转换成Date
     * @param ltime
     * @return long 返回
     * @Description:
     * @author z00562 2016年5月25日
     */
    public static String transferLongTimetoDateTime(Long ltime) {
        Date date = new Date(ltime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 根据long 型时间转换成Date
     * @param ltime
     * @return long 返回
     */
    public static String transferLongTimetoDateTimeV2(Long ltime) {
        Date date = new Date(ltime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 宇视协议时间转换成标准时间
     * @param strTime
     * @return String
     */
    public static String transUnvTimeToTimestamp(String strTime) throws Exception
    {
        String strBack = "";
        String strMic = "";
        int iLen = 0;
        if(null != strTime && !"".equals(strTime))
        {
            iLen = strTime.length();
        }
        if(null != strTime && !"".equals(strTime) && 0 != iLen)
        {
            String strYear = strTime.substring(0,4);
            String strMonth = strTime.substring(4,6);
            String strDay = strTime.substring(6,8);
            String strHours = strTime.substring(8,10);
            String strMin = strTime.substring(10,12);
            String strSec = strTime.substring(12,14);
            if(iLen > 14)
            {
                 strMic = strTime.substring(14,17);
            }
            else
            {
                strMic = "000";
            }
            strBack = strYear + "-" + strMonth + "-" + strDay + " "+ strHours + ":" + strMin + ":" + strSec + "." + strMic;
        }
        return strBack;
    }

    /**
     * 宇视标准时间转化成宇视分表时间
     * @param strTime
     * @return yyyy_MM_dd
     * @throws Exception
     */
    public static String transTimestampToUnvTbale(String strTime) throws Exception
    {
        String strBack = "";
        if(null != strTime && !"".equals(strTime))
        {
            String strYear = strTime.substring(0,4);
            String strMonth = strTime.substring(4,6);
            String strDay = strTime.substring(6,8);
            strBack = strYear + "_" + strMonth + "_" + strDay;
        }
        return strBack;
    }


    /**
     * 宇视协议时间转成路径
     * @param strTime
     * @return String
     */
    public static String transUnvTimeToTimestampPath(String strTime) throws Exception
    {
        String strBack = "";
        if(null != strTime && !"".equals(strTime))
        {
            String strYear = strTime.substring(0,4);
            String strMonth = strTime.substring(4,6);
            String strDay = strTime.substring(6,8);
            String strHours = strTime.substring(8,10);
            strBack = strYear + "\\" + strMonth + "\\" + strDay + "\\"+ strHours;
        }
        return strBack;
    }

    /**
     * 根据时间字符串获取秒数
     *
     * @param strDate
     * @return long 返回
     */
    public static long getSecondV2(String strDate)
    {
        Date oDate = new Date();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            oDate = formatter.parse(strDate);
        }
        catch (ParseException pe)
        {
            LOGGER.error("异常信息:" + pe.getMessage(), pe);
        }

        return oDate.getTime() / 1000;
    }

    /**
     * 根据开始时间和结束时间段内的时间集合，如果开始时间和结束时间一样的话就是取某一天的数据
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return listDate
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate)
    {

        List<Date> listDate = new ArrayList<Date>();
        // 把开始时间加入集合
        listDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        if(beginDate.getTime() == endDate.getTime())
        {
            return listDate;
        }
        while (true)
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime()))
            {
                listDate.add(cal.getTime());
            }
            else
            {
                break;
            }
        }
        listDate.add(endDate);// 把结束时间加入集合
        return listDate;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     * @param nowdate
     * @param delay
     * @return String
     */
    public static String getNextDay(String nowdate, String delay)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = stringToDate(nowdate, "yyyy-MM-dd");
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        }
        catch (Exception e)
        {
            return "";
        }
    }


    /**
     * 测试函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        //测试getDatesBetweenTwoDate
        String start = "2016-10-03";
        String end = "2016-11-06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(start);
        Date dEnd = sdf.parse(end);
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        for(int i=0;i<listDate.size();i++){
            System.out.println(sdf.format(listDate.get(i)));
        }

        String strDate = "20160630031308";
        String strUTCTime = null;
        String strTemp = null;
        String strTimeOne = "20160630000130";
        String strTimeTwo = "20160630000131";
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oDateOne = new Date();
            Date oDateTwo = new Date();
            try
            {
                oDateOne = formatter.parse(strTimeOne);
                oDateTwo = formatter.parse(strTimeTwo);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            long lTimeOne = oDateOne.getTime();
            long lTimeTwo = oDateTwo.getTime();

            long lBack = Math.abs((lTimeTwo - lTimeOne));

        } catch (Exception e) {
            LOGGER.error("异常信息：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
