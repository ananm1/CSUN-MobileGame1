package com.csun.greenapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.csun.greenapp.utils.JSONUtil;
import com.csun.greenapp.utils.NetworkUtil;
import com.csun.greenapp.utils.RESTUtil;
import com.csun.greenapp.utils.SingletonUser;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends FragmentActivity {
	private static final String TAG = LoginActivity.class.getSimpleName();
	private static final String URL_LOG_IN = "http://bookboi.com/chan/greenapp/ss12_login.php";
			
	private String activeUsername;
	private String activePassword;
	private LogInTask task = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		prepareLogin();
	}
	
	private void prepareLogin() {
		Button login = (Button) findViewById(R.id.activity_login_XML_button_login);
		if (NetworkUtil.haveNetworkConnection(LoginActivity.this)) {
			login.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						onPerformLogin();
					}
			});
		} else {
			showNetworkErrorDialog(new Throwable());
		}
	}
	
	/**
	 * Make sure the current task is not running 
	 * @return
	 * 			true if either the task have 
	 * 			not started or already finished
	 */
	private boolean haveTaskAvailable() {
        return ((task == null) || (task != null && task.getStatus() == AsyncTask.Status.FINISHED));
	}
	
	private void onPerformLogin() {
		EditText usernameEdit = (EditText) findViewById(R.id.activity_login_XML_edittext_username);
		EditText passwordEdit = (EditText) findViewById(R.id.activity_login_XML_edittext_password);
		if (!TextUtils.isEmpty(usernameEdit.getText()) && !TextUtils.isEmpty(passwordEdit.getText())) {
			String username = usernameEdit.getText().toString();
			String password = passwordEdit.getText().toString();
			// TODO: parse a complete user from server. 
			// This is dirty hack!
			activeUsername = username.toLowerCase();
			activePassword = password.toLowerCase();
			if (haveTaskAvailable()) {
				List<NameValuePair> p = new ArrayList<NameValuePair>();
				p.add(new BasicNameValuePair("username", activeUsername));
				p.add(new BasicNameValuePair("password", activePassword));
				task = new LogInTask(URL_LOG_IN, p);
				task.execute();
			}
		}
	}

	private class LogInTask extends AsyncTask<Void, Void, InputStream> {
		private String url;
		private List<NameValuePair> credential;
		private final ProgressDialog progressDialog;

		public LogInTask(String url, List<NameValuePair> credential) {
			this.url = url;
			this.credential = credential;
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setMessage("Logging in...");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
		}

		@Override
		protected void onPreExecute() {
			progressDialog.show();
		}

		@Override
		protected InputStream doInBackground(Void... params) {
			InputStream input = null;
			if (!isCancelled()) {
				input = RESTUtil.post(url, credential);
			}
			return input;
		}

		@Override
		protected void onPostExecute(InputStream input) {
			progressDialog.dismiss();
			if (input != null) {
				JSONObject result = JSONUtil.buildObject(input);
				if (result != null && result.has("id")) {
					try {
						int id = result.getInt("id");
						if (id != 0) {
							SingletonUser.setActiveUser(id, activeUsername, activePassword);
							LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));
							LoginActivity.this.finish();
						}
					} catch (JSONException e) {
						Log.e(TAG, "Exception has occured while parsing JSON", e);
					}
				} else {
					showDialogLogInError();
				}
			}
		}
	}
	
	private void showDialogLogInError() {
		FragmentManager fm = getSupportFragmentManager();
	    NetworkErrorDialog d = new NetworkErrorDialog();
	    d.show(fm, "Login Error");
	}
	
	public void showNetworkErrorDialog(Throwable t) {
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDialog d = new NetworkErrorDialog();
        d.show(fm, "Disconnected");
    }
}

