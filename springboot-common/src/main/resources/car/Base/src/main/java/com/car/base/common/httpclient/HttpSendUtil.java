package com.base.springboot.car.Base.src.main.java.com.car.base.common.httpclient;

/**
 * Created by monkjavaer on 17-9-1.
 */
public class HttpSendUtil extends BaseHttpClient {
    private String url;

    public HttpSendUtil() {
    }

    @Override
    protected String bulidParam(Object[] arr) {
        this.url = (String) arr[0];
        return "".equals(this.url) ? "" : (String) arr[1];
    }

    @Override
    protected HttpClientResultBean formatResults(HttpClientResultBean result) {
        if (result.isSuccess()) {
            result.setResult(this.getResResult());
        }

        return result;
    }

    @Override
    protected String getUrl() {
        return this.url;
    }
}
