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
import com.csun.greenapp.utils.SingletonUser;
import com.csun.greenapp.utils.UiUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class NumberGameActivity extends FragmentActivity {
	private static final String TAG = NumberGameActivity.class.getSimpleName();
	private static final String URL_SUBMIT_STRIKE = "http://bookboi.com/chan/greenapp/ss12_submit.php";
	private static final String URL_UPDATE_STATE = "http://bookboi.com/chan/greenapp/ss12_update.php";
	private NumberBoardView boardView;
	private GameTask gameTask;
	private StrikeTask strikeTask = null;
	private TextView score;
	private SoundPool soundPool;
	private int sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		gameTask = new GameTask(URL_UPDATE_STATE);
		gameTask.execute();
		setUpBoardView();
		setUpScoreView();
		setUpSoundEffects();
	}

	private void setUpSoundEffects() {
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sound = soundPool.load(this, R.raw.beep, 1);
		soundPool.play(sound, 1.0f, 1.0f, 1, -1, 1.0f);
	}
	
	private void setUpScoreView() {
		score = (TextView) findViewById(R.id.activity_main_XML_textview_user_score);
		score.setTextColor(Color.RED);
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
						soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f);
						if (haveTaskAvailable(strikeTask)) {
							strikeTask = new StrikeTask(URL_SUBMIT_STRIKE, Integer.toString(number));
							strikeTask.execute();
						}
						updateScore();
					} else {
						Vibrator vib = (Vibrator) getSystemService(NumberGameActivity.VIBRATOR_SERVICE);
						vib.vibrate(300);
					}

					UiUtil.showText(NumberGameActivity.this,
							Integer.toString(boardView.getNumberAt(event)));
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

	private void showGameoverDialog() {
		FragmentManager fm = getSupportFragmentManager();
		GameoverDialog d = new GameoverDialog();
		d.show(fm, "Gameover");
	}

	private class GameTask extends AsyncTask<Void, Void, Boolean> {
		private String url;

		public GameTask(String url) {
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... b) {
			boardView.invalidate();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			while (!isCancelled() && !isGameOver()) {
				InputStream input = RESTUtil.get(url);
				// build JSON array
				JSONArray array = null;
				if (!isCancelled()) {
					array = JSONUtil.buildArray(input);
				}
				// parse data
				if (!isCancelled() && array != null) {
					if (array.length() > 0) {
						int current = 0;
						synchronized (this) {
							current = boardView.getStateSize();
						}
						for (int i = current; i < array.length(); ++i) {
							if (!isCancelled()) {
								GameState s = null;
								try {
									s = new GameStateParser().parse(array
											.getJSONObject(i));
									boardView.addNewState(s);
								} catch (JSONException e) {
									Log.e(TAG, "Parsing exception " + e, e);
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
		protected void onPostExecute(Boolean result) {
			showGameoverDialog();
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
			extras.add(new BasicNameValuePair("user_id", Integer
					.toString(SingletonUser.getActiveUser().getId())));
			extras.add(new BasicNameValuePair("number", number));
			RESTUtil.post(url, extras);
			return null;
		}

		@Override
		protected void onPostExecute(Void input) {

		}
	}

	private void updateScore() {
		score.setText(Integer.toString(boardView.getUserScore(SingletonUser
				.getActiveUser().getId())));
	}

	private synchronized boolean isGameOver() {
		if (boardView != null) {
			return boardView.haveAllNumbersCrossed();
		}
		return false;
	}

	public boolean haveTaskAvailable(AsyncTask<?, ?, ?> task) {
		return ((task == null) || (task != null && task.getStatus() == AsyncTask.Status.FINISHED));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
		// super.onSaveInstanceState(outState);
	}
}
