package com.m.gis.springboot.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.m.gis.springboot.constants.Constants;

import java.util.Date;
import java.util.List;

/**
 * @Title: JsonUtil.java
 * @Package com.m.gis.springboot.common.utils
 * @Description: 封装json工具类
 * @author monkjavaer
 * @date 2017年8月26日 下午12:32:43
 * @version V1.0
 */
public class JsonUtils {

	public  static  final  SerializerFeature[] BEAN_TO_ARRAY_FEATURES = {
			SerializerFeature.BeanToArray,//标识是否采用属性值数组的方式输出
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse // Boolean字段如果为null，输出为false，而不是null
	};

	public  static  final SerializerFeature[] FEATURES = new SerializerFeature[BEAN_TO_ARRAY_FEATURES.length-1];

	static {
		System.arraycopy(BEAN_TO_ARRAY_FEATURES,1,FEATURES,0,BEAN_TO_ARRAY_FEATURES.length-1);
	}


	//转换为JsonObject对象
	public static JSONObject toJSONObject(String jsonString){
		return JSON.parseObject(jsonString);
	}
	
	public static JSONObject toJSONObject(Object object){
		return (JSONObject)JSON.toJSON(object);
	}
	
	public static JSONArray toJSONArray(String jsonString){
		return JSONArray.parseArray(jsonString);
	}
	
	public static JSONArray toJSONArray(Object object){
		return (JSONArray)JSONArray.toJSON(object);
	}
	
	public static JSONArray toDateFormatJSONArray(Object object){		
		String jsonString = toDateFormatJSONArrayString(object);
		return (JSONArray)JSONArray.parse(jsonString);
	}
	
	public static <T> T toBean(String jsonString,Class<T> clazz){
		return JSON.parseObject(jsonString, clazz);
	}
	
	public static <T> List<T> toBeanArray(String jsonString, Class<T> clazz){
		return JSON.parseArray(jsonString, clazz);
	}

	/**
	 * 转换为Json字符
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object,FEATURES);
	}

	public static String toDateFormatJSONString(Object object){
		return JSON.toJSONStringWithDateFormat(object, Constants.TIME_FORMAT[0],FEATURES);
	}

	/**
	 * 省略属性名，model转换为由属性值组成的数组，如[1001,"gaotie"]
	 * @param object
	 * @return
	 */
	public static String toBeanToArrayJSONString(Object object){
		return JSON.toJSONString(object,BEAN_TO_ARRAY_FEATURES);
	}

	public static String toJSONArrayString(Object object){
		return JSONArray.toJSONString(object,FEATURES);
	}
	
	public static String toDateFormatJSONArrayString(Object object){
		return JSONArray.toJSONStringWithDateFormat(object, Constants.TIME_FORMAT[0], FEATURES);
	}

	/**
	 * 获取指定key的值，返回字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject json, String key) {
		return getString(json,key,null);
	}

	/**
	 * 获取指定key的值，返回字符串，如果为null，返回指定的默认值
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject json, String key, String defaultValue) {
		if (json.get(key) == null) 
			return defaultValue;
		return json.getString(key);
	}

	/**
	 * 获取指定key的值，返回double
	 * @param json
	 * @param key
	 * @return double
	 */
	public static Double getDouble(JSONObject json, String key) {
		return getDouble(json,key,null);
	}

	/**
	 * 获取指定key的值，返回double
	 * @param json
	 * @param key
	 * @return double
	 */
	public static Double getDouble(JSONObject json, String key, Double defaultValue) {
		if (json.get(key) == null) 
			return defaultValue;
		return json.getDouble(key);
	}

	/**
	 * 获取指定key的值，返回int
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getInteger(JSONObject json, String key) {
		return getInteger(json,key,null);
	}

	/**
	 * 获取指定key的值，返回int，如果获取失败，返回默认值
	 * @param json
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInteger(JSONObject json, String key, Integer defaultValue) {
		if (json.get(key) == null) 
			return defaultValue;
		return json.getInteger(key);
	}


	/**
	 * 获取指定key的值，返回Date,如果获取失败，返回默认值
	 * @param json
	 * @param key
	 * @return
	 */
	public static Date getDate(JSONObject json, String key,Date defaultValue) {
		if (json.get(key) == null) 
			return defaultValue;
		return json.getDate(key);
	}
	
	
	/**
	 * 获取指定key的值，返回Date
	 * @param json
	 * @param key
	 * @return
	 */
	public static Date getDate(JSONObject json, String key) {
		return getDate(json,key,null);
	}

	
	public static JSONArray getArray(JSONObject json, String key, JSONArray defaultValue){
		if (json.get(key) == null) 
			return defaultValue;
		return json.getJSONArray(key);
	}
	
	public static JSONArray getArray(JSONObject json, String key){
		return getArray(json,key,null);
	}
	
	/**
	 * 
	 * @param jsonString
	 * @param typeRef 例如TypeReference<Map<String, User>>() {}，即可返回一个Map对象
	 * @return 
	 */
	public static <T> T toBeanType(String jsonString,TypeReference<T> typeRef){
		return JSON.parseObject(jsonString, typeRef);
	}
	
}

