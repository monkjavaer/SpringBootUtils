package com.m.gis.springboot.config.mvc;

import com.m.gis.springboot.config.converts.GisBaseEnumConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @Title: WebMvcConfig
 * @Package: com.m.gis.springboot.config.mvc
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 添加国际化参数拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        //国际化参数拦截器，拦截含有locale参数的url
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 添加跨域
     * @param registry
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("locale");
        return lci;
    }

    /**
     * 配置默认的语言
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * 配置枚举类转换器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new GisBaseEnumConverterFactory());
    }

    /**
     * favorPathExtension表示支持后缀匹配，属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }


    /**
     * 添加静态资源映射
     */
/*    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/

}