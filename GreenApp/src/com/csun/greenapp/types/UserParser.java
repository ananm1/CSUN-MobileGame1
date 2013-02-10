package com.csun.greenapp.types;

import org.json.JSONException;
import org.json.JSONObject;

public class UserParser extends AbstractParser<User> {
	@Override
	public User parse(JSONObject json) throws JSONException {
		User obj = new User();
		if (json.has("id")) {
			obj.setId(json.getInt("id"));
		}
		if (json.has("username")) {
			obj.setUsername(json.getString("username"));
		}
		if (json.has("password")) {
			obj.setPassword(json.getString("password"));
		}
		return obj;
	}
}

