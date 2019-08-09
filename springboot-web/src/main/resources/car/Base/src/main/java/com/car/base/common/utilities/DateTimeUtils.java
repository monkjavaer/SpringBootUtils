package com.car.base.common.utilities;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 时间日期帮助类
 * @author Administrator
 *
 */
public class DateTimeUtils {

	public static final String DEFAULT_FORMAT_DATE_WITHOUT_TIME = "yyyy-MM-dd";
	public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String ANG_FORMAT_DATE = "dd-MM-yyyy HH:mm:ss";
	public static final String ANG_FORMAT_DATE2 = "dd/MM/yyyy HH:mm:ss";
	public static final String DEFAULT_FORMAT_DATE_OTHER = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final Pattern DATE_PATTERN = Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])([-/.]?)(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0?[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2([-/.]?)29)$");

	/**
	 * 检测时间格式是否合法
	 * @param timeString 校验串 dd-MM-yyyy HH:mm:ss
	 * @return true 合法
	 */
	public static boolean checkTimeString(String timeString){
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat(ANG_FORMAT_DATE);
		format.setLenient(false);
		try {
			format.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验时间格式
	 * @param dateText
	 * @return
	 */
	public static boolean validateDateFormat(String dateText){
		return DATE_PATTERN.matcher(dateText).matches();
	}



	public static int getHour(String dateStr){
		Date date = null;
		try {
			date = DateTimeUtils.formatDate(dateStr, null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取是第几个小时
		Calendar calendar = simpleDateFormat.getCalendar();
		assert date != null;
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}


	public static String getDay(String dateStr){
		// 处理参数
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayFormat.format(date);
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Timestamp now(){
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 通过Timestamp获取安哥拉格式时间
	 * @param timestamp
	 * @return
	 */
	public static String getTimeByTimestamp(Timestamp timestamp){
		SimpleDateFormat df = new SimpleDateFormat(ANG_FORMAT_DATE);
		return df.format(timestamp);
	}

	public static String getTimeByTimestampDefault(Timestamp timestamp){
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		return df.format(timestamp);
	}
	/**
	 * 获取当前时间字符串
	 *
	 * @param
	 */
	public static String getNowTime() {
		return formatDate(new Date(), null);
	}

	/**
	 * 获取当前时间字符串yyyy-MM-dd
	 * @return
	 */
	public static String getTodayTimeInit() {

		return formatDate(new Date(), DEFAULT_FORMAT_DATE_WITHOUT_TIME);
	}

	/**
	 * 格式化传入时间，如果传入时间大于当前时间就返回当前时间,否则返回原值
	 * @param time 传入时间
	 * @return 格式化后的时间
	 * @throws ParseException
	 */
	public static String initTime(String time) throws ParseException {
		Date now = new Date();
		Date passDate = DateTimeUtils.formatDate(time,null);
		return now.before(passDate) ? DateTimeUtils.formatDate(now,null) : time;
	}


	/**
	 * 给时间加上几个小时
	 * @param day 当前时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param hour 需要加的时间
	 * @return
	 */
	public static String addDateHour(String day, int hour) throws ParseException {
		if (StringUtils.isBlank(day)) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		Date date = format.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 24小时制
		cal.add(Calendar.HOUR, hour);
		date = cal.getTime();
		return format.format(date);

	}


	/**
	 * 给时间加上几分钟
	 * @param day 当前时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param minute 需要加的时间
	 * @return
	 */
	public static String addDateMinute(String day, int minute) throws ParseException {
		if (StringUtils.isBlank(day)) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		Date date = format.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 24小时制
		cal.add(Calendar.MINUTE, minute);
		date = cal.getTime();
		return format.format(date);

	}


	/**
	 * 判断时间是否为今天
	 * @param dateStr yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean isToday(String dateStr){
		String todayStr = formatDate(new Date(), DEFAULT_FORMAT_DATE_WITHOUT_TIME);
		return todayStr.equals(dateStr.substring(0, 10));

	}
	/**
	 * 获取当前时间字符串
	 *
	 */
	public static String getNowTimeByyyyyMMddHHmmss(Date date) {
		return formatDate(date, "yyyyMMddHHmmss");
	}

	public static Date resetTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 比较两个时间，如果endtime晚于begintime，则返回true，否则返回false
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean compareTime(String beginTime, String endTime) {
		long begin = DateTime.dateTimeParse(beginTime, DEFAULT_FORMAT_DATE);
		long end = DateTime.dateTimeParse(endTime, DEFAULT_FORMAT_DATE);
		if (end > begin) {
			return true;
		}
		return false;
	}

	/**
	 * 获取Calendar
	 * @return
	 */
	public static Calendar getCalendar(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		return simpleDateFormat.getCalendar();
	}

	/**
	 * 比较两个时间，如果endtime晚于begintime，则返回true，否则返回false
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean compareTime(String beginTime, String endTime,String dateformat) {
		long begin = DateTime.dateTimeParse(beginTime, dateformat);
		long end = DateTime.dateTimeParse(endTime, dateformat);
		if (end > begin) {
			return true;
		}
		return false;
	}

	/**
	 * date转string
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(Date date, String formatStr) {
		return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE
				: formatStr))).format(date);
	}

	/**
	 * @author monkjavaer
	 * @description 根据结束时间yyyy-MM-dd HH:mm:ss返回开始时间（只到小时yyyy-MM-dd HH:00:00）
	 * @date 14:51 2019/7/3
	 * @param: [endTime]
	 * @return java.lang.String
	 **/
	public static String getDateOneHourString(String endTime) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		return dateFormat.format(DateTimeUtils.formatDate(endTime,null));
	}

	/**
	 *  string转date  "yyyy-MM-dd HH:mm:ss"
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String dateStr, String formatStr)
			throws ParseException {
		return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE
				: formatStr))).parse(dateStr);
	}

	/**
	 * String转date  "yyyy-MM-dd HH:mm"
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDateOther(String dateStr, String formatStr)
			throws ParseException {
		return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE_OTHER
				: formatStr))).parse(dateStr);
	}

	/**
	 * Sting to TimeStamp yyyymmddhhmmssSSS - "yyyy/mm/dd hh:mm:ss"
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp timeStampFromString(String dateStr) throws ParseException{

		String tempString = dateStr.substring(0,4)+"-"+dateStr.substring(4,6)+"-"+dateStr.substring(6,8)
				+" "+dateStr.substring(8,10)+":"+dateStr.substring(10,12)+":"+dateStr.substring(12,14);
		return Timestamp.valueOf(tempString);

	}

	/**
	 * 获得某个时间的前几天，或者后几天
	 * @param dateStr 基准时间
	 * @param days 正数表示该日期后n天，负数表示该日期的前n天
	 * @return
	 */
	public static String getBeforeOrAfterTime(String dateStr,int days){
		// 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
		Date date = sdf.parse(dateStr, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
		calendar.add(Calendar.DATE, days);
		Date date1 = calendar.getTime();
		return sdf.format(date1);
	}

	public static String getBeforeOrAfterMin(String dateStr,int min){
		// 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
		Date date = sdf.parse(dateStr, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// add方法中的第二个参数n中，正数表示该日期后n分钟，负数表示该日期的前n分钟
		calendar.add(Calendar.MINUTE, min);
		Date date1 = calendar.getTime();
		return sdf.format(date1);
	}

	public static String getBeforeOrAfterHours(String dateStr,int hour){
		// 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
		Date date = sdf.parse(dateStr, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// add方法中的第二个参数n中，正数表示该日期前几小时，负数表示该日期的后几小时
		calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - hour);
		Date date1 = calendar.getTime();
		return sdf.format(date1);
	}



	/**
	 * yyyy-MM-dd HH:mm:ss格式转yyyyMMddHHmmss
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getyyyyMMddHHmmss(String dateStr) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf1.parse(dateStr);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf2.format(date);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Integer daysBetween(Date smdate,Date bdate) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		smdate=sdf.parse(sdf.format(smdate));
		bdate=sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long betweenDays=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 计算两个时间的相差天数，不满一天的当一天计算
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static Integer daysBetween(String smdate,String bdate) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long betweenDays=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(betweenDays))+1;
	}

	/**
	 * 获取一年前的时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getOneYearAgo(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		//过去一年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		return format.format(y);
	}

	/**
	 * 获取一月前的时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getOneMonthAgo(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date y = c.getTime();
		return format.format(y);
	}

}
