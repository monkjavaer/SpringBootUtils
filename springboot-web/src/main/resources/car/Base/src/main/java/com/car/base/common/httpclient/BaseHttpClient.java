package com.car.base.common.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by monkjavaer on 17-9-1.
 */
public abstract class BaseHttpClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String reqParam;
    private HttpClientResultBean result = new HttpClientResultBean();
    private String resResult;
    private String type;
    private Cookie sessionCookie = null;
    public static final String POST = "POST";
    public static final String GET = "GET";

    public BaseHttpClient() {
    }

    protected abstract String bulidParam(Object[] var1);

    protected abstract HttpClientResultBean formatResults(HttpClientResultBean var1);

    protected abstract String getUrl();

    public HttpClientResultBean getResult(Object[] arr, String type) {
        this.setType(type);

        try {
            this.reqParam = this.bulidParam(arr);

            this.send(this.getUrl(), this.reqParam);

            if (this.resResult != null && this.resResult != "") {
                this.resResult = URLDecoder.decode(this.resResult, "UTF-8");
            }

        } catch (Exception var4) {
            this.logger.error(var4.getMessage(), var4);
        }

        return this.formatResults(this.result);
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    private HttpMethod getMethod(String url, String param) throws IOException {
        GetMethod get = new GetMethod(url + "?" + param);
        get.setRequestHeader("Connection", "close");
        get.addRequestHeader("Accept", "text/plain");
        get.releaseConnection();
        return get;
    }

    private HttpMethod postMethod(String url, String param) throws IOException {
        PostMethod post = new PostMethod(url);
        if (StringUtils.isNotEmpty(param)) {
            RequestEntity entity = new StringRequestEntity(param, "application/json", "UTF-8");
            post.setRequestEntity(entity);
        } else {
            RequestEntity entity = new StringRequestEntity("", "application/json", "UTF-8");
            post.setRequestEntity(entity);
        }
        //post.releaseConnection();
        return post;
    }

    private void send(String url, String param) throws IOException {
        HttpClient httpClient = new HttpClient();
        this.result = new HttpClientResultBean();
        HttpMethod method = null;

        try {
            if ("POST".equals(this.type.toUpperCase())) {
                method = this.postMethod(url, param);
            } else if ("GET".equals(this.type.toUpperCase())) {
                method = this.getMethod(url, param);
            }

            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            httpClient.executeMethod(method);
            Cookie[] e = httpClient.getState().getCookies();

            int statusCode;
            for (statusCode = 0; statusCode < e.length; ++statusCode) {
                Cookie cookie = e[statusCode];
                if ("SESSION_KEY".equals(cookie.getName())) {
                    this.sessionCookie = cookie;
                    break;
                }
            }

            statusCode = method.getStatusCode();
            switch (statusCode) {
                case 200:
                    this.resResult = method.getResponseBodyAsString();
                    this.result.setSuccess(true);
                    break;
                case 404:
                    this.result.setErrorStatus(404);
                    this.result.setErrorMsg("未知的请求地址！");
                    break;
                case 408:
                    this.result.setErrorStatus(500);
                    this.result.setErrorMsg("网络连接超时,请稍后再试！！");
                    break;
                case 500:
                    this.result.setErrorStatus(500);
                    this.result.setErrorMsg("系统内部错误！");
                    break;
                default:
                    this.result.setErrorStatus(500);
                    this.result.setErrorMsg("系统内部错误！");
            }
        } catch (IOException var11) {
            throw var11;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }

        }

    }

    public Cookie getSessionCookie() {
        return this.sessionCookie;
    }

    public String getResResult() {
        return this.resResult;
    }

    public HttpClientResultBean getResult() {
        return this.result;
    }
}
