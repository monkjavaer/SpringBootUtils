package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import com.car.base.common.exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GisConfig {

	protected String  gisIpAddress = null;//should be IP of public network
	
	protected static GisConfig instance = null;
	
	private GisConfig() throws ConfigException{
		readProperties();
	}
	
	public static GisConfig getInstance() throws ConfigException {
		if(instance == null) {
			instance = new GisConfig();
		}
		return instance;
	}
	
	protected void readProperties() throws ConfigException{
		Properties prop = new Properties();
		InputStream in = null;
		
		
		try {
			in = getClass().getResourceAsStream("/gis-server.properties");
			prop.load(in);
			
			gisIpAddress = prop.getProperty("gis.ip.address");
			
			if(null == gisIpAddress){
				throw new ConfigException("ERROR: empty gis.ip.address in gis-server.properties", "gis-server.properties");
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getGisIpAddress() {
		return gisIpAddress;
	}

	public void setGisIpAddress(String gisIpAddress) {
		this.gisIpAddress = gisIpAddress;
	}
}
