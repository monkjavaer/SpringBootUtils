package com.m.gis.springboot.config.converts;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.m.gis.springboot.common.utils.JsonUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FastJsonConvertConfig
 * @Package: com.m.gis.springboot.config.converts
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/12
 * @Version: V1.0
 */
@Configuration
public class FastJsonConvertConfig {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fjc = new FastJsonConfig();
        fjc.setSerializerFeatures(JsonUtils.FEATURES);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonConverter.setFastJsonConfig(fjc);
        return new HttpMessageConverters(fastJsonConverter);
    }
}




