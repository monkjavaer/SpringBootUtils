package com.base.springboot.mydruid.config;

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
    private static Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource db1DataSource() {
        DataSourceProperties dataSourceProperties = db1DataSourceProperties();
        log.info("db1 datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager db1TxManager(DataSource db1DataSource) {
        return new DataSourceTransactionManager(db1DataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db2DataSource() {
        DataSourceProperties dataSourceProperties = db2DataSourceProperties();
        log.info("db2 datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager db2TxManager(DataSource db2DataSource) {
        return new DataSourceTransactionManager(db2DataSource);
    }

}
