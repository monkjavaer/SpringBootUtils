package com.base.springboot.car.Base.src.main.java.com.car.base.common.sms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestor {
	private String charset = "utf-8";
	
	/**
	 * 
	 * @param url - The requested url
	 * @return - Only the body of the response
	 * @throws Exception
	 */
	public String get(String url) throws Exception{
		URL localURL = new URL(url);
		
		URLConnection conn = localURL.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)conn;
 
		
		httpConn.setRequestProperty("Accept-Charset", charset);
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		if(httpConn.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpConn.getResponseCode());
		}
		
		InputStream inputStream = null;
		BufferedReader reader = null;
		StringBuffer response = null;
		try {
		inputStream = httpConn.getInputStream();
		reader = new BufferedReader(new InputStreamReader(inputStream));
		
		response = new StringBuffer();
		String tmpLine = null;
		while((tmpLine = reader.readLine()) != null) {
			response.append(tmpLine + "\r\n");
		}
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
			
			if(reader != null) {
				reader.close();
			}
		}
		
		
		return response.toString();
	}
	
	/**
	 * 
	 * @param url - the requested url
	 * @param postBody - the body of the request
	 * @return - Only the body of the response
	 * @throws Exception
	 */
	public String post(String url, String postBody) throws Exception {
		URL localURL = new URL(url);
		URLConnection urlConn = localURL.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) urlConn;
		
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty("Accept-Charset", charset);
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Content-Length", String.valueOf(postBody.length()));
		
		OutputStream outputStream = null;
		BufferedWriter writer = null;
		InputStream inputStream = null;
		BufferedReader reader = null;
		
		StringBuffer response = new StringBuffer();
		try {
			outputStream = httpConn.getOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			
			writer.write(postBody);
			writer.flush();
			
			if(httpConn.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpConn.getResponseCode());
			}
			
			inputStream = httpConn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			
			
			String tmpLine = null;
			
			while((tmpLine = reader.readLine()) != null) {
				response.append(tmpLine + "\n");
			}
		} finally {
			outputStream.close();
			writer.close();
			inputStream.close();
			reader.close();
		}
		
		
		return response.toString();
	}
	
	/**
	 * 
	 * @param url - the requested url
	 * @param parameterMap - the body of the request
	 * @return - Only the body of the response
	 * @throws Exception
	 */
	public String post(String url, Map parameterMap) throws Exception {
		StringBuffer paraBuffer = new StringBuffer();
		
		if(parameterMap != null) {
			Iterator itr = parameterMap.keySet().iterator();
			String key;
			String value;
			while(itr.hasNext()) {
				key = (String)itr.next();
				value = (String)parameterMap.get(key);
				if(value == null) {
                    value = "";
                }
				
				paraBuffer.append(key).append("=").append(value);
				if(itr.hasNext()) {
                    paraBuffer.append("&");
                }
			}
		}

		String postBody = paraBuffer.toString();
		return post(url, postBody);

	}
	
}
