package com.base.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * REST客户端
 * @author monkjavaer
 * @date 2019/7/31 13:06
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory simpleClientHttpRequestFactory){
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean("simpleClientHttpRequestFactory")
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }
/*    @Bean("httpComponentsClientHttpRequestFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }
    @Bean("okHttp3ClientHttpRequestFactory")
    public OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory(){
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }*/
}
