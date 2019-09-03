package com.m.gis.springboot.utils;

import com.m.gis.springboot.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: LoggerEntity
 * @Package: com.m.gis.springboot.common
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
public class LoggerEntity implements Serializable {

    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 会话ID
     */
    private String sesseionId;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 请求IP
     */
    private String ip;
    /**
     * 请求方式GET、POST
     */
    private String httpMethod;
    /**
     * 响应方法
     */
    private String classMethod;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 请求时间
     */
    private Long requestTime;
    /**
     * 返回时间
     */
    private Long responseTime;
    /**
     * 返回结果
     */
    private String result;
    /**
     * http status
     */
    private int status;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSesseionId() {
        return sesseionId;
    }

    public void setSesseionId(String sesseionId) {
        this.sesseionId = sesseionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getRequestFormat(){
        return String.format("--> ID = {%s}, URL = {%s}, IP = {%s}, HTTP_METHOD = {%s}, CLASS_METHOD = {%s}, PARAMS = {%s}, TIME = {%s}",
                this.requestId,
                this.url,
                this.ip,
                this.httpMethod,
                this.classMethod,
                this.params,
                DateUtils.format(new Date(this.requestTime)));
    }

    public String getResponseFormat(){
        return String.format("<-- ID = {%s}, TIME = {%s}, COST = {%s ms}, RESPONSE = %s",
                this.requestId,
                DateUtils.format(new Date(this.responseTime)),
                this.responseTime-this.requestTime,
                this.result);
    }
}
