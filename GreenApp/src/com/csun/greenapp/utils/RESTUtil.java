package com.csun.greenapp.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RESTUtil {
	public static final String DEBUG_TAG = "RESTUtil";
	
	/**
	 * Non-construct object
	 */
	private RESTUtil() {
		
	}
	
	public static InputStream post(String url, List<NameValuePair> data) {
		InputStream input = null;
		HttpClient client = SingletonHttpClient.instance();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(data));
		} catch (UnsupportedEncodingException e) {
			Log.v(DEBUG_TAG, "Encoding exception in post() method" + e);
		}
		try {
			response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			input = entity.getContent();
		} catch (ClientProtocolException e) {
			Log.v(DEBUG_TAG, "Connection exception in post() method" + e);
		} catch (IOException e) {
			Log.v(DEBUG_TAG, "IO exception in post() method" + e);
		}
		return input;
	}
	
	public static InputStream get(String url) {
		InputStream input = null;
		HttpClient client = SingletonHttpClient.newInstance();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			input = entity.getContent();
		} catch (ClientProtocolException e) {
			Log.v(DEBUG_TAG, "Connection exception in get() method" + e);
		} catch (IOException e) {
			Log.v(DEBUG_TAG, "IO exception in get() method" + e);
		}
		return input;
	}
}

