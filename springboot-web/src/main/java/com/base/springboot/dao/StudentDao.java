package com.base.springboot.dao;

import com.base.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc 批量插入 <br/>
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/12 0012 23:36
 */
@Repository
public class StudentDao {

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("namedParameterJdbcTemplate1")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate1;

    /**
     * <p>
     *     1、JdbcTemplate
     *     2、NamedParameterJdbcTemplate
     * </p>
     */
    public void batchInsert() {

        //批量插入方式1
        jdbcTemplate.batchUpdate("INSERT INTO STUDENT (NAME,AGE) VALUES (?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "b-" + i);
                        ps.setInt(2,  + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 2;
                    }
                });

        //批量插入方式2
        List<Student> list = new ArrayList<>();
        list.add(Student.builder().name("赵云").age(25).build());
        list.add(Student.builder().name("赵子龙").age(25).build());
        namedParameterJdbcTemplate1
                .batchUpdate("INSERT INTO STUDENT (NAME, AGE) VALUES (:name, :age)",
                        SqlParameterSourceUtils.createBatch(list));
    }
}
