package com.csun.greenapp.types;

import org.json.JSONException;
import org.json.JSONObject;

public class GameStateParser extends AbstractParser<GameState> {
	@Override
	public GameState parse(JSONObject json) throws JSONException {
		GameState obj = new GameState();
		if (json.has("number")) {
			obj.setNumber(json.getInt("number"));
		}
		if (json.has("user_id")) {
			obj.setUserId(json.getInt("user_id"));
		}
		return obj;
	}
}