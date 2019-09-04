package com.base.springboot.declarativetransaction;

public interface StudentService {
    void insertRecord();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback1() throws RollbackException;
    void invokeInsertThenRollback2() throws RollbackException;
    void invokeInsertThenRollback3() throws RollbackException;
}
