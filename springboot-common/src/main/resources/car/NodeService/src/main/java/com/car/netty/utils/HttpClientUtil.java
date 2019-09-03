package com.base.springboot.car.NodeService.src.main.java.com.car.netty.utils;

import com.car.netty.enums.UnvEnum;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP 简单封装
 *
 * @ClassName: HttpClientUtil
 * @Description:
 */
public class HttpClientUtil
{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * Http的客户端
     */
    private static CloseableHttpClient httpClient;
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    private static HttpClientUtil instance = null;

    /**
     * 构造函数
     */
    private HttpClientUtil()
    {
    }

    /**
     * HttpClientUtil 单例
     *
     * @return
     */
    public static HttpClientUtil getInstance()
    {
        if (instance == null)
        {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     */
    public String sendHttpPost(String httpUrl)
    {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params)
    {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try
        {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }


    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params
     *  参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params, String contentType)
    {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try
        {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param params
     * 参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPostLive(String params, String contentType, HttpPost httpPost)
    {
        String strBack = null;
        try
        {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            strBack = sendHttpPostKeepLive(httpPost);
        }
        catch (IOException e)
        {
            httpClient = null;
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            // 创建默认的httpClient实例.
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        }
        return strBack;
    }

    /**
     * 发送 post请求 短连接
     * @param params
     * 参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPostShort(String params, String contentType, HttpPost httpPost)
    {
        String strBack = null;
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        stringEntity.setContentType(contentType);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Connection", "close");
        strBack = sendHttpPost(httpPost);
        return strBack;
    }


    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps)
    {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet())
        {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists)
    {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (String key : maps.keySet())
        {
            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
        }
        for (File file : fileLists)
        {
            FileBody fileBody = new FileBody(file);
            meBuilder.addPart("files", fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }


    /**
     * 发送Post请求
     * 长连接
     * @param httpPost
     * @return
     */
    private String sendHttpPostKeepLive(HttpPost httpPost) throws IOException
    {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            if(null == httpClient)
            {
                // 创建默认的httpClient实例.
                httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            }
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");

        }
        finally
        {
            // 关闭连接,释放资源
            if (response != null)
            {
                response.close();
            }
        }

        return responseContent;
    }


    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        }
        catch (ClientProtocolException ce)
        {
            ce.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + ce.getMessage(), ce);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        }

        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            }
        }
        return responseContent;
    }

    /**
     * 发送 get请求获取字符串
     *
     * @param httpUrl
     */
    public String sendHttpGetS(String httpUrl)
    {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGetS(httpGet);
    }

    /**
     * 发送 get请求获取字节
     *
     * @param httpUrl
     */
    public byte[] sendHttpGetB(String httpUrl)
    {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGetB(httpGet);
    }

    /**
     * 发送 get请求Https，获取字符串
     *
     * @param httpUrl
     */
    public String sendHttpsGet(String httpUrl)
    {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private String sendHttpGetS(HttpGet httpGet)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        }
        catch (ClientProtocolException ce)
        {
            ce.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + ce.getMessage(), ce);
        }
        catch (Exception e)
        {
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private byte[] sendHttpGetB(HttpGet httpGet)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        byte[] responseContentByte = null;
        try
        {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            // 配置
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContentByte = EntityUtils.toByteArray(entity);
        }
        catch (ClientProtocolException ce)
        {
            ce.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + ce.getMessage(), ce);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            }
        }
        return responseContentByte;
    }

    /**
     * 发送Get请求Https
     *
     * @param httpGet
     * @return
     */
    private String sendHttpsGet(HttpGet httpGet)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader
                    .load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                LOGGER.error(UnvEnum.UNV_ERROR + e.getMessage(), e);
            }
        }
        return responseContent;
    }

    /**
     * 测试函数
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        HttpClientUtil cc = HttpClientUtil.getInstance();
        cc.sendHttpGetB("http://208.208.121.200:8083/5665789001387212804/2016/05/30/19/a4/19371833402.jpg?fid=707962");
        System.out.println(1111);
    }
}



