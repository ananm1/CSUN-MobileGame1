package com.csun.greenapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONUtil {
	private static final String DEBUG_TAG = "JSONUtil";
	
	/**
	 * Non-construct object
	 */
	private JSONUtil() {
		
	}
	
	/**
	 * Parse an input stream from HttpClient
	 * 
	 * @param input
	 * 			Data from network
	 * @return
	 * 			An array of JSON objects
	 * @throws JSONException
	 * 			Wrong data format
	 * 			
	 */
	public static JSONArray buildArray(InputStream input) {
		String result = "";
		JSONArray json = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"), 8);
			StringBuilder content = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line + "\n");
				Log.v("WTF", line);
			}
			input.close();
			result = content.toString();
			json = new JSONArray(result);
		} catch (UnsupportedEncodingException e) {
			Log.e(DEBUG_TAG, "Exception occurs in parseArray()", e);
		} catch (IOException e) {
			Log.e(DEBUG_TAG, "Exception occurs in parseArray()", e);
		} catch (JSONException e) {
			Log.e(DEBUG_TAG, "Exception occurs in parseArray()", e);
		}
		return json;
	}
	
	/**
	 * Parse an input stream from HttpClient 
	 * 
	 * @param input
	 * 			Data from network	
	 * @return
	 * 			A JSON object 
	 * @throws JSONException
	 * 			Wrong data format
	 */
	public static JSONObject buildObject(InputStream input) {
		String result = "";
		JSONObject json = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"), 8);
			StringBuilder content = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line + "\n");
			}
			input.close();
			result = content.toString();
			json = new JSONObject(result);
		} catch (IOException e) {
			Log.e(DEBUG_TAG, "Exception occurs in parseObject()", e);
		} catch (JSONException e) {
			Log.e(DEBUG_TAG, "Exception occurs in parseObject()", e);
		}
		return json;
	}
}

