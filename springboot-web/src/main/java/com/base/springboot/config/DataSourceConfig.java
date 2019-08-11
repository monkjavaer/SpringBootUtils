package com.base.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/11 0011 22:06
 */
@Configuration
public class DataSourceConfig {

    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "datasource.mysql1")
    public DataSourceProperties getMysql1Properties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource getMysql1DataSource() {
        DataSourceProperties dataSourceProperties = getMysql1Properties();
        logger.info("=============mysql1 datasource: {}", dataSourceProperties.getUrl());
        //DataSourceBuilder
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager mysql1TxManager(DataSource fooDataSource) {
        return new DataSourceTransactionManager(fooDataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.mysql2")
    public DataSourceProperties getMysql2Properties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource getMysql2DataSource() {
        DataSourceProperties dataSourceProperties = getMysql2Properties();
        logger.info("=============mysql2 datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager mysql2TxManager(DataSource fooDataSource) {
        return new DataSourceTransactionManager(fooDataSource);
    }

}
