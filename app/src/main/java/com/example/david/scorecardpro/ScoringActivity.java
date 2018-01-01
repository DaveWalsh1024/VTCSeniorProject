package com.example.david.scorecardpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jacklavallee on 11/28/17.
 */

public class ScoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent go;
        switch(item.getItemId())
        {
            case R.id.login:
                go = new Intent(this,LoginActivity.class);
                startActivity(go);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int inningCount = 1;
    Inning currentInning = null;
    Team homeTeam = null;
    Team awayTeam = null;
    TeamInGame homeTeamInGame;
    TeamInGame awayTeamInGame;
    HalfInning currentHalfInning = null;
    int topOrBottom = 1;
    BasePath basePath = new BasePath();
    int homeTeamBattingOrderPosition = 1;
    int awayTeamBattingOrderPosition = 1;
    int currentBattingOrderPosition;
    ArrayList <Player> currentBattingOrder = null;
    ArrayList <PositionsInGame> currentFieldingPositions = null;
    AtBat currentBatter = null;

    Player player1 = new Player("David", "Walsh", 1, 22);
    Player player2 = new Player("Jack", "Lavallee", 2, 24);
    Player player3 = new Player("Joe", "Russell", 3, 35);
    Player player4 = new Player("Craig", "Damon", 4, 55);
    Player player5 = new Player ("Leslie", "Damon", 5, 50);
    Player player6 = new Player ("Peter", "Chapin", 6, 45);
    Player player7 = new Player ("Mike", "Hall", 7, 22);
    Player player8 = new Player ("Matt", "Tanneberger", 8, 21);
    Player player9 = new Player ("Jake", "Morrill", 9, 22);

    ArrayList <Player> homeTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));
    ArrayList <Player> awayTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));

    PositionsInGame pitching = new PositionsInGame(player1, Positions.PITCHER);
    PositionsInGame firstBase = new PositionsInGame(player2, Positions.FIRSTBASE);
    PositionsInGame catcher = new PositionsInGame(player3, Positions.CATCHER);
    PositionsInGame secondBase= new PositionsInGame(player4, Positions.SECONDBASE);
    PositionsInGame shortStop = new PositionsInGame(player5, Positions.SHORTSTOP);
    PositionsInGame thirdBase = new PositionsInGame(player6, Positions.THIRDBASE);
    PositionsInGame centerField = new PositionsInGame(player7, Positions.CENTERFIELD);
    PositionsInGame rightField = new PositionsInGame(player8, Positions.RIGHTFIELD);
    PositionsInGame leftField = new PositionsInGame(player9, Positions.LEFTFIELD);

    ArrayList <PositionsInGame> homeTeamPositions = new ArrayList<>(Arrays.asList(pitching, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));
    ArrayList <PositionsInGame> awayTeamPositions = new ArrayList<>(Arrays.asList(pitching, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));

    public void startGame (View b)
    {
        TextView homeTeamText = (TextView)findViewById(R.id.homeTeam_Text);
        TextView awayTeamText = (TextView)findViewById(R.id.awayTeam_Text);

        homeTeam = new Team(homeTeamText.getText().toString());
        awayTeam = new Team(awayTeamText.getText().toString());

        homeTeamInGame = new TeamInGame(homeTeam);
        awayTeamInGame = new TeamInGame(awayTeam);


        GameType gameType = new GameType("LittleLeage", 6);

        Game game = new Game(homeTeamInGame, awayTeamInGame, gameType);

        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

        TextView homeTeamTitle = (TextView)findViewById(R.id.homeScore_View);
        TextView awayTeamTitle = (TextView)findViewById(R.id.awayScore_View);

        homeTeamTitle.setText(homeTeamText.getText());
        awayTeamTitle.setText(awayTeamText.getText());

        startScoring(game);
    }

    public void startScoring (Game game)
    {
        if (topOrBottom % 2 == 1)
        {
            currentHalfInning = new HalfInning(awayTeam, homeTeam, 1, inningCount, basePath);
            currentBattingOrder = awayTeamBattingOrder;
            currentBattingOrderPosition = awayTeamBattingOrderPosition;
            currentFieldingPositions = homeTeamPositions;
        }

        else
            currentHalfInning = new HalfInning(homeTeam, awayTeam, 2, inningCount, basePath);
            currentBattingOrder = homeTeamBattingOrder;
            currentBattingOrderPosition = homeTeamBattingOrderPosition;
            currentFieldingPositions = awayTeamPositions;

        Inning newInning = new Inning(inningCount, null, null);
        game.addInning(newInning);

       currentInning = game.getInningFromNumber(inningCount);
       currentInning.setInningNumber(inningCount);

       startHalfInning(currentHalfInning);
    }



    public void startHalfInning (HalfInning currentHalfInning)
    {
   //     while (currentHalfInning.getOuts() < 3)
  //      {
        currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));

   //     }
    }

    public void ball (View b)
    {
        TextView ballCount = (TextView)findViewById(R.id.balls_Edit);

        if (currentBatter.getBallCount() < 4)
        {
            currentBatter.incrementBalls();
            ballCount.setText(Integer.toString(currentBatter.getBallCount()));
        }
        else
            ballCount.setText(Integer.toString(0));
            basePath.advanceRunner(basePath.getHomeBase(),basePath.getFirstBase());
            currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
            System.out.println("New currentBatter is set");
    }

    public void strike (View b)
    {
        TextView strikeCount = (TextView)findViewById(R.id.strikes_Edit);

        if (currentBatter.getStrikeCount() < 3)
        {
            currentBatter.incrementStrikes();
            strikeCount.setText(Integer.toString(currentBatter.getStrikeCount()));
        }
        else
            strikeCount.setText(Integer.toString(0));
            currentBatter.getHalfInning().incrementOuts();
            currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
    }
}
