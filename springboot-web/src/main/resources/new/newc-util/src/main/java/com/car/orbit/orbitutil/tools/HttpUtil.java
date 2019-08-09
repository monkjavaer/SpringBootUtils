package com.car.orbit.orbitutil.tools;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * java模拟HTTP请求工具类
 * @author zks
 **/
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static int connectTimeOut = 100;
    private static int readTimeout = 100;
    private static int writeTimeout = 100;

    /**
     * @Description json方式发送Post请求
     * @Author zks
     */
    public static String sendPost(String url, JSONObject param) throws IOException {
        return sendPost(url,JsonUtils.toJSONString(param));
    }

    /**
     * @Description json方式发送Post请求
     * @Author zks
     * @param param，传入已经转换成json的对象
     */
    public static String sendPost(String url, String param) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, param);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }



    /**
     * @Description 自定义RequestBody实现流作为参数，发送Post请求
     * @Author zks
     * 注意 使用RequestBody上传文件时，并没有实现断点续传的功能
     * 可以使用这种方法结合RandomAccessFile类实现断点续传的功能，但是这里没有实现
     */
    public static String sendStreamPost(String url, InputStream is) throws IOException {
        /**
         * @Description 通过RequestBody类以及子类作为post方法的参数，我们这里自定义一个类，继承RequestBody，实现流的上传
         * @Author zks
         */
        RequestBody body = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            /**
             * @Description body对象重写了write方法，其中bufferedSink对象作为OKio包中的输出流，有write方法。使用这个方法我们可以实现上传流的功能
             * @param bufferedSink
             * @throws IOException
             * @Author zks
             */
            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                byte[] buffer = new byte[1024*8];
                if(is.read(buffer) != -1){
                    bufferedSink.write(buffer);
                }
            }
        };
        //创建OkHttpClient对象。
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .build();
        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;

    }


    public static String sendGet(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            return response.body().string();

    }


}
