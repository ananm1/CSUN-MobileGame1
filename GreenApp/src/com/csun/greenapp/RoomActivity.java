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
import com.csun.greenapp.types.RoomParser;
import com.csun.greenapp.utils.JSONUtil;
import com.csun.greenapp.utils.RESTUtil;
import com.csun.greenapp.utils.SingletonUser;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class RoomActivity extends FragmentActivity {
	private static final String TAG = RoomActivity.class.getSimpleName();
	private static final String URL_FIND_ROOM = "http://bookboi.com/chan/greenapp/ss12_get_rooms.php";
	private static final String URL_SET_ROOM = "http://bookboi.com/chan/greenapp/ss12_set_room.php";
	private GridView gridview;
	private List<Room> rooms;
	private RoomAdapter adapter;
	private FindRoomTask task;
	private SetRoomTask setRoomTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		setUpViews();
		if (haveTaskAvailable(task)) {
			task = new FindRoomTask(URL_FIND_ROOM);
			task.execute();
		}
	}
	
	private void setUpViews() {
		gridview = (GridView) findViewById(R.id.activity_room_XML_gridview);
		rooms = new ArrayList<Room>();
		adapter = new RoomAdapter(this, rooms);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SingletonUser.getActiveUser().setRoomId(rooms.get(position).getId());
				if (haveTaskAvailable(setRoomTask)) {
					List<NameValuePair> p = new ArrayList<NameValuePair>();
					p.add(new BasicNameValuePair("user_id", Integer.toString(SingletonUser.getActiveUser().getId())));
					p.add(new BasicNameValuePair("room_id", Integer.toString(SingletonUser.getActiveUser().getRoomId())));
					setRoomTask = new SetRoomTask(URL_SET_ROOM, p);
					setRoomTask.execute();
				}
			}
		});
	}
	
	private class SetRoomTask extends AsyncTask<Void, LobbyItem, Boolean> {
		private String url;
		private List<NameValuePair> extras;
		ProgressDialog progressDialog;
		
		public SetRoomTask(String url, List<NameValuePair> extras) {
			this.url = url;
			this.extras = extras;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(RoomActivity.this);
			progressDialog.setMessage("Enter room...");
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
			RoomActivity.this.startActivity(new Intent(RoomActivity.this, LobbyActivity.class));
		}
	}
	
	private class FindRoomTask extends AsyncTask<Void, Room, Boolean> {
		private String url;
		private ProgressDialog progressDialog;
		
		public FindRoomTask(String url) {
			this.url = url;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(RoomActivity.this);
			progressDialog.setMessage("Loading rooms...");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected void onProgressUpdate(Room... r) {
			updateUi(r[0]);
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			InputStream input = null;
			// get stream from network
			if (!isCancelled()) {
				input = RESTUtil.get(url);
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
								Room r = new RoomParser().parse(array.getJSONObject(i));
								publishProgress(r);
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
	
	private void updateUi(Room r) {
		rooms.add(r);
		adapter.notifyDataSetChanged();	
	}
	
	public boolean haveTaskAvailable(AsyncTask<?, ?, ?> task) {
		return ((task == null) || (task != null && task.getStatus() == AsyncTask.Status.FINISHED));
	}
}
