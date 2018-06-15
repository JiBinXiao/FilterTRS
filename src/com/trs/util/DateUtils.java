package com.trs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 * <p>Description:日期操作类  </p>
 * @version 1.0
 */

public class DateUtils {
	/****************************获得日期的一系列方法*************************************/
	/**
	 * 获得服务器当前时间
	 * @return Date
	 */
	public static Date getDate() {
		return new Date();
	}
	/**
	 * 获得当前日期相对i天的日期
	 * @param offset 天数(可正可负)
	 * @return Date 改变后的日期
	 */
	public static Date getDate(int offset) {
		return getDate(getDate(),offset);
	}
	/**
	 * 获得相对某日i天的日期
	 * @param date 参照日期
	 * @param offset 天数(可正可负)
	 * @return Date 改变后的日期
	 */
	public static Date getDate(Date date, int offset) {
		Date rDate = new Date();
				
			rDate.setTime(date.getTime() + 86400000*Long.valueOf(offset));
			date = rDate;
		
		return rDate;
	}
	/**
	 * 获得相对某日i天的日期
	 * @param date 参照日期
	 * @param offset 天数(可正可负)
	 * @return Date 改变后的日期
	 */
	public static  Date  getDate(Date date, long offset){
		Date rDate = new Date();
		
		rDate.setTime(date.getTime() + 86400000*offset);
		date = rDate;
	
	return rDate;	
	}
	/**
	 * 从字符串中按照指定的格式生成日期对象.
	 * @param value 日期
	 * @param type 格式
	 * @return Date 格式后的日期
	 */
	public static Date getDate(String value, String type) {
		Date rtndate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(type);
			rtndate = sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtndate;
	}
	/**
	 * 根据整型的年度、月份和日生成日期
	 * @param year 年度
	 * @param month 月份
	 * @param day 日
	 * @return Date
	 */
	public static Date getDate(int year,int month,int day) {
		Calendar xmas = new GregorianCalendar(year,month-1,day);		
        return xmas.getTime();		
	}
	/**
	 * 根据字符型的年度、月份和日生成日期
	 * @param year 年度
	 * @param month 月份
	 * @param day 日
	 * @return Date
	 */
	public static Date getDate(String year,String month,String day) {
		return getDate(new Integer(year).intValue(),new Integer(month).intValue()-1,new Integer(day).intValue());        		
	}
	/****************************格式化日期时间的一系列方法*************************************/
	/**
	* 格式化日期时间
	* @param date Date 日期
	* @param datestyle int 日期格式化类型 
	* @param timestyle int 时间格式化类型
	* @return String
	*/
	public static String dateFormat(Date date, int datestyle, int timestyle) {
		DateFormat df = DateFormat.getDateTimeInstance(datestyle, timestyle);
		String rtndate = df.format(date);
		return rtndate;
	}
	/**
	* 格式化日期
	* @param date Date 日期
	* @param strFormat String 日期格式化类型 
	* @return String
	*/
	public static String dateFormat(Date date, String strFormat) {
		DateFormat df = new SimpleDateFormat(strFormat);
		return df.format(date);
	}	
	/****************************获得年度、月份、日期、小时、分和秒一系列方法*************************************/
	/**
	* 获得指定日期的年度字符串
	* @param date Date 日期
	* @return String
	*/
	public static String getYear(Date date) {
		return String.valueOf(_getYear(date));
	}
	/**
	* 获得指定日期的月份字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getMonth(Date date) {
		int month = _getMonth(date);
		if (month < 10) {
			return "0" + month;
		} else {
			return String.valueOf(month);
		}
	}
	/**
	* 获得指定日期的日字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getDay(Date date) {
		int day = _getDay(date);
		if (day < 10) {
			return "0" + day;
		} else {
			return String.valueOf(day);
		}
	}
	public static String getYear() {
		return getYear(getDate());
	}
	/**
	* 获得指定日期的月份字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getMonth() {
		return getMonth(getDate());
	}
	/**
	* 获得指定日期的日字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getDay() {
		return getDay(getDate());
	}
	/**
	* 获得指定日期的小时字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getHours(Date date) {
		int hours = _getHours(date);
		if (hours < 10) {
			return "0" + hours;
		} else {
			return String.valueOf(hours);
		}
	}	
	/**
	* 获得指定日期的分钟字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getMinutes(Date date) {
		int minutes = _getMinutes(date);
		if (minutes < 10) {
			return "0" + minutes;
		} else {
			return String.valueOf(minutes);
		}
	}	
	/**
	* 获得指定日期的秒字符串，不足2位，前面补0
	* @param date Date 日期
	* @return String
	*/
	public static String getSeconds(Date date) {
		int seconds = _getSeconds(date);
		if (seconds < 10) {
			return "0" + seconds;
		} else {
			return String.valueOf(seconds);
		}
	}
	/**
	* 获得指定日期的年度
	* @param date Date 日期
	* @return int
	*/
	public static int _getYear(Date date) {
		return getCalendar(date).get(Calendar.YEAR);
	}
	/**
	* 获得指定日期的月份
	* @param date Date 日期
	* @return int
	*/
	public static int _getMonth(Date date) {
		return getCalendar(date).get(Calendar.MONTH) + 1;
	}
	/**
	* 获得指定日期的日
	* @param date Date 日期
	* @return int
	*/
	public static int _getDay(Date date) {
		return getCalendar(date).get(Calendar.DAY_OF_MONTH);
	}
	/**
	* 获得指定日期的小时
	* @param date Date 日期
	* @return int
	*/
	public static int _getHours(Date date) {
		return getCalendar(date).get(Calendar.HOUR_OF_DAY);
	}
	/**
	* 获得指定日期的分钟
	* @param date Date 日期
	* @return int
	*/
	public static int _getMinutes(Date date) {
		return getCalendar(date).get(Calendar.MINUTE);
	}
	/**
	* 获得指定日期的秒
	* @param date Date 日期
	* @return int
	*/
	public static int _getSeconds(Date date) {
		return getCalendar(date).get(Calendar.SECOND);
	}
	/**
	* 根据指定日期获得日历对象
	* @param date Date 日期
	* @return Calendar
	*/
	public static Calendar getCalendar(Date date){
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c;		
	}
	/*********************与日期差值有关的方法*******************************/
	/**
	 * 获得两个日期之间的差值(可跨时区) 
	 * @param date1
	 * @param date2
	 * @param tz
	 * @return long 天数
	 */
	public static long getDateDiff(Date date1, Date date2, TimeZone tz) {
		Calendar cal1 = null;
		Calendar cal2 = null;
		if (tz == null) {
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
		} else {
			cal1 = Calendar.getInstance(tz);
			cal2 = Calendar.getInstance(tz);
		}
		cal1.setTime(date1);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
		long hr1 = (long) (ldate1 / 3600000); //60*60*1000
		long hr2 = (long) (ldate2 / 3600000);
		long days1 = (long) hr1 / 24;
		long days2 = (long) hr2 / 24;
		return days2 - days1;
		/*int weekOffset =(cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
		int weekDiff = dateDiff / 7 + weekOffset;
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);*/
	}
	
	/**
	 * 获得同一时区两个日期之间的差值
	 * @param date1 
	 * @param date2
	 * @return long 天数
	 */
	public static long getDateDiff(Date date1, Date date2) {
		if (date1==null) date1= getDate();
		if (date2==null) date2= getDate();		
		long ldate1 = date1.getTime();
		long ldate2 = date2.getTime();
		long iDatenum = 0;
		iDatenum = (long)((ldate2 - ldate1)/86400000);
		return iDatenum;
	}
	/********************************与周和星期有关的方法****************************/
	/**
	* 获得指定字符串类型日期的星期
	* @param sdate String 日期
	* @param fmt String 格式化类型 
	* @return String
	*/
	public static String getWeek(String sdate, String fmt) {
		SimpleDateFormat df = new SimpleDateFormat(fmt);		
		try {
			return getWeek(df.parse(sdate));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}		
	}
	/**
	 * 转换日期格式字符串
	 * @param value
	 *        日期值
	 * @param fromFormat
	 *        源格式
	 * @param toFormat
	 *        转换格式
	 * @return 
	 */
	public static  String  changeFormatStr(String value,String  toFormatStr){
		if(value==null||"".equals(value))
			return "";
		String   patternStr="[^0-9]?";
		Pattern pattern = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(value);
		String fromFormatStr="yyyyMMddHHmm";
		value=matcher.replaceAll("");
		if(value.length()==8)
			fromFormatStr="yyyyMMdd";
		if(value.length()==12)
			fromFormatStr="yyyyMMddHHmm";
		if(value.length()==14)
			fromFormatStr="yyyyMMddHHmmss";
	
		
		SimpleDateFormat fromFormat = new SimpleDateFormat(fromFormatStr);
		SimpleDateFormat toFormat = new SimpleDateFormat(toFormatStr);
		Date   formDate=null;
		try {
			   formDate=fromFormat.parse(value);
		} catch (ParseException e) {
			formDate=null;
		}
		if(formDate==null)
			return value;
		return toFormat.format(formDate);
	}
	
	
	/**
	* 获得指定日期的星期
	* @param date Date 日期
	* @return String
	*/
	public static String getWeek(Date date) {		
		Calendar cal1 = Calendar.getInstance();
		String chiweek = null;	
		cal1.setTime(date);
		int bh = cal1.get(Calendar.DAY_OF_WEEK);
		switch (bh) {
			case 1 :
				chiweek = "星期日";
				break;
			case 2 :
				chiweek = "星期一";
				break;
			case 3 :
				chiweek = "星期二";
				break;
			case 4 :
				chiweek = "星期三";
				break;
			case 5 :
				chiweek = "星期四";
				break;
			case 6 :
				chiweek = "星期五";
				break;
			case 7 :
				chiweek = "星期六";
				break;
		}
		return chiweek;
	}

	/**
	* 获得当前日期是今年的第几周
	* @return int 周数
	*/
	public static int getWeekOfYear() {
		return getWeekOfYear(getDate());
	}
	/**
	* 获得指定日期是今年的第几周
	* @param date Date 日期
	* @return int 周数
	*/
	public static int getWeekOfYear(Date date) {			
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	* 获得当前日期offset天是今年的第几周
	* @param offset int 日期
	* @return int 周数
	*/
	public static int getWeekOfYear(int offset) {
		return getWeekOfYear(getDate(), offset);
	}
	/**
	* 获得某日期offset天是今年的第几周
	* @param date Date 参照日期
	* @param offset int 日期
	* @return int 周数
	*/
	public static int getWeekOfYear(Date date,int offset) {
		return getWeekOfYear(getDate(date, offset));
	}	
	/**
	* 获得指定年有多少周
	* @param int year 年度
	* @return int 周数
	*/
	public static int getWeekNumbersOfYear(int year) {
		GregorianCalendar cal = new GregorianCalendar(year,12,31);
		return cal.getMaximum(GregorianCalendar.WEEK_OF_YEAR);
	}
	/**
	* 获得指定年有多少周
	* @param String year 年度
	* @return int 周数
	*/
	public static int getWeekNumbersOfYear(String year) {
		return getWeekNumbersOfYear(new Integer(year).intValue());
	}
	/**
   * 得到指定年月的最后一天
   * @param strDate String 字符串格式的日期
   * @return String 月份最后一天的字符
   */
	public static String getLastDayOfMonth(String strDate) {
		int year=new Integer(strDate.substring(0,4)).intValue();
		int month=new Integer(strDate.substring(5,7)).intValue();
		return dateFormat(getLastDate(year,month),"dd");
	}
	/**
	* 得到指定年月的最后一天的日期
	* @param date int 年
	* @param month int 月
	* @return Date
	*/
	public static Date getLastDate(int year, int month) {
		int day = 0;
		String strmonth = null;
		boolean blrn = false; //是否为闰年
		if ((month < 1) || (month > 12)) {
			return null;
		}
		if (((year % 4 == 0) || (year % 100 == 0)) && (year % 400 == 0)) {
			blrn = true;
		} else {
			blrn = false;
		}
		if ((month == 1)|| (month == 3)|| (month == 5)|| (month == 7)|| (month == 8)|| (month == 10)|| (month == 12)) {
			day = 31;
		}
		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			day = 30;
		}
		if (month == 2) {
			if (blrn == true) {
				day = 29;
			} else {
				day = 28;
			}
		}
		if (month < 10) {
			strmonth = "0" + Integer.toString(month);
		} else {
			strmonth = Integer.toString(month);
		}
		return getDate(Integer.toString(year) + "-" + strmonth + "-" + Integer.toString(day),"yyyy-MM-dd");
	}
	/**
	 * 
	 * @param dateString
	 *         日期字符串
	 * @return
	 */
	public  static   String   replaceDateStr(String dateString){
	
		
		
		return dateString;
		
	}
	/**
	 * 查到相隔俩个时间间隔秒数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static  long   getBetweenSecond(Date beginDate,Date endDate){
		long  longBeginDate=beginDate.getTime();
		long  longEndDate=endDate.getTime();
		return longEndDate/1000-longBeginDate/1000;
	}
	/**
	 * 获取 N秒后日期
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static  Date  getDateSecond(Date date,long  seconds){
		return new Date(date.getTime()+seconds*1000);
	}
	/**
	 * 
	 * @Title dateToSqlDate
	 * @Description TODO(普通日期转换SQL日期)
	 * @param date
	 * @return
	 */
	public  static  java.sql.Date   dateToSqlDate(Date date){
		return date==null?null:new  java.sql.Date(date.getTime());
	}
	
	
	
    /**
     * 
     * @Title sqlDateToDate
     * @Description TODO(SQL日期转换普通日期)
     * @param date
     * @return
     */
	public  static  Date   sqlDateToDate(java.sql.Date date){
		return date==null?null:new  Date(date.getTime());
	}
	
	
	/**
	 * 
	 * @Title dateToSqlDate
	 * @Description TODO(普通日期转换SQL日期)
	 * @param date
	 * @return
	 */
	public  static  java.sql.Timestamp   dateToSqlTimestamp(Date date){
		return date==null?new java.sql.Timestamp(new Date().getTime()):new  java.sql.Timestamp (date.getTime());
	}
	
	
	
    /**
     * 
     * @Title sqlDateToDate
     * @Description TODO(SQL日期转换普通日期)
     * @param date
     * @return
     */
	public  static  Date   sqlTimestampToDate(java.sql.Timestamp date){
		return date==null?null:new  Date(date.getTime());
	}
	
	/**
	 * 判断是否是限定时间
	 * @param limitdate
	 *        限制时间单位天
	 * @param histime
	 *        之前时间
	 * @param nowtime
	 *        当前时间
	 * @return
	 */
	public  static boolean isSimLimit(long limitdate,long histime,long nowtime){
		if((nowtime - histime) > limitdate*24*60*60*1000)
			return true;
		return false;
		
	}
	
	public static void main(String[] args) {
		
		System.err.println(new Date(10000000));;
		//System.out.println(getDate(-1));
		String   dateStr=" 2015-09/22 ";
		System.out.println(changeFormatStr(dateStr, "yyyyMMdd HH:mm:ss"));
	
		SimpleDateFormat fromFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");
		Date  dd=new Date();
		System.out.println(fromFormat.format(dd));
	}
}
