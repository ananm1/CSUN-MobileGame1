package com.changethis.greenapplobby;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter; //for the ListView's ArrayAdapter
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent; //for switching to the GameScreen activity

public class LobbyScreen extends Activity
{
	private TextView userIdLabel;
	private ListView listView;
	private Button startGameButton;
	private int maxPlayers = 3; //the maximum number of players in a game
	private boolean waiting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lobby_screen);
		
		Random generator = new Random();
		int userId = generator.nextInt(1000000);
		/* BELOW IS PSEUDO-CODE FOR MAKING SURE THAT THE USERID IS ALWAYS UNIQUE
		for (int i = 0; i < database.userCount(); i++)
		{
			if (userId == Integer.parseInt(database.getUser(i).getId()))
			{
				userId = generator.nextInt(1000000);
				i = 0;
			}
		}*/
		
		userIdLabel = (TextView)findViewById(R.id.lobbyUserId);
		listView = (ListView)findViewById(R.id.lobbyListView);
		startGameButton = (Button)findViewById(R.id.lobbyStartGame);
		waiting = false;
		
		userIdLabel.setText(Integer.toString(userId));
		startGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//database.addNewUser(userId); //ADD A NEW USER
				startGameButton.setText("Waiting...");
				waiting = true;
			}
		});
	}
	
	protected void onResume()
	{
		super.onResume();
		startGameButton.setText("Start Game");
		waiting = false;
	}
	
	protected void onEveryCycle() //this runs to update the list and checks when to enter a new game
	{
		ArrayList<String> users = new ArrayList<String>();
		
		/* PSEUDO CODE FOR POPULATING THE LIST OF PLAYERS IN THE LOBBY
		for (int i = 0; i < database.userCount(); i++)
		{
			users.add(database.GetUser(i).getName());
		}
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users));
		*/
		/* PESUDO CODE FOR CHECKING WHETHER TO ENTER THE GAME OR NOT
		if (waiting && database.userCount() >= maxPlayers)
		{
			database.removeUser(userId);
			database.addPlayCharacter(userId);
			startActivity(new Intent(LobbyScreen.this, GameScreen.class));
		}*/
	}
}