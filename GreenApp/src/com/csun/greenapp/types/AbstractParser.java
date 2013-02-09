package com.csun.greenapp.types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractParser<T extends GreenAppType> implements Parser<T> {
    /** 
     * All derived parsers must implement parsing a JSONObject instance of themselves. 
     */
    public abstract T parse(JSONObject json) throws JSONException;
}