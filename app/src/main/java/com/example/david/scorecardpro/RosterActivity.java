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

        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);
        battingOrderToSend.add(null);

        System.out.println("We have reached onCreate in RosterActivity");

        playerListView = (ListView) findViewById(R.id.player_List_View);
        ArrayAdapter<Player> adapter = new ArrayAdapter <Player> (this, android.R.layout.simple_list_item_1, team.getPlayers());
        playerListView.setAdapter(adapter);

        Intent arrived = getIntent();

        if (arrived.hasExtra("homeTeam"))
        {
            isHomeTeam = arrived.getBooleanExtra("Home Team", false);
            awayTeamBattingOrderToSend = (ArrayList<Player>)arrived.getSerializableExtra("awayTeamLineup");
            awayTeamPositionsInGameToSend = (ArrayList<PositionsInGame>)arrived.getSerializableExtra("awayTeamPositions");
        }

        homeTeamName = arrived.getStringExtra("homeTeamName");
        awayTeamName = arrived.getStringExtra("awayTeamName");

        setTeamNameTextField();

        System.out.println("awayTeamName = " + awayTeamName + " hasExtra = " + arrived.hasExtra("homeTeamName"));

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent.requestFocusFromTouch();
                parent.setSelection(position);
                System.out.println(parent.getSelectedItemPosition());

                currentPlayer = (Player)parent.getItemAtPosition(position);

          //      String s = String.valueOf(parent.getItemAtPosition(position));

           //     Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
    TextView battingOrder1TextView;
    TextView battingOrder2TextView;
    TextView battingOrder3TextView;
    TextView battingOrder4TextView;
    TextView battingOrder5TextView;
    TextView battingOrder6TextView;
    TextView battingOrder7TextView;
    TextView battingOrder8TextView;
    TextView battingOrder9TextView;
    TextView teamNameTextView;

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
        battingOrder1TextView = (TextView)findViewById(R.id.battingOrder1);
        battingOrder2TextView = (TextView)findViewById(R.id.battingOrder2);
        battingOrder3TextView = (TextView)findViewById(R.id.battingOrder3);
        battingOrder4TextView = (TextView)findViewById(R.id.battingOrder4);
        battingOrder5TextView = (TextView)findViewById(R.id.battingOrder5);
        battingOrder6TextView = (TextView)findViewById(R.id.battingOrder6);
        battingOrder7TextView = (TextView)findViewById(R.id.battingOrder7);
        battingOrder8TextView = (TextView)findViewById(R.id.battingOrder8);
        battingOrder9TextView = (TextView)findViewById(R.id.battingOrder9);
        teamNameTextView = (TextView)findViewById(R.id.teamName);
    }

    Team team;

    public void setTeamNameTextField ()
    {
        catcherFieldView.setText("Catcher");
        pitcherFieldView.setText("Pitcher");
        firstBaseFieldView.setText("FirstBase");
        secondBaseFieldView.setText("SecondBase");
        thirdBaseFieldView.setText("ThirdBase");
        shortStopFieldView.setText("ShortStop");
        centerFieldView.setText("CenterField");
        leftFieldView.setText("LeftField");
        rightFieldView.setText("RightField");
        battingOrder1TextView.setText("1");
        battingOrder2TextView.setText("2");
        battingOrder3TextView.setText("3");
        battingOrder4TextView.setText("4");
        battingOrder5TextView.setText("5");
        battingOrder6TextView.setText("6");
        battingOrder7TextView.setText("7");
        battingOrder8TextView.setText("8");
        battingOrder9TextView.setText("9");
           if (!isHomeTeam)
           {
               System.out.println("Away team name was set to " + awayTeamName);
               teamNameTextView.setText(awayTeamName);
           }

           else
           {
               System.out.println("Home team name was set to " + homeTeamName);
               teamNameTextView.setText(homeTeamName);
           }
    }

    public void selectedPosition (View b)
    {
        System.out.println("We reached selectedPosition");
        TextView local =  (TextView)b;

    //    Player player = (Player)playerListView.getSelectedItem();
    //    System.out.println("Player = " + player);

        local.setText(currentPlayer.getLastName());

        PositionsInGame newPositionsInGameToAdd = null;
        switch (b.getId())
        {
            case R.id.catcherFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.CATCHER);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.pitcherFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.PITCHER);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.firstBaseFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.FIRSTBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.secondBaseFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.SECONDBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.thirdBaseFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.THIRDBASE);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.shortStopFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.SHORTSTOP);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.centerFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.CENTERFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.leftFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.LEFTFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
            case R.id.rightFieldViewRoster : newPositionsInGameToAdd = new PositionsInGame(currentPlayer, Positions.RIGHTFIELD);
                positionsInGameToSend.add(newPositionsInGameToAdd);
                break;
        }

        System.out.println("Size of positionsInGameToSend = " + positionsInGameToSend.size());
        for (PositionsInGame p : new ArrayList<PositionsInGame>( positionsInGameToSend ))
        {
            System.out.println("p.getPlayer() = " + p.getPlayer());
            System.out.println("currentPlayer = " + currentPlayer);

            if (p == newPositionsInGameToAdd)
            {
                if (p.getPlayer() != currentPlayer)
                {
                    local.setText(currentPlayer.getLastName());
                }
            }

            else
            {
                if (p.getPlayer() == currentPlayer)
                {
                    System.out.println("Size of positionsInGameToSend = " + positionsInGameToSend.size());
                    clearPositionText(p.getPosition());
                    positionsInGameToSend.remove(p);
                }
            }
        }
    }

    private void clearPositionText (Positions position)
    {
        switch (position)
        {
            case PITCHER:
                TextView tv = (TextView)findViewById(R.id.pitcherFieldViewRoster);
                tv.setText("Pitcher");
                break;
            case CATCHER:
                TextView tv1 = (TextView)findViewById(R.id.catcherFieldViewRoster);
                tv1.setText("Catcher");
                break;
            case FIRSTBASE:
                TextView tv2 = (TextView)findViewById(R.id.firstBaseFieldViewRoster);
                tv2.setText("FirstBase");
                break;
            case SECONDBASE:
                TextView tv3 = (TextView)findViewById(R.id.secondBaseFieldViewRoster);
                tv3.setText("SecondBase");
                break;
            case THIRDBASE:
                TextView tv4 = (TextView)findViewById(R.id.thirdBaseFieldViewRoster);
                tv4.setText("ThirdBase");
                break;
            case SHORTSTOP:
                TextView tv5 = (TextView)findViewById(R.id.shortStopFieldViewRoster);
                tv5.setText("ShortStop");
                break;
            case CENTERFIELD:
                TextView tv6 = (TextView)findViewById(R.id.centerFieldViewRoster);
                tv6.setText("CenterField");
                break;
            case LEFTFIELD:
                TextView tv7 = (TextView)findViewById(R.id.leftFieldViewRoster);
                tv7.setText("LeftField");
                break;
            case RIGHTFIELD:
                TextView tv8 = (TextView)findViewById(R.id.rightFieldViewRoster);
                tv8.setText("RightField");
                break;
        }
    }

    private void clearPositionTextBattingOrder (int position)
    {
        switch (position)
        {
            case 0:
                TextView tv = (TextView)findViewById(R.id.battingOrder1);
                tv.setText("");
                break;
            case 1:
                TextView tv1 = (TextView)findViewById(R.id.battingOrder2);
                tv1.setText("");
                break;
            case 2:
                TextView tv2 = (TextView)findViewById(R.id.battingOrder3);
                tv2.setText("");
                break;
            case 3:
                TextView tv3 = (TextView)findViewById(R.id.battingOrder4);
                tv3.setText("");
                break;
            case 4:
                TextView tv4 = (TextView)findViewById(R.id.battingOrder5);
                tv4.setText("");
                break;
            case 5:
                TextView tv5 = (TextView)findViewById(R.id.battingOrder6);
                tv5.setText("");
                break;
            case 6:
                TextView tv6 = (TextView)findViewById(R.id.battingOrder7);
                tv6.setText("");
                break;
            case 7:
                TextView tv7 = (TextView)findViewById(R.id.battingOrder8);
                tv7.setText("");
                break;
            case 8:
                TextView tv8 = (TextView)findViewById(R.id.battingOrder9);
                tv8.setText("");
                break;
        }
    }

    public void addPlayerToBattingOrder(View b)
    {
        System.out.println("size of batting order = " + battingOrderToSend.size());

        TextView local =  (TextView)b;
        local.setText(currentPlayer.getLastName());

            switch (b.getId()) {
                case R.id.battingOrder1:
                    battingOrderToSend.set(0, currentPlayer);
                    break;
                case R.id.battingOrder2:
                    battingOrderToSend.set(1, currentPlayer);
                    break;
                case R.id.battingOrder3:
                    battingOrderToSend.set(2, currentPlayer);
                    break;
                case R.id.battingOrder4:
                    battingOrderToSend.set(3, currentPlayer);
                    break;
                case R.id.battingOrder5:
                    battingOrderToSend.set(4, currentPlayer);
                    break;
                case R.id.battingOrder6:
                    battingOrderToSend.set(5, currentPlayer);
                    break;
                case R.id.battingOrder7:
                    battingOrderToSend.set(6, currentPlayer);
                    break;
                case R.id.battingOrder8:
                    battingOrderToSend.set(7, currentPlayer);
                    break;
                case R.id.battingOrder9:
                    battingOrderToSend.set(8, currentPlayer);
                    break;
        }
/*
        int counter = 0;
        for (Player p : new ArrayList<Player>( battingOrderToSend ))
        {
            System.out.println("p.getPlayer() = " + p);
            System.out.println("currentPlayer = " + currentPlayer);

            if (p != currentPlayer)
            {
                local.setText(currentPlayer.getLastName());
            }


            if (p == currentPlayer)
            {
                clearPositionTextBattingOrder(counter);
                battingOrderToSend.remove(p);
            }
            counter++;
        }
        */
    }

    public void launchGame (View b)
    {
        System.out.println("isHomeTeam = " + isHomeTeam);
        if (!isHomeTeam)
        {
            awayTeamPositionsInGameToSend = positionsInGameToSend;
            awayTeamBattingOrderToSend= battingOrderToSend;
            isHomeTeam = true;
            setTeamNameTextField();
            positionsInGameToSend.clear();
            battingOrderToSend.clear();
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            battingOrderToSend.add(null);
            return;
        }
        else
        {
            Intent i = new Intent(this, ScoringActivity.class);
            i.putExtra("awayTeamLineup", awayTeamBattingOrderToSend);
            i.putExtra("homeTeamLineup", battingOrderToSend);
            i.putExtra("awayTeamPositions", awayTeamPositionsInGameToSend);
            i.putExtra("homeTeamPositions", positionsInGameToSend);
            i.putExtra("homeTeamName", homeTeamName);
            i.putExtra("awayTeamName", awayTeamName);

            startActivity(i);
        }
    }

    public void checkFields (View b)
    {
        if (battingOrderToSend.size() == 9 && positionsInGameToSend.size() == 9)
        {
            launchGame(b);
        }

        else if (battingOrderToSend.size() != 9 && positionsInGameToSend.size() != 9)
        {
            String t = "Your batting order must have 9 players in it and every position must be filled!";
            Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
        }

        else if (battingOrderToSend.size() == 9 && positionsInGameToSend.size() != 9)
        {
            String t = "Every position must be filled!";
            Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
        }

        else if (battingOrderToSend.size() != 9 && positionsInGameToSend.size() == 9)
        {
            String t = "Your batting order must have 9 players in it!";
            Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
        }
    }

    public void repOk() {
        assert (battingOrderToSend.size() == 9);
    }

    private ArrayList<Player> battingOrderToSend = new ArrayList<>();
    private ArrayList <PositionsInGame> positionsInGameToSend = new ArrayList<>();
    private ArrayList<Player> awayTeamBattingOrderToSend = new ArrayList<>();
    private ArrayList <PositionsInGame> awayTeamPositionsInGameToSend = new ArrayList<>();
    private ListView playerListView;
    private Player currentPlayer;
    private boolean isHomeTeam;
    private String homeTeamName;
    private String awayTeamName;
}
