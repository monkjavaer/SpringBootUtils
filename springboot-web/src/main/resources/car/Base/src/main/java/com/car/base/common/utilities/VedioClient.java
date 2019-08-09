package com.car.base.common.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VedioClient {
	private Socket socketClient;
	private String ipAddress;
	private int portNumber;
	private int readOutTime;
	private DataOutputStream outToServer; 
	private BufferedReader inFromServer;
	
	
	public VedioClient(String ipAddress, int portNumber, int readOutTime) {
		super();
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.readOutTime = readOutTime;
	}
	
	public Boolean connect(){
		try {
			socketClient = new Socket(ipAddress,portNumber);
			socketClient.setSoTimeout(readOutTime);		
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  	
	}
	
	
	public Boolean sendAsByteArray(byte[] msg){
		if(socketClient!=null){
			int outLen=msg.length;
			try {
				DataOutputStream outToServer = new DataOutputStream(socketClient.getOutputStream());	
				outToServer.write(msg,0,outLen);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}           // write the message			
		}
		return false;
	}
	
	
	public byte[] receiveAsByteArray(){
		if(socketClient!=null){
			try {
		        DataInputStream in = new DataInputStream(socketClient.getInputStream());
		        int len=1024;
		        
		        byte[] data = new byte[len];
		        len=in.read(data);	        
				return data;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public Boolean close(){
		if(socketClient!=null){
			try {
				socketClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;		
			}
			return true;
		}
		return false;		
	}
	
}
