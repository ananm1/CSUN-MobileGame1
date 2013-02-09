package com.csun.greenapp.utils;

import android.os.Bundle;
import android.util.Log;

public class ThreadUtil {
	private static final String DEBUG_TAG = "ThreadUtil";

	/**
	 * Non-construct object
	 */
	private ThreadUtil() {

	}

	public static long getThreadId() {
		return Thread.currentThread().getId();
	}

	public static String getThreadSignature() {
		Thread t = Thread.currentThread();
		long id = t.getId();
		long priority = t.getPriority();
		String group = t.getThreadGroup().getName();
		String name = t.getName();
		return name + " = id: " + id + ", priority: " + priority + ", group: "
				+ group;
	}

	public static void logThreadSignature() {
		Log.v(DEBUG_TAG, getThreadSignature());
	}

	public static void sleepFor(int secs) {
		try {
			Thread.sleep(Math.abs(secs) * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		}
	}

	public static Bundle getStringAsBundle(String msg) {
		Bundle b = new Bundle();
		b.putString("message", msg);
		return b;
	}

	public static String getStringFromBundle(Bundle b) {
		return b.getString("message");
	}
}