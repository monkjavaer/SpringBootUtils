package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (date == null) ? "" : format.format(date);
	}


	/**
	 *
	 * @param date 返回HH:mm:ss MM/dd格式；如果是当天就只返回HH:mm:ss
	 * @return
	 */
	public static String dateToStringFormat(Date date){

		String timeStr = null;
		String absoluteTimeStr ;
		if(date != null){
			absoluteTimeStr = date.toString();
			Long bwt = DateUtil.daysBetween(absoluteTimeStr.substring(0, 19), DateUtil.currentTime("yyyy-MM-dd HH:mm:ss"), false, null);

			if(bwt != 0){
				timeStr = absoluteTimeStr.substring(11, 19)+" "+absoluteTimeStr.substring(5, 7)+"/"+absoluteTimeStr.substring(8, 10);
			}else {
				timeStr = absoluteTimeStr.substring(11, 19);
			}
		}
		return timeStr;
	}

	/**
	 *
	 * @param absoluteTimeStr 返回HH:mm:ss MM/dd格式；如果是当天就只返回HH:mm:ss
	 * @return
	 */
	public static String stringToStringFormat(String absoluteTimeStr){

		String timeStr = null;
		if(StringUtils.isNotEmpty(absoluteTimeStr)){
			Long bwt = DateUtil.daysBetween(absoluteTimeStr.substring(0, 19), DateUtil.currentTime("yyyy-MM-dd HH:mm:ss"), false, null);

			if(bwt != 0){
				timeStr = absoluteTimeStr.substring(11, 19)+" "+absoluteTimeStr.substring(5, 7)+"/"+absoluteTimeStr.substring(8, 10);
			}else {
				timeStr = absoluteTimeStr.substring(11, 19);
			}
		}
		return timeStr;
	}

	/**
	 * @param timeFormat
	 *            请使用本类提供的类型。格式："HH:mm:ss"或"HH:mm:ss:SSS" 。
	 * @return 返回指定格式的当前时间
	 */
	public static String currentTime(String timeFormat) {
		Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		return sdf.format(d);
	}

	public static String currentTime() {
		Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

	/**
	 * [daysBetween 获得两个日期字符串之间的天数差]
	 * @param  {[String]} startDate [传入的开始日期]
	 * @param  {[String]} endDate [传入的结束日期]
	 * @param  {[boolean]} requiredAbs [是否需要取绝对值,false 用于判断时间先后]
	 * @param  {[float]} ratio [时间差系数,默认是8640000,表示一天]
	 * @author liulin 2013/01/29
	 * @return {[Integer]}     [差值]
	 */
	public static Long daysBetween(String startDate, String endDate, boolean requiredAbs, Long ratio) {
		//系数,默认为天数
		Long quotient = 3600000L;
		if(null != ratio && Float.parseFloat(String.valueOf(ratio)) > 0){
			quotient = ratio;
		}
		Long cha = DateUtil.dateTimeParse(startDate, "yyyy-MM-dd hh:mm") - DateUtil.dateTimeParse(endDate, "yyyy-MM-dd hh:mm");
		if(requiredAbs){
			return Math.abs(cha) / quotient;
		}else{
			return cha / quotient;
		}
	}

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

	public static String dateToStringWithoutSecond(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
	   return (date == null) ? "" : format.format(date);
	}
	
	public static String dateToStringWithoutTime(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	   return (date == null) ? "" : format.format(date);
	}
			
	/**
	 * 支持的日期字箱串格式
	 * 2015/09/09-09:09:09  2015/9/9-9:9:9  2015/09/09 09:09:09  2015/9/9 9:9:9
	 * 2015/09/09-09:09		2015/9/9-9:9    2015/09/09 09:09	 2015/9/9 9:9
	 * 2015/09/09-09		2015/9/9-9      2015/09/09 09   	 2015/9/9 9
	 * 2015/09/09			2015/9/9
	 * @param strDate
	 * @return
	 */
	public static Date stringToDate(String strDate) {
		String str = strDate.trim();
		Calendar calendar = Calendar.getInstance();
		String strLarge = str;
		String strSmall = "";
		int ind = (str.indexOf("-") > -1) ? str.indexOf("-") : str.indexOf(" ");
		if( ind > -1) {
			strLarge = str.substring(0, ind);
			strSmall = str.substring(ind + 1);
		} 
		
		String[] listLarge = strLarge.split("/");
		int year = Integer.parseInt(listLarge[0]);
		int month = Integer.parseInt(listLarge[1]);
		int day = Integer.parseInt(listLarge[2]);
		
		int hour, minute, second;
		hour = minute = second = 0;
		
		if(!"".equals(strSmall)) {
			String[] listSmall = strSmall.split(":");
			hour = Integer.parseInt(listSmall[0]);
			if(listSmall.length >= 2) {
				minute = Integer.parseInt(listSmall[1]);
			}
			
			if(listSmall.length >= 3) {
				second = Integer.parseInt(listSmall[2]);
			}
			
		}
		
		calendar.set(year, month, day, hour, minute, second);
		
		return calendar.getTime();
	}

	public static Date getDate(String dateString) {
		if (dateString == null || dateString.isEmpty()) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (dateString.length() == "yyyy-MM-dd HH:mm:ss".length()) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (dateString.length() == "yyyy-MM-dd HH:mm".length()) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}

		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
