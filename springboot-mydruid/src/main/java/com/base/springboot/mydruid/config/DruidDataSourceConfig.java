package com.base.springboot.mydruid.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Druid 多数据源配置
 *
 * <p>
 *     https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
 * </p>
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/11 0011 22:06
 */
@Configuration
public class DruidDataSourceConfig {

    @Primary
    @Bean(name = "db1")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }

}
