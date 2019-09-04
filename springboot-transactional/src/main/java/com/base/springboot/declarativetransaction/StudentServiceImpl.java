package com.base.springboot.declarativetransaction;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentService studentService;


    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO Student (NAME) VALUES ('AAA')");
    }

    /**
     * <p>
     *      (rollbackFor = RollbackException.class) 抛出自定义异常时，回滚事务。
     * </p>
     * @throws RollbackException
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO Student (NAME) VALUES ('BBB')");
        throw new RollbackException();
    }

    /**
     * <p>
     *      spring的事务是通过aop进行代理增强的。
     *      这里是在类的内部直接执行方法调用，并没有调用代理类来进行方法调用，所以这里事务不会回滚。
     * </p>
     *
     * @throws RollbackException
     */
    @Override
    public void invokeInsertThenRollback1() throws RollbackException {
        insertThenRollback();
    }


    /**
     *
     * 通过AopContext.currentProxy()获取当前类的代理对象
     *
     * @throws RollbackException
     */
    @Override
    public void invokeInsertThenRollback2() throws RollbackException {
        ((StudentService) (AopContext.currentProxy())).insertThenRollback();
    }

    /**
     * 把自己的实例注入进来
     * @throws RollbackException
     */
    @Override
    public void invokeInsertThenRollback3() throws RollbackException {
        studentService.insertThenRollback();
    }
}
