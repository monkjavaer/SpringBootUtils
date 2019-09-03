package com.base.springboot.car.Base.src.main.java.com.car.base.common.ftp;

/**
 * FTP属性相关的配置
 * monkjavaer
 */
public class FTPConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean passiveMode;
    private int bufferSize;

    private String downloadFilePath;
    private String ftpServerTopFolder;

    public FTPConfig(String serverIp, int serverPort, String ftpServerUsername, String ftpServerPassword, boolean passiveMode, int bufferSize) {
        this.host = serverIp;
        this.port = serverPort;
        this.username = ftpServerUsername;
        this.password = ftpServerPassword;
        this.passiveMode = passiveMode;
        this.bufferSize = bufferSize;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public String getFtpServerTopFolder() {
        return ftpServerTopFolder;
    }

    public void setFtpServerTopFolder(String ftpServerTopFolder) {
        this.ftpServerTopFolder = ftpServerTopFolder;
    }
}
