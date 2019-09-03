package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import com.car.base.common.exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StreamConfig {
	protected String rtspPath = null;
	protected static StreamConfig instance = null;
	
	private StreamConfig (){
		readProperties();
	}
	
	public static StreamConfig getInstance() throws ConfigException{
		if(instance == null){
			instance = new StreamConfig();
		}
		
		return instance;
	}
	
	protected void readProperties(){
		Properties prop = new Properties();
		InputStream in = null;
		
		try {
			in = getClass().getResourceAsStream("/stream-server.properties");
			prop.load(in);
			
			rtspPath = prop.getProperty("stream.config");
			if(rtspPath == null) {
				throw new ConfigException("ERROR: empty stream.config in stream-server.properties", "stream-server.properties");
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRtspPath() {
		return rtspPath;
	}

	public void setRtspPath(String rtspPath) {
		this.rtspPath = rtspPath;
	}
	
	public String toFullRtspURL(String relativeURL){
		
		return rtspPath + relativeURL;
	}
}
