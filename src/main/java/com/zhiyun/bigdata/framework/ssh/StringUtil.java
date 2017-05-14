/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014年4月21日
 * Id: StringUtil.java,v 1.0 2014年4月21日 下午2:23:51 admin
 */
package com.zhiyun.bigdata.framework.ssh;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.zhiyun.bigdata.framework.utils.DateUtil;

/**
 * @ClassName StringUtil
 * @author yehao
 * @date 2014年4月21日 下午2:29:30
 * @Description 处理String的工具类
 */
public class StringUtil {
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @return
	 * @Description 获得不带横杠的UUID
	 */
	public static String getUUID(){
		return getBaseUUID().replace("-", "");
	}
	
	/**
	 * @author admin
	 * @date 2015年3月24日
	 * @return
	 * @Description 获取带基础的UUID
	 */
	public static String getBaseUUID(){
		return UUID.randomUUID().toString();
	}

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param str
	 * @return
	 * @Description 清除字符串两端的单/双引号
	 */
	public static String clearQuote(String str) {
		str = str.trim();
		if ((str.startsWith("\"") && str.endsWith("\""))
				|| (str.startsWith("'") && str.endsWith("'"))) {
			return str.substring(1, str.length() - 1);
		} else {
			return str;
		}
	}

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param regexStr 正则表达式
	 * @param content 待匹配字符串
	 * @param group 匹配结果所在的分组
	 * @return 匹配的结果
	 * @Description 正则匹配一个结果
	 */
	public static String regexMatchOne(String regexStr, String content,
			int group) {
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(content);
		String str = "";
		if (m.find()) {
			str = m.group(group).trim();
		}
		return str;
	}

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param regexStr 正则表达式
	 * @param content 待匹配的字符串
	 * @return 匹配的多个结果
	 * @Description 正则匹配多个结果
	 */
	public static List<String> regexMatchMore(String regexStr, String content) {
		return regexMatchMore(regexStr, content, 0);
	}

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param regexStr 正则表达式
	 * @param content 待匹配的字符串
	 * @param group 内部分组编号
	 * @return 匹配的多个结果
	 * @Description 正则匹配多个结果
	 */
	public static List<String> regexMatchMore(String regexStr, String content,
			int group) {
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(content);
		List<String> list = new ArrayList<String>();
		while (m.find()) {
			String str = m.group(group).trim();
			list.add(str);
		}
		return list;
	}

	/**
	 * 常见的SQL注入字符串
	 */
	public static String[] SQL_INJECTION_STRING = { "select", "union",
			"update", "delete", "insert", "into", "'", "creat", "and", "where",
			"1=1", "1=2", "/*", "//", "\\", "///", "/", "\\\\", "drop","--",
			"lock table", "grant", "ascii", "count", "chr", "mid", "master",
			"truncate" };

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @return
	 * @Description 将一个对象转化成json，默认时间yyyy-MM-dd
	 */
	public static String parseJson(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//不转换没有@Expose注解的字段
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @param dateFormat
	 * @return
	 * @Description 将一个对象转化成json,提供时间的方式
	 */
	public static String parseJson(Object obj ,String dateFormat){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//不转换没有@Expose注解的字段
		builder.setDateFormat(dateFormat);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @return
	 * @Description 提供未按@Expose的注解的对象json化，默认时间yyyy-MM-dd
	 */
	public static String parseJsonNoExpose(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestamp时间格式需要带时分秒
			@Override
			public JsonElement serialize(Timestamp date, Type type,
					JsonSerializationContext arg2) {
					return new JsonPrimitive(DateUtil.getDate(date));
			}});
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @param dateFormat
	 * @return
	 * @Description 提供未按@Expose的注解的对象json化，提供时间自定义
	 */
	public static String parseJsonNoExpose(Object obj , String dateFormat){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.setDateFormat(dateFormat);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @param dateFormat
	 * @param strategies
	 * @return
	 * @Description 增加可配置的序列化方法的
	 */
	public static String parseJsonExclusionStrategies(Object obj , String dateFormat , ExclusionStrategy... strategies){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(dateFormat);
		builder.setExclusionStrategies(strategies);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @return
	 * @Description 序列化字符串的时候带时间格式
	 */
	public static String parseJsonSetDate(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//不转换没有@Expose注解的字段
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestamp时间格式需要带时分秒
			@Override
			public JsonElement serialize(Timestamp date, Type type,
					JsonSerializationContext arg2) {
					return new JsonPrimitive(DateUtil.getDate(date));
			}});
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param obj
	 * @return
	 * @Description 序列化字符串的时候带时间格式
	 */
	public static String parseJsonSetDateNoExpose(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestamp时间格式需要带时分秒
			@Override
			public JsonElement serialize(Timestamp date, Type type,
					JsonSerializationContext arg2) {
					return new JsonPrimitive(DateUtil.getDate(date));
			}});
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月25日
	 * @return 成功信息头的json
	 * @Description 生成一个成功信息的JSONObject对象
	 */
	public static JSONObject getSUCCObj(){
		JSONObject result = new JSONObject();
		result.put("code", 0);
		return result;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月25日
	 * @return 失败信息头的json
	 * @Description 生成一个失败信息的JSONObject对象
	 */
	public static JSONObject getFAILDObj(){
		JSONObject result = new JSONObject();
		result.put("code", -1);
		return result;
	}
	
	/**
	 * @author admin
	 * @date 2014年12月18日
	 * @param msg
	 * @param method
	 * @return
	 * @Description 生成异步信令结果
	 */
	public static String getAsynResult(String msg , String method){
		JSONObject result = new JSONObject();
		result.put("method", method);
		result.put("result", new JSONObject(msg));
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年5月7日
	 * @return
	 * @Description 返回一个带默认的成功回复头
	 */
	public static String getSuccResult(){
		return getSuccResult(null);
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月28日
	 * @param json
	 * @return
	 * @Description 返回一个带成功信息并有返回body,忽略Expose注解
	 */
	public static String getSuccResultNoExpose(Object obj){
		JSONObject result = new JSONObject();
		result.put("code", 0);
		if(obj != null){
			if(obj instanceof String) {
				try {
					JSONObject object = new JSONObject(obj.toString());
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(obj.toString());
					result.put("body", array);
				}
			} else if (obj instanceof JSONObject) {
				result.put("body", obj);
			} else if (obj instanceof JSONArray ) {
				result.put("body", obj);
			} else {
				String json = parseJsonSetDateNoExpose(obj);
				try {
					JSONObject object = new JSONObject(json);
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(json);
					result.put("body", array);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月28日
	 * @param json
	 * @return
	 * @Description 返回一个带成功信息并有返回body
	 */
	public static String getSuccResult(Object obj){
		JSONObject result = new JSONObject();
		result.put("code", 0);
		if(obj != null){
			if(obj instanceof String) {
				try {
					JSONObject object = new JSONObject(obj.toString());
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(obj.toString());
					result.put("body", array);
				}
			} else if (obj instanceof JSONObject) {
				result.put("body", obj);
			} else if (obj instanceof JSONArray ) {
				result.put("body", obj);
			} else {
				String json = parseJsonSetDate(obj);
				try {
					JSONObject object = new JSONObject(json);
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(json);
					result.put("body", array);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月28日
	 * @param reason 错误原因，提示
	 * @return 获得一个错误的提示json
	 * @Description 获得错误的返回结果
	 */
	public static String getFaildResult(String reason){
		JSONObject result = new JSONObject();
		result.put("code", -1);
		result.put("msg", reason);
		return result.toString();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月28日
	 * @param reason 错误原因，提示
	 * @return 获得一个错误的提示json
	 * @Description 获得已存在错误的返回结果
	 */
	public static String getExistResult(Object obj){
		JSONObject result = new JSONObject();
		result.put("code", 2);
		if(obj != null){
			if(obj instanceof String) {
				try {
					JSONObject object = new JSONObject(obj.toString());
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(obj.toString());
					result.put("body", array);
				}
			} else if (obj instanceof JSONObject) {
				result.put("body", obj);
			} else if (obj instanceof JSONArray ) {
				result.put("body", obj);
			} else {
				String json = parseJsonSetDate(obj);
				try {
					JSONObject object = new JSONObject(json);
					result.put("body", object);
				} catch (JSONException e) {
					JSONArray array = new JSONArray(json);
					result.put("body", array);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年8月20日
	 * @return
	 * @Description 空数据结果集
	 */
	public static String getEmptyResult(){
		JSONObject result = new JSONObject();
		result.put("code", 1);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年11月1日
	 * @return
	 * @Description 返回房间容量达到上限，错误提示
	 */
	public static String getFullResult(){
		JSONObject result = new JSONObject();
		result.put("code", -3);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年12月5日
	 * @return
	 * @Description 踢人禁言鉴权失败
	 */
	public static String getNoRightResult(){
		JSONObject result = new JSONObject();
		result.put("code", -4);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年12月5日
	 * @return
	 * @Description 未登录
	 */
	public static String getNoLoginResult(){
		JSONObject result = new JSONObject();
		result.put("code", -5);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年7月21日
	 * @return
	 * @Description 获取重试结果
	 */
	public static String getRetryResult(){
		JSONObject result = new JSONObject();
		result.put("code", -2);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014年5月29日
	 * @param json
	 * @param clazz
	 * @return
	 * @Description json转换对象
	 */
	public static Object jsonToObj(String json ,Class<?> clazz){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		Object obj = null;
		try {
			obj = gson.fromJson(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return obj;
	}
	
	/**
	 * @author admin
	 * @date 2014年4月25日
	 * @param location 跳转路径
	 * @param json 传递参数
	 * @return 路径跳转的中间页面
	 * @Description 输出一个页面跳转的方法并带传递参数
	 */
	public static String locationScript(String location , JSONObject json){
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">");
		builder.append("window.location.href = \""+location+"");
		if(json != null){
			builder.append("?");
			for (Object key : json.keySet()) {
				builder.append(key + "=" + json.get(key.toString()).toString());
				builder.append("&");
			}
		}
		builder.append("\";");
		builder.append("</script>");
		builder.append("页面跳转中……");
		return builder.toString();
	}
	/**
	 * 
	 * @author admin
	 * @date 2014年5月14日
	 * @param str 要验证的字符串
	 * @return
	 * @Description 判断要验证的字符串是可以转化成时间格式
	 */
	public static boolean getFormat(String str){
		boolean convertSuccess=true;
		 // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        try {
		         // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
		           format.setLenient(false);
		           format.parse(str);
		        } catch (ParseException e) {
	               // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
		           convertSuccess=false;
		        }
				return convertSuccess; 
	}
	
     /**
      * 
      * @author admin
      * @date 2014年5月14日
      * @param str 要验证的字符串
      * @param str1 要验证的字符串
      * @return
      * @Description 判断要验证的字符串是可以转化成时间格式
      */
	public static boolean getFormat(String str,String str1){
		boolean convertSuccess=true;
		 // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        try {
		         // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
		           format.setLenient(false);
		           format.parse(str);
		           format.parse(str1);
		        } catch (ParseException e) {
		           convertSuccess=false;
		        }
				return convertSuccess; 
	} 
	
	/**
	 * 
	 * @author admin
	 * @date 2014年5月14日
	 * @param str 要验证的字符串
	 * @return
	 * @Description 判断要验证的字符串是可以转化成时间格式
	 */
	public static boolean getFormatDate(String str){
		boolean convertSuccess=true;
		 // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		         // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
		           format.setLenient(false);
		           format.parse(str);
		        } catch (ParseException e) {
	               // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
		           convertSuccess=false;
		        }
				return convertSuccess; 
	}
	
	/**
	 * @author admin
	 * @date 2014年6月10日
	 * @param path
	 * @return
	 * @Description 获取classpath下面的路径
	 */
	public static String readPath(String path){
		String filepath = null;
		try {
			filepath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + path;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return path;
		};
		if(StringUtils.isNotEmpty(filepath)){
			return filepath;
		}
		return path;
	}
	
	/**
	 * @author admin
	 * @date 2014年11月5日
	 * @param content 检查文件正文
	 * @return
	 * @Description 检查是否有跨站攻击字符串
	 */
	public static boolean checkXSS(String content){
		content = content.replaceAll(" |\\n|\\s|\t", "");//去除所有空格和换行符
		String reg = ".*javascript.*|.*script.*|.*vbscript.*|.*onerror.*";
		String context = regexMatchOne(reg, content, 0);
		if(StringUtils.isEmpty(context)){
			return true;
		}
		return false;
	}
	
	/**
	 * @author admin
	 * @date 2014年11月14日
	 * @param reg 匹配正则表达式
	 * @param context 匹配字符串
	 * @param matchVal 匹配数据值
	 * @return
	 * @Description 批量替换文件
	 */
	public static String replaceAll(String reg , String context , String matchVal){
		Matcher matcher = Pattern.compile(reg).matcher(context);
		while (matcher.find()) {
			context = context.replace(matcher.group(), matchVal);
		}
		return context;
	}
	
	/**
	 * @author admin
	 * @date 2014年12月16日
	 * @param content
	 * @return
	 * @Description 匹配是否HTTP链接
	 */
	public static String matchHTTP(String content){
		String reg = "^http://.*?/";
		return regexMatchOne(reg, content, 0);
	}
	
	/**
	 * @author admin
	 * @date 2015年5月7日
	 * @param content
	 * @return
	 * @Description 验证是否邮箱
	 */
	public static boolean matchMail(String content){
		String reg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern regex = Pattern.compile(reg);    
		Matcher matcher = regex.matcher(content);    
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
}

