package com.base.springboot.init;

import com.base.springboot.dao.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * CommandLineRunner启动时运行
 *
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/11 0011 21:42
 */
@Component
public class WebInit implements CommandLineRunner {

    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(WebInit.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
        studentDao.batchInsert();
    }

    private void showConnection() throws SQLException {
        logger.info("showConnection================================={}", dataSource.toString());
        Connection conn = dataSource.getConnection();
        logger.info("conn================================={}", conn.toString());
        conn.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("SELECT * FROM STUDENT ")
                .forEach(row -> logger.info("row================================={}", row.toString()));
    }

}
