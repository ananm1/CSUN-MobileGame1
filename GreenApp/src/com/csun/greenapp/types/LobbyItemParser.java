package com.csun.greenapp.types;

import org.json.JSONException;
import org.json.JSONObject;

import com.csun.greenapp.Room;

public class LobbyItemParser extends AbstractParser<LobbyItem> {
	@Override
	public LobbyItem parse(JSONObject json) throws JSONException {
		LobbyItem obj = new LobbyItem();
		if (json.has("username")) {
			obj.setUsername(json.getString("username"));
		}
		if (json.has("status")) {
			int r = json.getInt("status");
			if (r == 1) {
				obj.setReady(true);
			} else {
				obj.setReady(false);
			}
		}
		return obj;
	}
}
