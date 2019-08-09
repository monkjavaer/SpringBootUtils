package com.m.gis.springboot.config.mybatis;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Title: MybatisConfig
 * @Package: com.m.gis.springboot.config.mybatis
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/9/6
 * @Version: V1.0
 */
@Configuration
public class MybatisConfig {
    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "postgresql");    //配置数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

/*    @Bean
    public SqlMybatisInterceptor sqlMybatisInterceptor(){
        SqlMybatisInterceptor sqlMybatisInterceptor = new SqlMybatisInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", "postgresql");
        sqlMybatisInterceptor.setProperties(properties);
        return sqlMybatisInterceptor;
    }*/

}
