package com.csun.greenapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.csun.greenapp.types.GameState;
import com.csun.greenapp.types.GameStateParser;
import com.csun.greenapp.utils.JSONUtil;
import com.csun.greenapp.utils.RESTUtil;
import com.csun.greenapp.utils.UiUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class NumberGameActivity extends Activity {
	private static final String URL_SUBMIT_STRIKE = "http://bookboi.com/chan/greenapp/ss12_submit.php";
	private static final String URL_UPDATE_STATE = "http://bookboi.com/chan/greenapp/ss12_update.php";
	private boolean gameOver;
	private NumberBoardView boardView;
	private GameTask gameTask;
	private StrikeTask strikeTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		gameOver = false;
		gameTask = new GameTask(URL_UPDATE_STATE);
		gameTask.execute();
		setUpBoardView();
	}

	private void setUpBoardView() {
		boardView = (NumberBoardView) findViewById(R.id.activity_board_XML_number_board);
		boardView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					int number = boardView.getNumberAt(event);
					if (number != 0) {
						if (haveTaskAvailable(strikeTask)) {
							strikeTask = new StrikeTask(URL_SUBMIT_STRIKE, Integer.toString(number));
							strikeTask.execute();
						}
					}

					UiUtil.showText(NumberGameActivity.this, Integer.toString(boardView.getNumberAt(event)));
					break;

				case MotionEvent.ACTION_UP:
					break;

				case MotionEvent.ACTION_CANCEL:
					break;

				case MotionEvent.ACTION_MOVE:
					break;

				case MotionEvent.ACTION_OUTSIDE:
					break;
				}
				return true;
			}
		});
	}

	private class GameTask extends AsyncTask<Void, Void, Void> {
		private String url;

		public GameTask(String url) {
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... b) {
			// redraw
			boardView.invalidate();
		}

		@Override
		protected Void doInBackground(Void... params) {
			List<GameState> states = new ArrayList<GameState>();
			while (!isCancelled() && !gameOver) {
				InputStream input = RESTUtil.get(url);
				// build JSON array
				JSONArray array = null;
				if (!isCancelled()) {
					array = JSONUtil.buildArray(input);
				}
				// parse data
				if (!isCancelled() && array != null) {
					if (array.length() > 0) {
						for (int i = 0; i < array.length(); ++i) {
							if (!isCancelled()) {
								GameState s = null;
								try {
									s = new GameStateParser().parse(array
											.getJSONObject(i));
									boardView.addNewState(s);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				publishProgress();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void input) {

		}
	}

	private class StrikeTask extends AsyncTask<Void, Void, Void> {
		private String url;
		private String number;

		public StrikeTask(String url, String number) {
			this.url = url;
			this.number = number;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... b) {
			// redraw
			// boardView.invalidate();
		}

		@Override
		protected Void doInBackground(Void... params) {
			List<NameValuePair> extras = new ArrayList<NameValuePair>();
			extras.add(new BasicNameValuePair("user_id", "3"));
			extras.add(new BasicNameValuePair("number", number));
			RESTUtil.post(url, extras);
			return null;
		}

		@Override
		protected void onPostExecute(Void input) {

		}
	}

	public boolean haveTaskAvailable(AsyncTask<?, ?, ?> task) {
		return ((task == null) || (task != null && task.getStatus() == AsyncTask.Status.FINISHED));
	}
}
