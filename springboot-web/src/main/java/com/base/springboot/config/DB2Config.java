package com.base.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
public class DB2Config {
    @Autowired
    @Qualifier("db2")
    private DataSource db2;

    @Bean(name = "jdbcTemplate2")
    public JdbcTemplate jdbcTemplate2(){
        return new JdbcTemplate(db2);
    }
}
