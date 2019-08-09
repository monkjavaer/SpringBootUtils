package com.car.base.common.utilities;

import com.car.base.common.exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConfig {
	protected String videoAlarmUrl = null;
	protected String getIncidentTypeDicUrl = null;
	protected static SystemConfig instance = null;

	private SystemConfig(){
		readProperties();
	}
	
	public static SystemConfig getInstance() throws ConfigException{
		if(instance == null){
			instance = new SystemConfig();
		}
		
		return instance;
	}
	
	protected void readProperties(){
		Properties prop = new Properties();
		InputStream in = null;
		
		try {
			in = getClass().getResourceAsStream("/system.properties");
			prop.load(in);

			videoAlarmUrl = prop.getProperty("jcj.VideoAlarmUrl");
			if(videoAlarmUrl == null) {
				throw new ConfigException("ERROR: empty system.config in system.properties", "system.properties");
			}

			getIncidentTypeDicUrl = prop.getProperty("jcj.GetIncidentTypeDicUrl");
			if(getIncidentTypeDicUrl == null) {
				throw new ConfigException("ERROR: empty system.config in system.properties", "system.properties");
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

	public String getVideoAlarmUrl() {
		return videoAlarmUrl;
	}

	public void setVideoAlarmUrl(String videoAlarmUrl) {
		this.videoAlarmUrl = videoAlarmUrl;
	}

	public String getGetIncidentTypeDicUrl() {
		return getIncidentTypeDicUrl;
	}

	public void setGetIncidentTypeDicUrl(String getIncidentTypeDicUrl) {
		this.getIncidentTypeDicUrl = getIncidentTypeDicUrl;
	}
}
