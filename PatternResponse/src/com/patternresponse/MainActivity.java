//Chris Bowles - 2/10/13
//SS12 Coding Competition 2013 Project
//MainActivity.java

package com.patternresponse;

import java.util.Locale;
import java.util.Random;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnInitListener
{
	private TextView textView2, textView3;
	private Button button1, button2;
	private TextToSpeech textToSpeech;
	private Random generator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		textView2 = (TextView)findViewById(R.id.mainTextView2);
		textView3 = (TextView)findViewById(R.id.mainTextView3);
		generator = new Random();
		
		button1 = (Button)findViewById(R.id.mainButton1);
		button1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				intent.putExtra("opponent", false);
				if (generator.nextInt(2) == 1)
				{
					intent.putExtra("order", true);
				}
				else
				{
					intent.putExtra("order", false);
				}
				startActivity(intent);
			}
		});
		
		button2 = (Button)findViewById(R.id.mainButton2);
		button2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//int id = generator.nextInt(1000000);
				boolean first = false;
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				
				if (button2.getText().toString().equals("2-Player Game"))
				{
					button2.setText("Waiting...");
					intent.putExtra("opponent", true);
					/* SERVER PSEUDO CODE
					lobby.addUser(id);
					while (lobby.getUserCount() == 1)
					{
						first = true;
					}
					lobby.removeUser(id);*/
					intent.putExtra("order", first);
					startActivity(intent);
				}
			}
		});
		
		//Initialize the TextToSpeech object
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, 1);
	}

	//Called immediately after startActivityForResult
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Intent installIntent = new Intent();
		
		if (requestCode == 1)
		{
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
			{
				textToSpeech = new TextToSpeech(this, this);
			}
			else
			{
				Toast.makeText(this, "Installing TextToSpeech object...", Toast.LENGTH_LONG).show();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}
	
	//Called during textToSpeech's construction
	@Override
	public void onInit(int status)
	{
		String welcome = (String)textView2.getText() + "\n\n" + (String)textView3.getText();
		
		if (status == TextToSpeech.SUCCESS)
		{
			textToSpeech.setLanguage(Locale.US);
			textToSpeech.speak(welcome, TextToSpeech.QUEUE_FLUSH, null);
		}
		else if (status == TextToSpeech.ERROR)
		{
			Toast.makeText(this, "Error: TextToSpeech failed to initialize...", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onPause()
	{
		if (textToSpeech != null)
		{
			textToSpeech.stop();
		}
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if (textToSpeech != null)
		{
			textToSpeech.speak((String)textView3.getText(), TextToSpeech.QUEUE_FLUSH, null);
		}
		button2.setText("2-Player Game");
	}
	
	@Override
	protected void onDestroy()
	{
		textToSpeech.shutdown();
		super.onDestroy();
	}
}