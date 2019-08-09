package com.car.base.common.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/** 公共日期时间操作工具类 */
public class DateTime {
	/** DateTimeFormat1 = "yyyy-MM-dd HH:mm:ss" */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**时间开始日期 */
	public static final String START="2014-01";
	/**中午十二点*/
	public static final String NOONTIME="12:00:00";

	/**
	 * @return 返回文本信息的日期对应的格林威治标准时间（1970年1月1日00:00:00.000）的偏移量,单位是毫秒。 1秒=1000毫秒。
	 * @param format
	 *            "yyyy-MM-dd HH:mm:ss:SSS"或"yyyy-MM-dd HH:mm:ss"。
	 */
	public static long dateTimeParse(String datetime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(datetime);
			return date.getTime();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return 0;
	}
	

	

	/**
	 * 字符串类型 转换成 date
	 * @return
	 */
	public static Date str2DateTime(String date){
		Date d = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
		return d;
	}
	
	
	


}


