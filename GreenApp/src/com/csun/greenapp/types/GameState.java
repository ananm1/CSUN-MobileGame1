package com.csun.greenapp.types;

public class GameState implements GreenAppType {
	private int number;
	private int userId;
	
	public GameState() {
		number = 0;
		userId = 0;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
