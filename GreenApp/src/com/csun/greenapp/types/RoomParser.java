package com.csun.greenapp.types;

import org.json.JSONException;
import org.json.JSONObject;

import com.csun.greenapp.Room;

public class RoomParser extends AbstractParser<Room> {
	@Override
	public Room parse(JSONObject json) throws JSONException {
		Room obj = new Room();
		if (json.has("id")) {
			obj.setId(json.getInt("id"));
		}
		if (json.has("num")) {
			obj.setCount(json.getInt("num"));
		}
		return obj;
	}
}
