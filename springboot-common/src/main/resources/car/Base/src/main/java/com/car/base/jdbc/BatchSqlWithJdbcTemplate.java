package com.base.springboot.car.Base.src.main.java.com.car.base.jdbc;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

/**
 *
 * @ClassName: BatchSqlWithJdbcTemplate
 * @Description: JdbcTemplate批量更新操作工具类
 * @author: monkjavaer
 * @date: 2018-9-13 下午3:17:46
 */
public class BatchSqlWithJdbcTemplate {
        private static DataSource dataSource;
        private static JdbcTemplate jdbcTemplate;
        private static  String driverClassName ;
        private static  String url ;
        private static  String dbUsername;
        private static  String dbPassword;
        private static Logger logger = LoggerFactory.getLogger(BatchSqlWithJdbcTemplate.class);

        static{
            dataSource = getDataSource();
            jdbcTemplate = new JdbcTemplate(dataSource);
        }

        public static DriverManagerDataSource getDataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            try {
                PropertiesUtil propertiesUtil = new PropertiesUtil("/datasource.properties");
                driverClassName = propertiesUtil.getPropertieValue("connection.driver_class");
                url = propertiesUtil.getPropertieValue("connection.url");
                dbUsername = propertiesUtil.getPropertieValue("connection.username");
                dbPassword = propertiesUtil.getPropertieValue("connection.password");
                dataSource.setDriverClassName(driverClassName);
                dataSource.setUrl(url);
                dataSource.setUsername(dbUsername);
                dataSource.setPassword(dbPassword);
              }catch (Exception e) {
                e.printStackTrace();
                logger.error("BatchSqlWithJdbcTemplate init error!!!!!,Message is "+e.getMessage());
              }
            return dataSource;
        }

        public static void updateBatch(String sql,List<Object[]> params,int[] types)throws DataAccessException {
            jdbcTemplate.batchUpdate(sql, params, types);
        }
    }

