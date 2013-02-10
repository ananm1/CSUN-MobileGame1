package com.csun.greenapp.types;

public class LobbyItem implements GreenAppType {
	private String username;
	private boolean ready;
	
	public LobbyItem() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
