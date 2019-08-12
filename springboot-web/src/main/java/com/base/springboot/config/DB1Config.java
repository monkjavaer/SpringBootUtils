package com.base.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @Title: DB1Config
 * @Package: com.base.springboot.config
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Date: 2019/8/12 15:50
 * @Version: V1.0
 */
@Configuration
public class DB1Config {
    @Autowired
    @Qualifier("db1")
    private DataSource db1;

    @Bean(name = "jdbcTemplate1")
    public JdbcTemplate jdbcTemplate1(){
        return new JdbcTemplate(db1);
    }

    @Bean(name = "namedParameterJdbcTemplate1")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate1(){
        return new NamedParameterJdbcTemplate(db1);
    }
}
