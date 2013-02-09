package com.csun.greenapp.utils;

import android.content.Context;
import android.widget.Toast;

public class UiUtil {
	public static void showText(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
}
