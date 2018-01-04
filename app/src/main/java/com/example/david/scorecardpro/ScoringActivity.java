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

import org.w3c.dom.Text;

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
    Game game;

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

    TextView homeTeamTextView;
    TextView awayTeamTextView;
    TextView homeTeamTitleTextView;
    TextView awayTeamTitleTextView;
    TextView currentBattingTeamTextView;
    TextView currentInningTextView;
    TextView currentBatterTextView;
    TextView ballCountTextView;
    TextView strikeCountTextView;
    TextView outCountTextView;
    TextView bottomOrTopTextView;

    public void initializeTextViews ()
    {
        homeTeamTextView = (TextView) findViewById(R.id.homeTeam_Text);
        awayTeamTextView = (TextView) findViewById(R.id.awayTeam_Text);
        homeTeamTitleTextView = (TextView) findViewById(R.id.homeScore_View);
        awayTeamTitleTextView = (TextView) findViewById(R.id.awayScore_View);
        currentBattingTeamTextView = (TextView)findViewById(R.id.currentBattingTeam_View);
        currentInningTextView = (TextView)findViewById(R.id.currentInning_View);
        currentBatterTextView = (TextView)findViewById(R.id.currentBatter_View);
        ballCountTextView = (TextView)findViewById(R.id.balls_Edit);
        strikeCountTextView = (TextView)findViewById(R.id.strikes_Edit);
        outCountTextView = (TextView)findViewById(R.id.outs_Edit);
        bottomOrTopTextView = (TextView)findViewById(R.id.bottomOrTop_View);
    }

    public void startGame (View b)
    {
        initializeTextViews();
        homeTeam = new Team(homeTeamTextView.getText().toString());
        awayTeam = new Team(awayTeamTextView.getText().toString());

        homeTeamInGame = new TeamInGame(homeTeam);
        awayTeamInGame = new TeamInGame(awayTeam);

        GameType gameType = new GameType("LittleLeage", 6);

        game = new Game(homeTeamInGame, awayTeamInGame, gameType);

        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

        homeTeamTitleTextView.setText(homeTeamTextView.getText());
        awayTeamTitleTextView.setText(awayTeamTextView.getText());

        currentHalfInning = new HalfInning(awayTeam, homeTeam, 1, 1, basePath);
        setCurrentFieldingPositions();
        setBattingAndFieldingTextView();
        setCurrentBattingOrder();

        currentInning= new Inning(game, 1, currentHalfInning, null);
        setCurrentInningTextView();
        setTopOrBottomTextView();
        game.addInning(currentInning);

        startHalfInning(currentHalfInning);
    }

    public void startHalfInning (HalfInning currentHalfInning)
    {
        currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
        setCurrentBatterTextView();
    }

    public void setCurrentBatterTextView ()
    {
        currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
    }

    public void setTopOrBottomTextView ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            bottomOrTopTextView.setText("Top");
        }
        else
        {
            bottomOrTopTextView.setText("Bottom");
        }
    }

    public void setCurrentInningTextView ()
    {
        currentInningTextView.setText(Integer.toString(currentInning.getInningCount()));
    }

    public void setBattingAndFieldingTextView ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            currentBattingTeamTextView.setText(awayTeam.getTeamName());
        }
        else
        {
            currentBattingTeamTextView.setText(homeTeam.getTeamName());
        }
    }

    public void ball (View b)
    {
        if (currentBatter.getBallCount() < 3)
        {
            System.out.println("Current ball count is " + currentBatter.getBallCount());
            currentBatter.incrementBalls();
            ballCountTextView.setText(Integer.toString(currentBatter.getBallCount()));
        }
        else
        {
            ballCountTextView.setText(Integer.toString(0));
            basePath.advanceRunner(basePath.getHomeBase(), basePath.getFirstBase());
            currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
            setCurrentBatterTextView();
            System.out.println("4 balls, New currentBatter is set");
        }
    }

    public void strike (View b)
    {
        if (currentBatter.getStrikeCount() < 2)
        {
            System.out.println("Current strike count is " + currentBatter.getStrikeCount());
            currentBatter.incrementStrikes();
            strikeCountTextView.setText(Integer.toString(currentBatter.getStrikeCount()));
        }
        else
        {
            strikeCountTextView.setText(Integer.toString(0));
            incrementOuts(b);
            currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
            currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
            System.out.println("3 Strikes, increment outs and set new current batter");
        }
    }

    public void setCurrentBattingOrder ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            homeTeamBattingOrderPosition = currentBattingOrderPosition;
            currentBattingOrder = awayTeamBattingOrder;
            currentBattingOrderPosition = awayTeamBattingOrderPosition;
        }
        else
        {
            awayTeamBattingOrderPosition = currentBattingOrderPosition;
            currentBattingOrder = homeTeamBattingOrder;
            currentBattingOrderPosition = homeTeamBattingOrderPosition;
        }
    }

    public void setCurrentFieldingPositions ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            awayTeamPositions = currentFieldingPositions;
            currentFieldingPositions = homeTeamPositions;
        }
        else
        {
            homeTeamPositions = currentFieldingPositions;
            currentFieldingPositions = awayTeamPositions;
        }
    }

    public void incrementBattingOrderPosition (int oldPosition)
    {
        if (oldPosition < 8)
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + currentBattingOrderPosition);
            currentBattingOrderPosition++;
            System.out.println("New position is " + currentBattingOrderPosition);
        }

        else
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + currentBattingOrderPosition);
            currentBattingOrderPosition = 0;
            System.out.println("New position is " + currentBattingOrderPosition);
        }
    }

    public void incrementOuts (View b)
    {
        incrementBattingOrderPosition(currentBattingOrderPosition);

        if (currentBatter.getHalfInning().getOuts() < 2)
        {
            System.out.println("Current out count is " + currentBatter.getHalfInning().getOuts());
            currentHalfInning.incrementOuts();
            outCountTextView.setText(Integer.toString(currentBatter.getHalfInning().getOuts()));
            currentBatter = new AtBat(currentHalfInning, basePath, currentBattingOrder.get(currentBattingOrderPosition));
            currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
        }

        else
        {
            System.out.println("There are now 3 outs, setting up next half inning");
            System.out.println("Current Inning Count is " + currentInning.getInningCount());
            strikeCountTextView.setText(Integer.toString(0));
            ballCountTextView.setText(Integer.toString(0));
            outCountTextView.setText(Integer.toString(0));

            basePath = new BasePath();

            if (currentHalfInning.topOrBottom() == 1) //currently the top of the inning
            {
                System.out.println("The new half inning will be the bottom of the " + currentInning.getInningCount());
                currentHalfInning = new HalfInning(awayTeam, homeTeam, 2, currentInning.getInningCount(), basePath);
            }
            else
            {
                System.out.println("New Inning. Setting up top of the new inning. The inning number is " + currentInning.getInningCount());
                currentInning.incrementInningNumber();
                currentHalfInning = new HalfInning(homeTeam, awayTeam, 1, currentInning.getInningCount(), basePath);
                currentInning = new Inning(game, currentInning.getInningCount(), currentHalfInning, null);
                game.addInning(currentInning);

                startHalfInning(currentHalfInning);
            }

            setTopOrBottomTextView();
            setCurrentInningTextView();
            setBattingAndFieldingTextView();
            setCurrentFieldingPositions();
            setCurrentBattingOrder();
        }
    }
}
