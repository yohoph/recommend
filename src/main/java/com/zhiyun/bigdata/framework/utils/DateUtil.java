/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014��4��21��
 * Id: DateUtil.java,v 1.0 2014��4��21�� ����1:55:04 admin
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
 * @date 2014��4��21�� ����1:55:53
 * @Description ʱ���һ����������
 */
public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String[] dayOfWeek = {"SUN","MON","TUES","WED","THUR","FRI","SAT"};
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_TIME_FOMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @return ��ǰʱ���ַ���
	 * @Description ��õ�ǰ��̬��ʱ���ַ���,��Ĭ�ϵ�yyyy-MM-dd hh:mm:ss ��ʽ
	 */
	public static String getLocalDateString(){
		return dateFormat.format(new Date());
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param date ʱ��
	 * @return
	 * @Description ��ʽ��һ��ʱ��,��Ĭ�ϵ�yyyy-MM-dd hh:mm:ss ��ʽ
	 */
	public static String getDate(Date date){
		return dateFormat.format(date);
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param date ʱ�����
	 * @param fomat ʱ���ʽ
	 * @return ʱ���ַ���
	 * @Description ��ʱ���Ǹ�ʽ����ָ�����ַ�����ʽ
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
	 * @date 2014��4��21��
	 * @param source ʱ���ַ���
	 * @param fomat ʱ���ʽ
	 * @return ��ʽ���õ�ʱ��
	 * @throws ParseException 
	 * @Description ����ָ����ʱ���ַ�����ʽ��һ��ʱ��
	 */
	public static Date parseDate(String source , String fomat) {
		if(StringUtils.isEmpty(source) || fomat == null) return null;
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			logger.warn("�ַ���תʱ���쳣���ַ�Ϊ��" + source + "��ת��Ŀ���ʽ��" + fomat, e);
		}
		return date;
	}
	
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param source ���ڸ�ʽ
	 * @return ����
	 * @throws ParseException 
	 * @Description ��ʱ���ַ�����ʽ����һ��ʱ�䣬��Ĭ�ϵ�yyyy-MM-dd hh:mm:ss ��ʽ
	 */
	public static Date getDate(String source) {
		Date date = null;
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			logger.warn("�ַ���תʱ���쳣���ַ�Ϊ��"+ source , e);
		}
		return date;
	}
	
	/**
	 * @author admin
	 * @date 2015��6��17��
	 * @param date
	 * @param count
	 * @param timeType
	 * @return
	 * @Description ��ȡ֮ǰ��ʱ��
	 */
	public static Date getBeforeTime(Date date , int count , int field){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - count);
		return calendar.getTime();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param date
	 * @param days
	 * @param format ϣ�����ص����ڸ�ʽ
	 * @return the date  someone days before the input date
	 * @description ������������n��ǰ������
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
	 * @date 2014��4��21��
	 * @param date
	 * @return the date  someone days before the input date
	 * @description ������������n��ǰ������
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
	 * @date 2014��4��21��
	 * @param date
	 * @param days
	 * @param format ϣ�����ص����ڸ�ʽ
	 * @return the date someone days after the input date
	 * @description ������������n��������
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
	 * @date 2014��4��21��
	 * @param date
	 * @param days
	 * @return the date someone days after the input date
	 * @description ������������n��������
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
	 * @date 2014��4��21��
	 * @param month
	 * @return
	 * @Description ����ָ���µ����һ��
	 */
	public static String getLastDay(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] datetimes = month.split("-");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.YEAR, Integer.parseInt(datetimes[0]));
		lastDate.set(Calendar.MONTH, Integer.parseInt(datetimes[1]));
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		//lastDate.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}
	

	
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param month
	 * @return
	 * @Description ��ȡָ���µĵ�һ��
	 */
	public static String getFirstDayOfMonth(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] datetimes = month.split("-");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.YEAR,Integer.parseInt(datetimes[0]));
		firstDate.set(Calendar.MONTH,Integer.parseInt(datetimes[1])-1);
		firstDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		str = sdf.format(firstDate.getTime());
		return str;
	}
	
	
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param d
	 * @return
	 * @Description ��ȡָ���µ�����
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
	 * @date 2014��4��21��
	 * @param d
	 * @return
	 * @Description ��ȡָ�������
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
	 * @date 2014��4��21��
	 * @param date
	 * @return
	 * @Description �ж������ַ����Ƿ񳬹��˽���֮��
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
	 * @date 2014��5��23��
	 * @param dateTime ָ������
	 * @return
	 * @throws ParseException 
	 * @Description ��ȡָ�����ڵ�����һ
	 */
	public static String getMondayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//�����������,ȡ14��ǰ
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
	 * @date 2014��5��23��
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description ��ȡָ�����ڵ�������
	 */
	public static String getSundayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//��������ʱ������7��
		if(!"SUN".equals(DateUtil.getDayOfWeek(dateTime))){
			return DateUtil.getAfterDate(cal.getTime(), 7, "yyyy-MM-dd");
		}else{
			return sdf.format(cal.getTime());
		}
	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014��5��23��
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description �ж�ָ������Ϊ�����ڼ�
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
	 * @date 2014��5��23��
	 * @param dateTime
	 * @return
	 * @throws ParseException 
	 * @Description ��ȡָ�������ϸ���
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
	 * @date 2014��8��11��
	 * @param dateTime
	 * @param i
	 * @return
	 * @throws ParseException
	 * @Description ��ȡ��ǰʱ����¸��·���Ӽ�
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
	 * @date 2014��6��7��
	 * @return
	 * @throws ParseException 
	 * @Description ��ȡ�ڶ����賿����������
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
	 * @date 2014��6��25��
	 * @param n
	 * @return
	 * @Description ��ȡn���Ӻ��ʱ��
	 */
	public static Date getNMinutesLater(int n){
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*60*1000);
		return nMinutesLater;

	}
	
	/**
	 * @author zhangkun
	 * @date 2014��6��25��
	 * @param n
	 * @return
	 * @Description ��ȡn���Ӻ��ʱ��
	 */
	public static Date getNSecondsLater(int n){
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*1000);
		return nMinutesLater;

	}
	
	
	/**
	 * @author zhangkun
	 * @date 2014��6��25��
	 * @param n
	 * @return
	 * @Description ��ȡn���Ӻ��ʱ��
	 */
	public static String getNMinutesLaterString(int n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nowTime = new Date().getTime();
		Date nMinutesLater = new Date(nowTime + n*60*1000);
		return sdf.format(nMinutesLater);
	}
	
	/**
	 * @author admin
	 * @date 2014��11��4��
	 * @param oldDate ԭ����
	 * @param newDate ������
	 * @return
	 * @Description ��ȡ������������������ȥ����ǰ��
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
	 * @date 2015��4��7��
	 * @param length
	 * @return
	 * @Description TODO(ת����ʱ����)
	 */
	public static String parseTime(long length) {
		if(length <=0){
			return "����1����";
		}
		String s = "";
//		long sec = length%60;
		long min = length/60%60;
		long hour = length/3600;
		s = hour + "Сʱ" +min + "����";
		return s;
	}
	
	/**
	 * @author zhangkun
	 * @date 2014��6��7��
	 * @return
	 * @throws ParseException 
	 * @Description ��ȡ�ڶ����賿����������
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