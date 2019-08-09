package com.car.base.common.utilities;

import com.car.base.common.exception.ConfigException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StorageConfig {
	
	protected String dataFilePath = null;
	
	protected static StorageConfig instance = null;
	
	//file type
	public static final String THUMBNAIL = "thumbnail";
	
	public static final String VIDEO = "video";
	
	public static final String SNAPSHOT = "snapshot"; 
	
	public static final String PICTURE = "picture";
	
	private StorageConfig() throws ConfigException {
		readProperties();
	}

	public static StorageConfig getInstance() throws ConfigException {
		if(instance == null) {
			instance = new StorageConfig();
		}
		return instance;
	}
	
	protected void readProperties() throws ConfigException{
		Properties prop = new Properties();
		InputStream in = null;
		
		try{
			//in = new BufferedInputStream(new FileInputStream("file-server.properties"));
			in = getClass().getResourceAsStream("/storage-server.properties");
			prop.load(in);
			
			dataFilePath = prop.getProperty("hdfs.data.file.path");
			if(dataFilePath == null) {
				throw new ConfigException("ERROR: empty upload.file.path in storage-server.properties", "storage-server.properties");
			}
			
			in.close();
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
		
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	
	public String toFullURL(String relativeURL) {
		return dataFilePath + "/" + relativeURL;
	}

 

}
