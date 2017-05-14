/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014年4月21日
 * Id: DateUtil.java,v 1.0 2014年4月21日 下午1:55:04 admin
 */
package com.zhiyun.bigdata.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName DateUtil
 * @author yehao
 * @date 2014年4月21日 下午1:55:53
 * @Description 时间的一个调整方法
 */
public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String[] dayOfWeek = {"SUN","MON","TUES","WED","THUR","FRI","SAT"};
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_TIME_FOMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @return 当前时间字符串
	 * @Description 获得当前静态的时间字符串,以默认的yyyy-MM-dd hh:mm:ss 格式
	 */
	public static String getLocalDateString(){
		return dateFormat.format(new Date());
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date 时间
	 * @return
	 * @Description 格式化一个时间,以默认的yyyy-MM-dd hh:mm:ss 格式
	 */
	public static String getDate(Date date){
		return dateFormat.format(date);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date 时间对象
	 * @param fomat 时间格式
	 * @return 时间字符串
	 * @Description 将时间是格式化成指定的字符串格式
	 */
	public static String parseDate(Date date , String fomat){
		if(date == null || fomat == null) return null;
		String source = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
		source = dateFormat.format(date);
		return source;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param source 时间字符串
	 * @param fomat 时间格式
	 * @return 格式化好的时间
	 * @throws ParseException 
	 * @Description 按照指定的时间字符串格式化一个时间
	 */
	public static Date parseDate(String source , String fomat) {
		if(StringUtils.isEmpty(source) || fomat == null) return null;
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			logger.warn("字符串转时间异常，字符为：" + source + "，转换目标格式：" + fomat, e);
		}
		return date;
	}
	
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param source 日期格式
	 * @return 日期
	 * @throws ParseException 
	 * @Description 将时间字符串格式化成一个时间，以默认的yyyy-MM-dd hh:mm:ss 格式
	 */
	public static Date getDate(String source) {
		Date date = null;
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			logger.warn("字符串转时间异常，字符为："+ source , e);
		}
		return date;
	}
	
	/**
	 * @author admin
	 * @date 2015年6月17日
	 * @param date
	 * @param count
	 * @param timeType
	 * @return
	 * @Description 获取之前的时间
	 */
	public static Date getBeforeTime(Date date , int count , int field){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - count);
		return calendar.getTime();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date
	 * @param days
	 * @param format 希望返回的日期格式
	 * @return the date  someone days before the input date
	 * @description 返回输入日期n天前的日期
	 */
	public static String getBeforeDate(Date date,int days,String format) {   
		if(date == null) return null;
	    SimpleDateFormat df = new SimpleDateFormat(format);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return df.format(calendar.getTime());   
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date
	 * @return the date  someone days before the input date
	 * @description 返回输入日期n天前的日期
	 */
	public static Date getBeforeDate(Date date,int days) {   
		if(date == null) return null;
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return calendar.getTime();   
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date
	 * @param days
	 * @param format 希望返回的日期格式
	 * @return the date someone days after the input date
	 * @description 返回输入日期n天后的日期
	 */
	public static String getAfterDate(Date date,int days,String format) {   
		if(date == null) return null;
	    SimpleDateFormat df = new SimpleDateFormat(format);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return df.format(calendar.getTime());   
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date
	 * @param days
	 * @return the date someone days after the input date
	 * @description 返回输入日期n天后的日期
	 */
	public static Date getAfterDate(Date date,int days) {   
		if(date == null) return null;
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return calendar.getTime();   
	}
	
	
	
	/** 
	 * @author yehao
	 * @date 2014年4月21日
	 * @param month
	 * @return
	 * @Description 返回指定月的最后一天
	 */
	public static String getLastDay(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] datetimes = month.split("-");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.YEAR, Integer.parseInt(datetimes[0]));
		lastDate.set(Calendar.MONTH, Integer.parseInt(datetimes[1]));
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		//lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}
	

	
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param month
	 * @return
	 * @Description 获取指定月的第一天
	 */
	public static String getFirstDayOfMonth(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] datetimes = month.split("-");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.YEAR,Integer.parseInt(datetimes[0]));
		firstDate.set(Calendar.MONTH,Integer.parseInt(datetimes[1])-1);
		firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(firstDate.getTime());
		return str;
	}
	
	
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param d
	 * @return
	 * @Description 获取指定月的天数
	 */
	public static int getMonthDays(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String date=sdf.format(d);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) -1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param d
	 * @return
	 * @Description 获取指定年的月
	 */
	public static Integer getMonth(Date d){
	    Calendar lastDate = Calendar.getInstance();
		   lastDate.setTime(d);
		   lastDate.add(Calendar.MONTH, 1);
		   int month = lastDate.get(Calendar.MONTH);
		return month;
	}
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param date
	 * @return
	 * @Description 判断日期字符串是否超过了今天之后
	 */
	public static boolean checkDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String td = sdf.format(today);
		Date d1,d2;
		try {
			d1 = sdf.parse(date);
			d2 = sdf.parse(td);
			if(d1.getTime()>d2.getTime()){
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	/**
	 * @author zhangkun
	 * @date 2014年5月23日
	 * @param dateTime 指定日期
	 * @return
	 * @throws ParseException 
	 * @Description 获取指定日期的上周一
	 */
	public static String getMondayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//如果是星期天,取14天前
		if("SUN".equals(DateUtil.getDayOfWeek(dateTime))){
			cal.add(Calendar.DATE, -14);
		}else{
			cal.add(Calendar.WEEK_OF_YEAR, -1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sdf.format(cal.getTime());
	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014年5月23日
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description 获取指定日期的上周日
	 */
	public static String getSundayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//不是周日时往后推7天
		if(!"SUN".equals(DateUtil.getDayOfWeek(dateTime))){
			return DateUtil.getAfterDate(cal.getTime(), 7, "yyyy-MM-dd");
		}else{
			return sdf.format(cal.getTime());
		}
	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014年5月23日
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description 判断指定日期为子星期几
	 */
	public static String getDayOfWeek(String dateTime) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar c = Calendar.getInstance();
		  c.setTime(format.parse(dateTime));
		  int n = c.get(Calendar.DAY_OF_WEEK) - 1;
		  return DateUtil.dayOfWeek[n];
	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014年5月23日
	 * @param dateTime
	 * @return
	 * @throws ParseException 
	 * @Description 获取指定日期上个月
	 */
	public static String getLastMonth(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		String lastMonth = sdf.format(cal.getTime());
		return lastMonth.substring(0, 7);
	}

	/**
	 * @author admin
	 * @date 2014年8月11日
	 * @param dateTime
	 * @param i
	 * @return
	 * @throws ParseException
	 * @Description 获取当前时间的下个月份相加减
	 */
	public static Date getNextMonth(String dateTime , int i){
		Date date = null;
		if(StringUtils.isNotEmpty(dateTime)){
			date = parseDate(dateTime, "yyyy-MM-dd");
			if(date == null)
				date = new Date();
		} else {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014年6月7日
	 * @return
	 * @throws ParseException 
	 * @Description 获取第二天凌晨零点零分零秒
	 */
	public static Date getTimeOf12AmOfNextDay() throws ParseException{
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return  cal.getTime();
	}
	
	/**
	 * @author zhangkun
	 * @date 2014年6月25日
	 * @param n
	 * @return
	 * @Description 获取n分钟后的时间
	 */
	public static Date getNMinutesLater(int n){
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*60*1000);
		return nMinutesLater;

	}
	
	/**
	 * @author zhangkun
	 * @date 2014年6月25日
	 * @param n
	 * @return
	 * @Description 获取n秒钟后的时间
	 */
	public static Date getNSecondsLater(int n){
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*1000);
		return nMinutesLater;

	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014年6月25日
	 * @param n
	 * @return
	 * @Description 获取n分钟后的时间
	 */
	public static String getNMinutesLaterString(int n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*60*1000);
		return sdf.format(nMinutesLater);
	}
	
	/**
	 * @author admin
	 * @date 2014年11月4日
	 * @param oldDate 原日期
	 * @param newDate 新日期
	 * @return
	 * @Description 获取两个日期相差的天数，去掉当前天
	 */
	public static int getDateDiff(Date oldDate , Date newDate){
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(oldDate);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(newDate);
		int year = calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR);
		if(year >= 0) {
			return (year * 365) + (calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR));
		} else {
			return 0;
		}
	}
	
	/** 
	 * @author Administrator
	 * @date 2015年4月7日
	 * @param length
	 * @return
	 * @Description TODO(转换成时分秒)
	 */
	public static String parseTime(long length) {
		if(length <=0){
			return "不足1分钟";
		}
		String s = "";
//		long sec = length%60;
		long min = length/60%60;
		long hour = length/3600;
		s = hour + "小时" +min + "分钟";
		return s;
	}
	
	/**
	 * @author zhangkun
	 * @date 2014年6月7日
	 * @return
	 * @throws ParseException 
	 * @Description 获取第二天凌晨零点零分零秒
	 */
	public static Date getTimeOf12AmOfNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return  cal.getTime();
	}
}