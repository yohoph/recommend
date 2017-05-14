/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014��4��21��
 * Id: StringUtil.java,v 1.0 2014��4��21�� ����2:23:51 admin
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
 * @date 2014��4��21�� ����2:29:30
 * @Description ����String�Ĺ�����
 */
public class StringUtil {
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @return
	 * @Description ��ò�����ܵ�UUID
	 */
	public static String getUUID(){
		return getBaseUUID().replace("-", "");
	}
	
	/**
	 * @author admin
	 * @date 2015��3��24��
	 * @return
	 * @Description ��ȡ��������UUID
	 */
	public static String getBaseUUID(){
		return UUID.randomUUID().toString();
	}

	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param str
	 * @return
	 * @Description ����ַ������˵ĵ�/˫����
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
	 * @date 2014��4��21��
	 * @param regexStr ������ʽ
	 * @param content ��ƥ���ַ���
	 * @param group ƥ�������ڵķ���
	 * @return ƥ��Ľ��
	 * @Description ����ƥ��һ�����
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
	 * @date 2014��4��21��
	 * @param regexStr ������ʽ
	 * @param content ��ƥ����ַ���
	 * @return ƥ��Ķ�����
	 * @Description ����ƥ�������
	 */
	public static List<String> regexMatchMore(String regexStr, String content) {
		return regexMatchMore(regexStr, content, 0);
	}

	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param regexStr ������ʽ
	 * @param content ��ƥ����ַ���
	 * @param group �ڲ�������
	 * @return ƥ��Ķ�����
	 * @Description ����ƥ�������
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
	 * ������SQLע���ַ���
	 */
	public static String[] SQL_INJECTION_STRING = { "select", "union",
			"update", "delete", "insert", "into", "'", "creat", "and", "where",
			"1=1", "1=2", "/*", "//", "\\", "///", "/", "\\\\", "drop","--",
			"lock table", "grant", "ascii", "count", "chr", "mid", "master",
			"truncate" };

	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param obj
	 * @return
	 * @Description ��һ������ת����json��Ĭ��ʱ��yyyy-MM-dd
	 */
	public static String parseJson(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//��ת��û��@Exposeע����ֶ�
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param obj
	 * @param dateFormat
	 * @return
	 * @Description ��һ������ת����json,�ṩʱ��ķ�ʽ
	 */
	public static String parseJson(Object obj ,String dateFormat){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//��ת��û��@Exposeע����ֶ�
		builder.setDateFormat(dateFormat);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param obj
	 * @return
	 * @Description �ṩδ��@Expose��ע��Ķ���json����Ĭ��ʱ��yyyy-MM-dd
	 */
	public static String parseJsonNoExpose(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestampʱ���ʽ��Ҫ��ʱ����
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
	 * @date 2014��4��21��
	 * @param obj
	 * @param dateFormat
	 * @return
	 * @Description �ṩδ��@Expose��ע��Ķ���json�����ṩʱ���Զ���
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
	 * @date 2014��4��21��
	 * @param obj
	 * @param dateFormat
	 * @param strategies
	 * @return
	 * @Description ���ӿ����õ����л�������
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
	 * @date 2014��4��21��
	 * @param obj
	 * @return
	 * @Description ���л��ַ�����ʱ���ʱ���ʽ
	 */
	public static String parseJsonSetDate(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();//��ת��û��@Exposeע����ֶ�
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestampʱ���ʽ��Ҫ��ʱ����
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
	 * @date 2014��4��21��
	 * @param obj
	 * @return
	 * @Description ���л��ַ�����ʱ���ʱ���ʽ
	 */
	public static String parseJsonSetDateNoExpose(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>(){//Timestampʱ���ʽ��Ҫ��ʱ����
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
	 * @date 2014��4��25��
	 * @return �ɹ���Ϣͷ��json
	 * @Description ����һ���ɹ���Ϣ��JSONObject����
	 */
	public static JSONObject getSUCCObj(){
		JSONObject result = new JSONObject();
		result.put("code", 0);
		return result;
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��25��
	 * @return ʧ����Ϣͷ��json
	 * @Description ����һ��ʧ����Ϣ��JSONObject����
	 */
	public static JSONObject getFAILDObj(){
		JSONObject result = new JSONObject();
		result.put("code", -1);
		return result;
	}
	
	/**
	 * @author admin
	 * @date 2014��12��18��
	 * @param msg
	 * @param method
	 * @return
	 * @Description �����첽������
	 */
	public static String getAsynResult(String msg , String method){
		JSONObject result = new JSONObject();
		result.put("method", method);
		result.put("result", new JSONObject(msg));
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��5��7��
	 * @return
	 * @Description ����һ����Ĭ�ϵĳɹ��ظ�ͷ
	 */
	public static String getSuccResult(){
		return getSuccResult(null);
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��28��
	 * @param json
	 * @return
	 * @Description ����һ�����ɹ���Ϣ���з���body,����Exposeע��
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
	 * @date 2014��4��28��
	 * @param json
	 * @return
	 * @Description ����һ�����ɹ���Ϣ���з���body
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
	 * @date 2014��4��28��
	 * @param reason ����ԭ����ʾ
	 * @return ���һ���������ʾjson
	 * @Description ��ô���ķ��ؽ��
	 */
	public static String getFaildResult(String reason){
		JSONObject result = new JSONObject();
		result.put("code", -1);
		result.put("msg", reason);
		return result.toString();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��28��
	 * @param reason ����ԭ����ʾ
	 * @return ���һ���������ʾjson
	 * @Description ����Ѵ��ڴ���ķ��ؽ��
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
	 * @date 2014��8��20��
	 * @return
	 * @Description �����ݽ����
	 */
	public static String getEmptyResult(){
		JSONObject result = new JSONObject();
		result.put("code", 1);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��11��1��
	 * @return
	 * @Description ���ط��������ﵽ���ޣ�������ʾ
	 */
	public static String getFullResult(){
		JSONObject result = new JSONObject();
		result.put("code", -3);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��12��5��
	 * @return
	 * @Description ���˽��Լ�Ȩʧ��
	 */
	public static String getNoRightResult(){
		JSONObject result = new JSONObject();
		result.put("code", -4);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��12��5��
	 * @return
	 * @Description δ��¼
	 */
	public static String getNoLoginResult(){
		JSONObject result = new JSONObject();
		result.put("code", -5);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��7��21��
	 * @return
	 * @Description ��ȡ���Խ��
	 */
	public static String getRetryResult(){
		JSONObject result = new JSONObject();
		result.put("code", -2);
		return result.toString();
	}
	
	/**
	 * @author admin
	 * @date 2014��5��29��
	 * @param json
	 * @param clazz
	 * @return
	 * @Description jsonת������
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
	 * @date 2014��4��25��
	 * @param location ��ת·��
	 * @param json ���ݲ���
	 * @return ·����ת���м�ҳ��
	 * @Description ���һ��ҳ����ת�ķ����������ݲ���
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
		builder.append("ҳ����ת�С���");
		return builder.toString();
	}
	/**
	 * 
	 * @author admin
	 * @date 2014��5��14��
	 * @param str Ҫ��֤���ַ���
	 * @return
	 * @Description �ж�Ҫ��֤���ַ����ǿ���ת����ʱ���ʽ
	 */
	public static boolean getFormat(String str){
		boolean convertSuccess=true;
		 // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        try {
		         // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤����
		           format.setLenient(false);
		           format.parse(str);
		        } catch (ParseException e) {
	               // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
		           convertSuccess=false;
		        }
				return convertSuccess; 
	}
	
     /**
      * 
      * @author admin
      * @date 2014��5��14��
      * @param str Ҫ��֤���ַ���
      * @param str1 Ҫ��֤���ַ���
      * @return
      * @Description �ж�Ҫ��֤���ַ����ǿ���ת����ʱ���ʽ
      */
	public static boolean getFormat(String str,String str1){
		boolean convertSuccess=true;
		 // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        try {
		         // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤����
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
	 * @date 2014��5��14��
	 * @param str Ҫ��֤���ַ���
	 * @return
	 * @Description �ж�Ҫ��֤���ַ����ǿ���ת����ʱ���ʽ
	 */
	public static boolean getFormatDate(String str){
		boolean convertSuccess=true;
		 // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		         // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤����
		           format.setLenient(false);
		           format.parse(str);
		        } catch (ParseException e) {
	               // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
		           convertSuccess=false;
		        }
				return convertSuccess; 
	}
	
	/**
	 * @author admin
	 * @date 2014��6��10��
	 * @param path
	 * @return
	 * @Description ��ȡclasspath�����·��
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
	 * @date 2014��11��5��
	 * @param content ����ļ�����
	 * @return
	 * @Description ����Ƿ��п�վ�����ַ���
	 */
	public static boolean checkXSS(String content){
		content = content.replaceAll(" |\\n|\\s|\t", "");//ȥ�����пո�ͻ��з�
		String reg = ".*javascript.*|.*script.*|.*vbscript.*|.*onerror.*";
		String context = regexMatchOne(reg, content, 0);
		if(StringUtils.isEmpty(context)){
			return true;
		}
		return false;
	}
	
	/**
	 * @author admin
	 * @date 2014��11��14��
	 * @param reg ƥ��������ʽ
	 * @param context ƥ���ַ���
	 * @param matchVal ƥ������ֵ
	 * @return
	 * @Description �����滻�ļ�
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
	 * @date 2014��12��16��
	 * @param content
	 * @return
	 * @Description ƥ���Ƿ�HTTP����
	 */
	public static String matchHTTP(String content){
		String reg = "^http://.*?/";
		return regexMatchOne(reg, content, 0);
	}
	
	/**
	 * @author admin
	 * @date 2015��5��7��
	 * @param content
	 * @return
	 * @Description ��֤�Ƿ�����
	 */
	public static boolean matchMail(String content){
		String reg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern regex = Pattern.compile(reg);    
		Matcher matcher = regex.matcher(content);    
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
}

