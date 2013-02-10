//Chris Bowles - 2/10/13
//SS12 Coding Competition 2013 Project
//GameActivity.java

package com.patternresponse;

import java.util.Random;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class GameActivity extends Activity
{
	private TextView textView1, textView2, textView3, textView5;
	private ImageView imageView0, imageView1, imageView2, imageView3, imageView4;
	private MediaPlayer note1, note2, note3, note4, begin, send, receive, win, lose;
	private Random generator;
	private boolean multiplayer, yourTurn, control;
	private int turn, subTurn, option;
	private int[] notes;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		textView1 = (TextView)findViewById(R.id.gameTextView1);
		textView2 = (TextView)findViewById(R.id.gameTextView2);
		textView3 = (TextView)findViewById(R.id.gameTextView3);
		textView5 = (TextView)findViewById(R.id.gameTextView5);
		imageView0 = (ImageView)findViewById(R.id.gameImageView0);
		imageView1 = (ImageView)findViewById(R.id.gameImageView1);
		imageView2 = (ImageView)findViewById(R.id.gameImageView2);
		imageView3 = (ImageView)findViewById(R.id.gameImageView3);
		imageView4 = (ImageView)findViewById(R.id.gameImageView4);
		imageView1.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (yourTurn && control)
				{
					option = 1;
					noControl();
					changeImage();
					if (multiplayer)
					{
						//other.option = 1;
						//other.changeImage();
					}
				}
				return false;
			}
		});
		imageView2.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (yourTurn && control)
				{
					option = 2;
					noControl();
					changeImage();
					if (multiplayer)
					{
						//other.option = 2;
						//other.changeImage();
					}
				}
				return false;
			}
		});
		imageView3.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (yourTurn && control)
				{
					option = 3;
					noControl();
					changeImage();
					if (multiplayer)
					{
						//other.option = 3;
						//other.changeImage();
					}
				}
				return false;
			}
		});
		imageView4.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (yourTurn && control)
				{
					option = 4;
					noControl();
					changeImage();
					if (multiplayer)
					{
						//other.option = 4;
						//other.changeImage();
					}
				}
				return false;
			}
		});
		
		note1 = new MediaPlayer();
		note2 = new MediaPlayer();
		note3 = new MediaPlayer();
		note4 = new MediaPlayer();
		begin = new MediaPlayer();
		send = new MediaPlayer();
		receive = new MediaPlayer();
		win = new MediaPlayer();
		lose = new MediaPlayer();
		try
		{
			note1.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.note1));
			note1.setAudioStreamType(AudioManager.STREAM_RING);
			note2.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.note2));
			note2.setAudioStreamType(AudioManager.STREAM_RING);
			note3.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.note3));
			note3.setAudioStreamType(AudioManager.STREAM_RING);
			note4.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.note4));
			note4.setAudioStreamType(AudioManager.STREAM_RING);
			begin.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.begin));
			begin.setAudioStreamType(AudioManager.STREAM_RING);
			send.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.send));
			send.setAudioStreamType(AudioManager.STREAM_RING);
			receive.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.receive));
			receive.setAudioStreamType(AudioManager.STREAM_RING);
			win.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win));
			win.setAudioStreamType(AudioManager.STREAM_RING);
			lose.setDataSource(getBaseContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.lose));
			lose.setAudioStreamType(AudioManager.STREAM_RING);
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Error: Unable to load audio...", Toast.LENGTH_LONG).show();
		}
		note1.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				note1.stop();
				imageView0.setImageResource(R.drawable.empty);
				if (yourTurn)
				{
					gameLogic();
				}
				else if (!yourTurn && !multiplayer)
				{
					computerLogic();
				}
			}
		});
		note2.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				note2.stop();
				imageView0.setImageResource(R.drawable.empty);
				if (yourTurn)
				{
					gameLogic();
				}
				else if (!yourTurn && !multiplayer)
				{
					computerLogic();
				}
			}
		});
		note3.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				note3.stop();
				imageView0.setImageResource(R.drawable.empty);
				if (yourTurn)
				{
					gameLogic();
				}
				else if (!yourTurn && !multiplayer)
				{
					computerLogic();
				}
			}
		});
		note4.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				note4.stop();
				imageView0.setImageResource(R.drawable.empty);
				if (yourTurn)
				{
					gameLogic();
				}
				else if (!yourTurn && !multiplayer)
				{
					computerLogic();
				}
			}
		});
		begin.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				begin.stop();
				changeContent();
			}
		});
		send.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				send.stop();
				changeContent();
			}
		});
		receive.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				receive.stop();
				changeContent();
			}
		});
		win.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				win.stop();
				finish();
			}
		});
		lose.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				lose.stop();
				finish();
			}
		});
		
		generator = new Random();
		multiplayer = getIntent().getBooleanExtra("opponent", false);
		yourTurn = getIntent().getBooleanExtra("order", false);
		turn = 0;
		textView5.setText(Integer.toString(turn + 1));
		subTurn = 0;
		textView3.setText(Integer.toString(subTurn + 1));
		notes = new int[16];
		control = false;
		if (multiplayer)
		{
			textView1.setText("2-Player Game");
		}
		else
		{
			textView1.setText("1-Player Game");
		}
		try
		{
			if (begin.isPlaying())
			{
				begin.stop();
			}
			begin.prepare();
			begin.start();
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Error: Unable to prepare begin audio...", Toast.LENGTH_LONG).show();
		}
	}
	
	private void changeContent()
	{
		if (yourTurn)
		{
			if (turn == 0)
			{
				textView2.setText("To start the game, play a note on the bottom of the screen.");
			}
			else
			{
				textView2.setText("Now it is your turn. Good luck!");
			}
			yesControl();
		}
		else
		{
			textView2.setText("Memorize this pattern and replay it exactly as is, with one additional note afterwards:");
			noControl();
			if (!multiplayer)
			{
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				computerChoice();
			}
		}
	}
	
	private void yesControl()
	{
		control = true;
		imageView1.setImageResource(R.drawable.image1);
		imageView2.setImageResource(R.drawable.image2);
		imageView3.setImageResource(R.drawable.image3);
		imageView4.setImageResource(R.drawable.image4);
	}
	
	private void noControl()
	{
		control = false;
		imageView1.setImageResource(R.drawable.image5);
		imageView2.setImageResource(R.drawable.image6);
		imageView3.setImageResource(R.drawable.image7);
		imageView4.setImageResource(R.drawable.image8);
	}
	
	private void changeImage()
	{
		switch (option)
		{
		case 1:
			imageView0.setImageResource(R.drawable.image1);
			try
			{
				if (note1.isPlaying())
				{
					note1.stop();
				}
				note1.prepare();
				note1.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare note1 audio...", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			imageView0.setImageResource(R.drawable.image2);
			try
			{
				if (note2.isPlaying())
				{
					note2.stop();
				}
				note2.prepare();
				note2.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare note2 audio...", Toast.LENGTH_LONG).show();
			}
			break;
		case 3:
			imageView0.setImageResource(R.drawable.image3);
			try
			{
				if (note3.isPlaying())
				{
					note3.stop();
				}
				note3.prepare();
				note3.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare note3 audio...", Toast.LENGTH_LONG).show();
			}
			break;
		case 4:
			imageView0.setImageResource(R.drawable.image4);
			try
			{
				if (note4.isPlaying())
				{
					note4.stop();
				}
				note4.prepare();
				note4.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare note4 audio...", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private void gameLogic()
	{
		if (subTurn < turn)
		{
			if (option == notes[subTurn])
			{
				subTurn++;
				textView3.setText(Integer.toString(subTurn + 1));
				yesControl();
			}
			else
			{
				defeat();
				if (multiplayer)
				{
					//otherPlayer.victory();
				}
			}
		}
		else if (subTurn == turn)
		{
			notes[turn] = option;
			subTurn = 0;
			textView3.setText(Integer.toString(subTurn + 1));
			turn++;
			textView5.setText(Integer.toString(turn + 1));
			if (multiplayer)
			{
				//otherPlayer.notes[turn] = option;
				//otherPlayer.turn++;
			}
			if (turn == notes.length)
			{
				victory();
				if (multiplayer)
				{
					//otherPlayer.defeat();
				}
			}
			else
			{
				changeTurn();
				if (multiplayer)
				{
					//otherPlayer.changeTurn();
				}
			}
		}
	}
	
	private void changeTurn()
	{
		if (yourTurn)
		{
			yourTurn = false;
			try
			{
				if (send.isPlaying())
				{
					send.stop();
				}
				send.prepare();
				send.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare send audio...", Toast.LENGTH_LONG).show();
			}
		}
		else if (!yourTurn)
		{
			yourTurn = true;
			try
			{
				if (receive.isPlaying())
				{
					receive.stop();
				}
				receive.prepare();
				receive.start();
			}
			catch (Exception e)
			{
				Toast.makeText(getBaseContext(), "Error: Unable to prepare receive audio...", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private void computerChoice()
	{
		int[] correctness = {95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20};
		
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		if (subTurn < turn)
		{
			if (generator.nextInt(100) < correctness[subTurn])
			{
				option = notes[subTurn];
			}
			else
			{
				option = generator.nextInt(4) + 1;
			}
		}
		else if (subTurn == turn)
		{
			option = generator.nextInt(4) + 1;
		}
		changeImage();
	}
	
	private void computerLogic()
	{
		if (subTurn < turn)
		{
			if (option == notes[subTurn])
			{
				subTurn++;
				textView3.setText(Integer.toString(subTurn + 1));
				computerChoice();
			}
			else
			{
				victory();
			}
		}
		else if (subTurn == turn)
		{
			notes[turn] = option;
			subTurn = 0;
			textView3.setText(Integer.toString(subTurn + 1));
			turn++;
			textView5.setText(Integer.toString(turn + 1));
			if (turn == notes.length)
			{
				defeat();
			}
			else
			{
				changeTurn();
			}
		}
	}
	
	private void victory()
	{
		try
		{
			if (win.isPlaying())
			{
				win.stop();
			}
			win.prepare();
			win.start();
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Error: Unable to prepare win audio...", Toast.LENGTH_LONG).show();
		}
		textView2.setText("You win!");
		noControl();
	}
	
	private void defeat()
	{
		try
		{
			if (lose.isPlaying())
			{
				lose.stop();
			}
			lose.prepare();
			lose.start();
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Error: Unable to prepare lose audio...", Toast.LENGTH_LONG).show();
		}
		textView2.setText("You lose...");
		noControl();
	}
	
	@Override
	protected void onDestroy() //This is called whenever the Activity is destroyed
	{
		note1.release();
		note2.release();
		note3.release();
		note4.release();
		begin.release();
		send.release();
		receive.release();
		win.release();
		lose.release();
		super.onDestroy();
	}
}