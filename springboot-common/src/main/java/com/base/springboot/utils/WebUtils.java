package com.base.springboot.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @Title: WebUtils
 * @Package: com.base.springboot.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Date: 2019/8/8 14:11
 * @Version: V1.0
 */
public class WebUtils {
    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    private static final int BUFFER_SIZE = 1024;

    private WebUtils() {

    }

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static JSONObject getRequest(HttpServletRequest request) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(request.getInputStream(), BUFFER_SIZE);
            int contentLength = Integer.valueOf(request.getHeader("Content-Length"));
            byte[] bytes = new byte[contentLength];
            int readCount = 0;
            while (readCount < contentLength) {
                readCount += is.read(bytes, readCount, contentLength - readCount);
            }

            String requestJson = new String(bytes, "UTF-8");
            JSONObject jsonObj = JsonUtils.toJSONObject(requestJson);
            logger.info(String.format("--> URL={%s}, PARAMS={%s}",request.getRequestURI(), jsonObj));
            return jsonObj;
        } catch (IOException e) {
            logger.error("error happens when get request, " + e.toString());
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    /**
     *
     * <b>方法说明：</b>
     * <ul>
     * 提取Http请求中的查询参数
     * </ul>
     * @param request Http请求
     * @param clazz 待提取的参数类型
     * @return T 提取出来的参数对象
     */
    public static <T> T extractParams(HttpServletRequest request, Class<T> clazz) {
        try {
            T queryVO = clazz.newInstance();
            for (Field field : queryVO.getClass().getDeclaredFields()) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue; //序列号不取
                }

                String paramVal = request.getParameter(field.getName());
                if (paramVal != null) {
                    field.setAccessible(true);
                    if (field.getGenericType().toString().equals("boolean") || field.getGenericType().toString().equals("class java.lang.Boolean")) {
                        field.set(queryVO, Boolean.parseBoolean(paramVal));
                    } else if (field.getGenericType().toString().equals("char") || field.getGenericType().toString().equals("class java.lang.Character")) {
                        field.set(queryVO, paramVal.charAt(0));
                    } else if (field.getGenericType().toString().equals("byte") || field.getGenericType().toString().equals("class java.lang.Byte")) {
                        field.set(queryVO, paramVal.getBytes()[0]);
                    } else if (field.getGenericType().toString().equals("short") || field.getGenericType().toString().equals("class java.lang.Short")) {
                        field.set(queryVO, Short.parseShort(paramVal));
                    } else if (field.getGenericType().toString().equals("int") || field.getGenericType().toString().equals("class java.lang.Integer")) {
                        field.set(queryVO, Integer.parseInt(paramVal));
                    } else if (field.getGenericType().toString().equals("long") || field.getGenericType().toString().equals("class java.lang.Long")) {
                        field.set(queryVO, Long.parseLong(paramVal));
                    } else if (field.getGenericType().toString().equals("float") || field.getGenericType().toString().equals("class java.lang.Float")) {
                        field.set(queryVO, Float.parseFloat(paramVal));
                    } else if (field.getGenericType().toString().equals("double") || field.getGenericType().toString().equals("class java.lang.Double")) {
                        field.set(queryVO, Double.parseDouble(paramVal));
                    } else if (field.getGenericType().toString().equals("class java.lang.String")) {
                        field.set(queryVO, paramVal);
                    } else if (field.getGenericType().toString().equals("class java.util.Date")) {
                        field.set(queryVO, DateTimeUtils.getDate(paramVal));
                    }
                }
            }

            return queryVO;
        } catch (Exception e) {
            logger.error("error happens when extractParams, " + e.toString());
            throw new RuntimeException("Exception when trying to extract parameters from http redquest", e);
        }
    }
}
