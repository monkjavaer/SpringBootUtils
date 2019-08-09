package com.car.base.common.httpclient;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by monkjavaer on 2017/11/20 0020.
 */
public class HttpUtil {

    /**
     *
     * @param url    post地址
     * @param jsonStr json格式字符串参数，如果没有，传null
     * @return
     */
    /*public static JSONObject sendPost(String url,String jsonStr){

        final Object[] arr = new Object[2];
        arr[0] = url;
        if (StringUtils.isNotBlank(jsonStr)) {
            arr[1] = jsonStr;
        }

        BaseHttpClient hc = new HttpSendUtil();
        HttpClientResultBean bean = hc.getResult(arr, "post");
        Object res = bean.getResult();
        JSONObject jsonObject=JSONObject.fromObject(res);
        return jsonObject;
    }*/

    /**
     *
     * @param url    post地址
     * @param jsonStr json格式字符串参数，如果没有，传null
     * @return
     */
    public static Object sendPost(String url,String jsonStr){

        final Object[] arr = new Object[2];
        arr[0] = url;
        if (StringUtils.isNotBlank(jsonStr)) {
            arr[1] = jsonStr;
        }

        BaseHttpClient hc = new HttpSendUtil();
        HttpClientResultBean bean = hc.getResult(arr, "post");
        Object res = bean.getResult();
        //JSONObject jsonObject=JSONObject.fromObject(res);
        return res;
    }

    /*public static JSONObject sendGet(String url,String param){

        final Object[] arr = new Object[2];
        arr[0] = url;
        if (StringUtils.isNotBlank(param)) {
            arr[1] = param;
        }

        BaseHttpClient hc = new HttpSendUtil();
        HttpClientResultBean bean = hc.getResult(arr, "get");
        Object res = bean.getResult();
        JSONObject jsonObject=JSONObject.fromObject(res);
        return jsonObject;
    }*/

    public static JSONObject sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = null;
            if (StringUtils.isNotBlank(param)) {
                urlNameString = url + "?" + param;
            }else {
                urlNameString = url ;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("contentType", "UTF-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        JSONObject jsonObject=JSONObject.fromObject(result);
        return jsonObject;
    }
}
