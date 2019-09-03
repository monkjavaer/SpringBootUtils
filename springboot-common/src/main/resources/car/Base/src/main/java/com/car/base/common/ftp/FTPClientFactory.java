package com.base.springboot.car.Base.src.main.java.com.car.base.common.ftp;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * FTPClient工厂类，通过FTPClient工厂提供FTPClient实例的创建和销毁
 * @author heaven
 */
public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {

    private static final Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);

    private FTPConfig ftpConfig;

    public FTPClientFactory(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }
    /**
     * 新建对象
     */
    @Override
    public FTPClient create() throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.error("===FTPServer 拒绝连接");
                return null;
            }
            boolean result = ftpClient.login(ftpConfig.getUsername(),ftpConfig.getPassword());
            if (!result) {
                logger.error("===FTPServer 登陆失败");
                throw new Exception("===FTPServer登陆失败! userName:"+ ftpConfig.getUsername() + " ; password:"
                        + ftpConfig.getPassword());
            }
            if(ftpConfig.getBufferSize() != 0){
                ftpClient.setBufferSize(ftpConfig.getBufferSize());
            }else{
                ftpClient.setBufferSize(1024);
            }
            if (ftpConfig.isPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }
            //系统默认采用BINARY_FILE_TYPE，如果不是，请自行设定
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            logger.error("===FTPServer 登陆失败:" + e.getMessage(), e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<FTPClient>(ftpClient);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.logout();
        super.destroyObject(p);
    }

    /**
     * 验证对象
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        boolean connect = false;
        try {
            connect = ftpClient.sendNoOp();
        } catch (IOException e) {
            logger.error("===FTPServer 验证失败:" + e.getMessage(), e);
        }
        return connect;
    }
}
