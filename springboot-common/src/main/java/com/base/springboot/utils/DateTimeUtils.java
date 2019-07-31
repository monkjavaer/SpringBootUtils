package com.base.springboot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author monkjavaer
 * @date 2019/7/31 13:49
 */
public class DateTimeUtils{

    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 将Date格式化为yyyy-MM-dd HH:mm:ss格式, 如果Date为空，返回"".
     * @param date
     * @return
     */
    public static String format(Date date) {
        String formatString = "";
        if (null != date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatString = format.format(date);
        }
        return formatString;
    }

}
