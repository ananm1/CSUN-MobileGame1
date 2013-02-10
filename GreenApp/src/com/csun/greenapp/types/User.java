package com.csun.greenapp.types;

public class User implements GreenAppType {
	public static final int INITIALIZE_STATE_INT = -1;
	public static final String INITIALIZE_STATE_STRING = "unknown";

	private int id;
	private int roomId;
	private String username;
	private String password;

	public User() {
		id = INITIALIZE_STATE_INT;
		username = INITIALIZE_STATE_STRING;
		password = INITIALIZE_STATE_STRING;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getRoomId() {
		return roomId;
	}
}

