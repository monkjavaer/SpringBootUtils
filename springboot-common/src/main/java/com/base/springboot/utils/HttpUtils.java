//package com.base.springboot.utils;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.protocol.HTTP;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * @Title: HttpUtils
// * @Package: com.base.springboot.utils
// * @Description: HTTP工具类
// * @Author: monkjavaer
// * @Date: 2019/8/8 14:08
// * @Version: V1.0
// */
//public class HttpUtils {
//    private static int TIMEOUT = 5000;
//
//    private static final String APPLICATION_JSON = "application/json";
//
//    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
//
//    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
//
//    private HttpUtils() {
//
//    }
//
//    /**
//     * POST方式提交请求
//     *
//     * @param url 请求地址
//     * @param json JSON格式请求内容
//     * @throws IOException
//     */
//    public static String doPost(String url, String json) throws IOException {
//        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
//        StringEntity stringEntity = new StringEntity(json, "UTF-8");
//        stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
//        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
//        httpPost.setEntity(stringEntity);
//        httpPost.setConfig(defaultRequestConfig);
//        CloseableHttpResponse response = null;
//        BufferedReader input = null;
//        String responseContent = "";
//        try {
//            response = httpClient.execute(httpPost);
//            int status = response.getStatusLine().getStatusCode();
//            logger.debug("response status: " + status);
//            if (status >= 200 && status < 300) {
//                HttpEntity entity = response.getEntity();
//                if (null == entity) {
//                    logger.debug("response body is empty.");
//                } else {
//                    input = new BufferedReader(new InputStreamReader(entity.getContent()));
//                    StringBuilder sb = new StringBuilder();
//                    String line = null;
//                    while ((line = input.readLine()) != null) {
//                        sb.append(line);
//                    }
//                    responseContent = StringUtils.trim(sb.toString());
//                }
//            } else {
//                throw new ClientProtocolException("Unexpected response status: " + status);
//            }
//        } catch (ClientProtocolException e) {
//            throw new ClientProtocolException(e);
//        } catch (IOException e) {
//            throw new IOException(e);
//        } finally {
//            IOUtils.closeQuietly(input);
//            IOUtils.closeQuietly(response);
//            IOUtils.closeQuietly(httpClient);
//        }
//        return responseContent;
//    }
//}
