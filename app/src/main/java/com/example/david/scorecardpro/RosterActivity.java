package com.example.david.scorecardpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by david on 3/21/2018.
 */

public class RosterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roster);

        initializeViews();

        team = new Team("The Awesome Team");

        team.addPlayerToTeam(player1);
        team.addPlayerToTeam(player2);
        team.addPlayerToTeam(player3);
        team.addPlayerToTeam(player4);
        team.addPlayerToTeam(player5);
        team.addPlayerToTeam(player6);
        team.addPlayerToTeam(player7);
        team.addPlayerToTeam(player8);
        team.addPlayerToTeam(player9);
        team.addPlayerToTeam(player10);
        team.addPlayerToTeam(player11);
        team.addPlayerToTeam(player12);
        team.addPlayerToTeam(player13);

        this.battingOrderToSend = new ArrayList<>();

        System.out.println("We have reached onCreate in RosterActivity");

        playerListView = (ListView) findViewById(R.id.player_List_View);
        ArrayAdapter<Player> adapter = new ArrayAdapter <Player> (this, android.R.layout.simple_list_item_1, team.getPlayers());
        playerListView.setAdapter(adapter);

        Intent arrived = getIntent();
        //      if (arrived.hasExtra("Home Team")) {
        setRoster(arrived);
        //      }

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent.requestFocusFromTouch();
                parent.setSelection(position);
                System.out.println(parent.getSelectedItemPosition());

                System.out.println("Get selected item position " + position);

                currentPlayer = (Player)parent.getItemAtPosition(position);

                addPlayerToBattingOrder((Player)parent.getItemAtPosition(position));

                String s = String.valueOf(parent.getItemAtPosition(position));

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    Player player1 = new Player("David", "Walsh", 1, 22);
    Player player2 = new Player("Jack", "Lavallee", 2, 24);
    Player player3 = new Player("Joe", "Russell", 3, 35);
    Player player4 = new Player("Craig", "Damon", 4, 55);
    Player player5 = new Player("Leslie", "Damon", 5, 50);
    Player player6 = new Player("Peter", "Chapin", 6, 55);
    Player player7 = new Player("Mike", "Hall", 7, 22);
    Player player8 = new Player("Matt", "Tanneberger", 8, 21);
    Player player9 = new Player("Jake", "Morrill", 9, 22);
    Player player10 = new Player("Travis", "Hart", 10, 23);
    Player player11 = new Player("Jen", "Russell", 11, 34);
    Player player12 = new Player("Elias", "Carter", 12, 24);
    Player player13 = new Player("Donald", "Trump", 13, 75);

    TextView pitcherFieldView;
    TextView catcherFieldView;
    TextView firstBaseFieldView;
    TextView secondBaseFieldView;
    TextView thirdBaseFieldView;
    TextView shortStopFieldView;
    TextView leftFieldView;
    TextView centerFieldView;
    TextView rightFieldView;

    public void initializeViews ()
    {
        pitcherFieldView = (TextView)findViewById(R.id.pitcherFieldViewRoster);
        catcherFieldView = (TextView)findViewById(R.id.catcherFieldViewRoster);
        firstBaseFieldView = (TextView)findViewById(R.id.firstBaseFieldViewRoster);
        secondBaseFieldView = (TextView)findViewById(R.id.secondBaseFieldViewRoster);
        thirdBaseFieldView = (TextView)findViewById(R.id.thirdBaseFieldViewRoster);
        shortStopFieldView = (TextView)findViewById(R.id.shortStopFieldViewRoster);
        leftFieldView = (TextView)findViewById(R.id.leftFieldViewRoster);
        centerFieldView = (TextView)findViewById(R.id.centerFieldViewRoster);
        rightFieldView = (TextView)findViewById(R.id.rightFieldViewRoster);
    }

    Team team;

    public void selectedPosition (View b)
    {
        System.out.println("We reached selectedPosition");
        TextView local =  (TextView)b;

        String s = local.getText().toString();
        System.out.println(s);
    //    Player player = (Player)playerListView.getSelectedItem();
    //    System.out.println("Player = " + player);


        local.setText(currentPlayer.getLastName());

        PositionsInGame newPositionsInGameToAdd;
        switch (s)
        {
            case "Catcher" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.CATCHER);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "Pitcher" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.PITCHER);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "FirstBase" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.FIRSTBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "SecondBase" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.SECONDBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "ThirdBase" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.THIRDBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "ShortStop" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.SHORTSTOP);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "CenterField" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.CENTERFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "LeftField" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.LEFTFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case "RightField" : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.RIGHTFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
        }

        System.out.println("Size of positionsInGameToSend = " + positionsInGameToSend.size());
       // for (PositionsInGame p : positionsInGameToSend)
        for (int i = 0; i < positionsInGameToSend.size(); i ++)
        {
            System.out.println("p.getPlayer() = " + positionsInGameToSend.get(i).getPlayer());
            System.out.println("currentPlayer = " + currentPlayer);

            if (positionsInGameToSend.get(i).getPlayer() == currentPlayer)
            {
                System.out.println("Size of positionsInGameToSend = " + positionsInGameToSend.size());
                local.setText(positionsInGameToSend.get(i).getPosition().toString());
            }
        }
    }

    public void addPlayerToBattingOrder(Player player)
    {
        System.out.println("Player = " + player);
        battingOrderToSend.add(player);

     //   rosterListView = (ListView) findViewById(R.id.roster_List_View);
     //   ArrayAdapter<Player> rosterAdapter = new ArrayAdapter <Player> (this, android.R.layout.simple_list_item_1, battingOrderToSend);
      //  playerListView.setAdapter(adapter);
    }

    public void removePlayerFromBattingOrder(Player player, int position) {
        battingOrderToSend.remove(player);
    }

    public void setRoster(Intent i) {
        System.out.println("We reached setRoster");

        Intent newIntent = new Intent(this, ScoringActivity.class);

        String homeTeamName = i.getStringExtra("Home Team");
        String awayTeamName = i.getStringExtra("Away Team");

        i.putExtra("Home Team", homeTeamName);
        i.putExtra("Away Team", awayTeamName);

        repOk();

        //    startActivity(i);
    }


    public void repOk() {
        assert (battingOrderToSend.size() == 9);
    }

    private ArrayList<Player> battingOrderToSend;
    private ArrayList <PositionsInGame> positionsInGameToSend = new ArrayList<>();
    private ListView playerListView;
    private Player currentPlayer;
}
