package com.csun.greenapp.types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public interface Parser<T extends GreenAppType> {
    public abstract T parse(JSONObject json) throws JSONException;
}