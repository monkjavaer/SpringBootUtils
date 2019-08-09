package com.car.base.common.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;

/**
 * FTPClient连接池的实现类
 * monkjavaer
 */
public class FTPClientPool {
    private GenericObjectPool<FTPClient> ftpClientPool;
    private FTPConfig config;

    public FTPClientPool(FTPConfig ftpConfig){
        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setMinIdle(10);
        poolConfig.setMaxIdle(100);
        poolConfig.setMaxTotal(50);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestWhileIdle(false);
        poolConfig.setLifo(false);
        config = ftpConfig;

        // 初始化对象池
        ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);
    }
    public FTPClient borrowObject() throws Exception {
//        System.out.println("获取前");
//        System.out.println("活动"+ftpClientPool.getNumActive());
//        System.out.println("等待"+ftpClientPool.getNumWaiters());
//        System.out.println("----------");
//        return ftpClientPool.borrowObject();
        return new FTPClientFactory(config).create();
    }

    public void returnObject(FTPClient ftpClient){
        disConnect(ftpClient);
//        System.out.println("归还前");
//        System.out.println("活动"+ftpClientPool.getNumActive());
//        System.out.println("等待"+ftpClientPool.getNumWaiters());
//        System.out.println("----------");
//        try {
//            ftpClient.changeWorkingDirectory("/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ftpClientPool.returnObject(ftpClient);
//        System.out.println("归还后");
//        System.out.println("活动"+ftpClientPool.getNumActive());
//        System.out.println("等待"+ftpClientPool.getNumWaiters());
//        System.out.println("----------");
    }

    /**
     * 断开连接
     * @param ftpClient
     */
    private static void disConnect(FTPClient ftpClient) {
        if (ftpClient != null) {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
