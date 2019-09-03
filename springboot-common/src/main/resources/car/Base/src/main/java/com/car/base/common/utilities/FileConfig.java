package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import com.car.base.common.exception.ConfigException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileConfig {
	protected String downloadFilePath = null;//should be IP of public network
	protected String videoFilePath = null;//private IP of video
	protected String uploadFileFtpServerIp = null;//can be IP of local network or public network, local IP preferred
	protected int uploadFileFtpServerPort = 21;   //upload.file.ftp.server.port
	protected String uploadFileFtpServerUsername = null;
	protected String uploadFileFtpServerPassword = null;
	protected String uploadFileFtpServerTopFolder = null;
	
	protected static FileConfig instance = null;
	
	private FileConfig() throws ConfigException {
		readProperties();
	}
	
	public static FileConfig getInstance() throws ConfigException {
		if(instance == null) {
			instance = new FileConfig();
		}
		return instance;
	}
	
	protected void readProperties() throws ConfigException{
		Properties prop = new Properties();
		InputStream in = null;
		
		try{
			//in = new BufferedInputStream(new FileInputStream("file-server.properties"));
			in = getClass().getResourceAsStream("/file-server.properties");
			prop.load(in);
			
			downloadFilePath = prop.getProperty("download.file.path");
			if(downloadFilePath == null) {
				throw new ConfigException("ERROR: empty download.file.path in file-server.properties", "file-server.properties");
			}
			
			videoFilePath = prop.getProperty("video.file.path");
			if(videoFilePath == null) {
				throw new ConfigException("ERROR: empty video.file.path in file-server.properties", "file-server.properties");
			}
			
			uploadFileFtpServerIp = prop.getProperty("upload.file.ftp.server.ip");
			if(uploadFileFtpServerIp == null) {
				throw new ConfigException("ERROR: empty upload.file.ftp.server.ip in file-server.properties", "file-server.properties");
			}
		
			
			String strPort = prop.getProperty("upload.file.ftp.server.port");
			if(strPort != null && strPort.trim() != null) {
				try {
					uploadFileFtpServerPort = Integer.parseInt(strPort);
				} catch(NumberFormatException nfe) {
				}
			}
			
			uploadFileFtpServerUsername = prop.getProperty("upload.file.ftp.server.username");
			if(uploadFileFtpServerUsername == null) {
				throw new ConfigException("ERROR: empty upload.file.ftp.server.username in file-server.properties", "file-server.properties");
			}
			
			uploadFileFtpServerPassword = prop.getProperty("upload.file.ftp.server.password");
			if(uploadFileFtpServerPassword == null) {
				throw new ConfigException("ERROR: empty upload.file.ftp.server.password in file-server.properties", "file-server.properties");
			}			
			
			uploadFileFtpServerTopFolder = prop.getProperty("upload.file.ftp.server.top.folder");
			if(uploadFileFtpServerTopFolder == null) {
				throw new ConfigException("ERROR: empty upload.file.ftp.server.top.folder in file-server.properties", "file-server.properties");
			}
			
			
			in.close();
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
		
	}

	public synchronized String getVideoFilePath() {
		return videoFilePath;
	}

	public synchronized void setVideoFilePath(String videoFilePath) {
		this.videoFilePath = videoFilePath;
	}

	public synchronized String getDownloadImagePath() {
		return downloadFilePath;
	}

	public synchronized void setDownloadImagePath(String downloadImagePath) {
		this.downloadFilePath = downloadImagePath;
	}

	public synchronized String getUploadImageFtpServerIp() {
		return uploadFileFtpServerIp;
	}

	public synchronized void setUploadImageFtpServerIp(String uploadImageFtpServerIp) {
		this.uploadFileFtpServerIp = uploadImageFtpServerIp;
	}

	public synchronized String getUploadImageFtpServerUsername() {
		return uploadFileFtpServerUsername;
	}

	public synchronized void setUploadImageFtpServerUsername(String uploadImageFtpServerUsername) {
		this.uploadFileFtpServerUsername = uploadImageFtpServerUsername;
	}

	public synchronized String getUploadImageFtpServerPassword() {
		return uploadFileFtpServerPassword;
	}

	public synchronized void setUploadImageFtpServerPassword(String uploadImageFtpServerPassword) {
		this.uploadFileFtpServerPassword = uploadImageFtpServerPassword;
	}

	public synchronized String getUploadImageFtpServerTopFolder() {
		return uploadFileFtpServerTopFolder;
	}

	public synchronized void setUploadImageFtpServerTopFolder(
			String uploadImageFtpServerTopFolder) {
		this.uploadFileFtpServerTopFolder = uploadImageFtpServerTopFolder;
	}

	public synchronized int getUploadImageFtpServerPort() {
		return uploadFileFtpServerPort;
	}

	public synchronized void setUploadImageFtpServerPort(int uploadImageFtpServerPort) {
		this.uploadFileFtpServerPort = uploadImageFtpServerPort;
	}

	
	
	
	
}
