package com.csun.greenapp.utils;

import com.csun.greenapp.types.User;

public class SingletonUser {
	private static boolean done = false;
	private static User user;
	
	private SingletonUser() {
	}
	
	public static synchronized User getActiveUser() { 
		return user;
	}
	
	public static synchronized void setActiveUser(int id, String username, String password) {
		if (!done) {
			user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setPassword(password);
		}
	}
}

