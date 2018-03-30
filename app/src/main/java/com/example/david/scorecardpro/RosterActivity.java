package com.example.david.scorecardpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


        team = new Team("The Awesome Team");

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
        final ArrayAdapter<Player> adapter = new ArrayAdapter <Player> (this, android.R.layout.simple_list_item_1, team.getPlayers());
        playerListView.setAdapter(adapter);

        Intent arrived = getIntent();
        //      if (arrived.hasExtra("Home Team")) {
        setRoster(arrived);
        //      }

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                addPlayerToBattingOrder((Player)playerListView.getSelectedItem());

                String s = String.valueOf(playerListView.getItemAtPosition(i));

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }



    Team team;

    public void addPlayerToBattingOrder(Player player)
    {
        battingOrderToSend.add(player);

        rosterListView = (ListView) findViewById(R.id.roster_List_View);
        ArrayAdapter<Player> rosterAdapter = new ArrayAdapter <Player> (this, android.R.layout.simple_list_item_1, battingOrderToSend);
        playerListView.setAdapter(rosterAdapter);
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
    private ListView playerListView;
    private ListView rosterListView;
}
