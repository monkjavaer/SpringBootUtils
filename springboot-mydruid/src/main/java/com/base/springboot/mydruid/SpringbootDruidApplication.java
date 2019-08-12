package com.base.springboot.mydruid;

import com.base.springboot.mydruid.filter.ConnectionLogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
public class SpringbootDruidApplication implements ApplicationRunner {

    /**
     * log
     */
    private static Logger log = LoggerFactory.getLogger(SpringbootDruidApplication.class);

    @Autowired
    @Qualifier("db1")
    private DataSource dataSource;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootDruidApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(dataSource.toString());
    }
}
