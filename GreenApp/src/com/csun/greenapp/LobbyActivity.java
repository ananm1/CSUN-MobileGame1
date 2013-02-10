package com.csun.greenapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.csun.greenapp.types.LobbyItem;
import com.csun.greenapp.types.LobbyItemParser;
import com.csun.greenapp.types.RoomParser;
import com.csun.greenapp.utils.JSONUtil;
import com.csun.greenapp.utils.RESTUtil;
import com.csun.greenapp.utils.SingletonUser;
import com.csun.greenapp.utils.UiUtil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LobbyActivity extends FragmentActivity {
	private static final String TAG = LoginActivity.class.getSimpleName();
	private static final String URL_LOAD_USERS = "http://bookboi.com/chan/greenapp/ss12_get_lobby_users.php";
	private static final String URL_FLAG_READY = "http://bookboi.com/chan/greenapp/ss12_set_ready.php";
	private static final String URL_CHECK_OTHERS = "http://bookboi.com/chan/greenapp/ss12_check_launch.php";
	
	private ListView listview;
	private List<LobbyItem> items;
	private LobbyAdapter adapter;
	private LoadUserTask task;
	private IamReadyTask iamTask;
	private CheckOtherPlayersTask checkTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);
		setUpViews();
		if (haveTaskAvailable(task)) {
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			p.add(new BasicNameValuePair("room_id", Integer.toString(SingletonUser.getActiveUser().getRoomId())));
			task = new LoadUserTask(URL_LOAD_USERS, p);
			task.execute();
		}
	}
	
	private void setUpViews() {
		listview = (ListView) findViewById(R.id.activity_lobby_XML_listview);
		items = new ArrayList<LobbyItem>();
		adapter = new LobbyAdapter(this, items);
		listview.setAdapter(adapter);
		
		Button ready = (Button) findViewById(R.id.activity_lobby_XML_button_ready);
		ready.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (haveTaskAvailable(iamTask)) {
					List<NameValuePair> p = new ArrayList<NameValuePair>();
					p.add(new BasicNameValuePair("user_id", Integer.toString(SingletonUser.getActiveUser().getId())));
					p.add(new BasicNameValuePair("room_id", Integer.toString(SingletonUser.getActiveUser().getRoomId())));
					iamTask = new IamReadyTask(URL_FLAG_READY, p);
					iamTask.execute();
				}
			}
		});
	}
	
	private class LoadUserTask extends AsyncTask<Void, LobbyItem, Boolean> {
		private String url;
		private List<NameValuePair> extras;
		private ProgressDialog progressDialog;
		
		public LoadUserTask(String url, List<NameValuePair> extras) {
			this.url = url;
			this.extras = extras;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LobbyActivity.this);
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onProgressUpdate(LobbyItem... lob) {
			updateUi(lob[0]);
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			InputStream input = null;
			// get stream from network
			if (!isCancelled()) {
				input = RESTUtil.post(url, extras);
			}
			
			// build JSON array
			JSONArray array = null;
			if (!isCancelled()) {
				array = JSONUtil.buildArray(input);
			}
		
			// parse data
			if (!isCancelled() && array != null) {
				if (array.length() > 0) {
					for (int i = array.length() - 1; i >= 0; --i) {
						if (!isCancelled()) {
							try {
								LobbyItem lob = new LobbyItemParser().parse(array.getJSONObject(i));
								publishProgress(lob);
							}
							catch (JSONException e) {
								Log.e(TAG, "Exception occured in doInBackGround()", e);
							}
						}
					}
				} 
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
		}
	}	
	
	private void updateUi(LobbyItem lob) {
		items.add(lob);
		adapter.notifyDataSetChanged();	
	}
	
	public boolean haveTaskAvailable(AsyncTask<?, ?, ?> task) {
		return ((task == null) || (task != null && task.getStatus() == AsyncTask.Status.FINISHED));
	}
	
	private class IamReadyTask extends AsyncTask<Void, LobbyItem, Boolean> {
		private ProgressDialog progressDialog;
		private String url;
		private List<NameValuePair> extras;
		
		public IamReadyTask(String url, List<NameValuePair> extras) {
			this.url = url;
			this.extras = extras;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LobbyActivity.this);
			progressDialog.setMessage("Flag ready...");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onProgressUpdate(LobbyItem... lob) {
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			if (!isCancelled()) {
				RESTUtil.post(url, extras);
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			checkOtherPlayers();
		}
	}
	
	private class CheckOtherPlayersTask extends AsyncTask<Void, Integer, Boolean> {
		private String url;
		private List<NameValuePair> extras;
		
		public CheckOtherPlayersTask(String url, List<NameValuePair> extras) {
			this.url = url;
			this.extras = extras;
		}
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected void onProgressUpdate(Integer... lob) {
			UiUtil.showText(LobbyActivity.this, "Someone is not ready!");
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			InputStream input = null;
			while (!isCancelled()) {
				input = RESTUtil.post(url, extras);
				JSONObject json = JSONUtil.buildObject(input);
				int r = -1;
				if (json.has("launch")) {
					try {
						r = json.getInt("launch");
						publishProgress(r);
						if (r == 1) {
							break;
						}
					} catch (JSONException e) {
						// ignore
					}
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			UiUtil.showText(LobbyActivity.this, "Your game will be starting in 1 second!");
			startGame();
		}
	}
	
	private void checkOtherPlayers() {
		UiUtil.showText(this, "Periodically check others...");
		if (haveTaskAvailable(checkTask)) {
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			p.add(new BasicNameValuePair("room_id", Integer.toString(SingletonUser.getActiveUser().getRoomId())));
			checkTask = new CheckOtherPlayersTask(URL_CHECK_OTHERS, p);
			checkTask.execute();
		}
	}
	
	private void startGame() {
		startActivity(new Intent(LobbyActivity.this, NumberGameActivity.class));
		finish();
	}
}	