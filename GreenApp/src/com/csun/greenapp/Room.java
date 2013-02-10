package com.csun.greenapp;

import com.csun.greenapp.types.GreenAppType;

public class Room implements GreenAppType {
	private int id;
	private int count;

	public Room() {
	
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
