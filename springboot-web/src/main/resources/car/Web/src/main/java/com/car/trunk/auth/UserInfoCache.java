package com.car.trunk.auth;

import java.util.Hashtable;

public class UserInfoCache {
	private static UserInfoCache instance = null;
	
	private Hashtable<String, String> userTable;
	
	private UserInfoCache() {
		userTable = new Hashtable<String, String>();
	}

	public static UserInfoCache getInstance() {
		if(instance == null) {
			instance = new UserInfoCache();
		}
		return instance;
	}
	
	public void add(String userName, String userId) {
		userTable.put(userName, userId);
	}
	
	public void remove(String userName) {
		userTable.remove(userName);
	}
	
	public String get(String userName) {
		return userTable.get(userName);
	}
}
