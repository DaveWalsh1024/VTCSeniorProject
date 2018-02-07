package com.example.david.scorecardpro;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureStroke;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jacklavallee on 11/28/17.
 */

public class ScoringActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);
 //       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  //      setSupportActionBar(toolbar);



        _gestureName = (TextView)findViewById(R.id.gestureName);
        GestureOverlayView gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestures);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                hitGesture(gestureOverlayView, gesture);
            }


        });
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

            case R.id.activity_scoring:
            go = new Intent(this,ScoringActivity.class);
            startActivity(go);
            return true;

            case R.id.about:
                go = new Intent(this,About.class);
                startActivity(go);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void hitGesture(GestureOverlayView gestureOverlayView, Gesture gesture) {
        _gestureName = (TextView)findViewById(R.id.gestureName);
        gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestures);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                Log.i("Gestures", "Gesture=" + gesture + " with " + gesture.getStrokesCount());
                ArrayList<GestureStroke> strokes = gesture.getStrokes();
                for (GestureStroke stroke : strokes) {
                    Log.i("Gestures", "box=" + stroke.computeOrientedBoundingBox().width + "x"
                            + stroke.computeOrientedBoundingBox().height + "@"
                            + stroke.computeOrientedBoundingBox().centerX + ","
                            + stroke.computeOrientedBoundingBox().centerY + " "
                            + stroke.computeOrientedBoundingBox().orientation);
                    Log.i("Gestures", "points=" + Arrays.toString(stroke.points));


                    if (stroke.computeOrientedBoundingBox().height > 50)
                        _gestureName.setText("Groundball");
                    else
                        _gestureName.setText("Fly ball");

                    for (int i = 0; i <= strokes.size() - 1; i++) {

                        double screenHeight = gestureOverlayView.getHeight();
                        double screenWidth = gestureOverlayView.getWidth();
                        double centerWidth = gestureOverlayView.getWidth() * .5;
                        double infieldY1 = gestureOverlayView.getHeight() * .7;
                        double infieldY2 = gestureOverlayView.getHeight() * .5;
                        double outfieldY = gestureOverlayView.getHeight() * .3;
                        double topStroke = stroke.boundingBox.top;
                        double bottomStroke = stroke.boundingBox.bottom;
                        double leftStroke = stroke.boundingBox.left;
                        double rightStroke = stroke.boundingBox.right;
                        double infieldX1 = gestureOverlayView.getWidth() * .25;
                        double infieldX2 = gestureOverlayView.getWidth() * .75;
                        double outfieldX1 = gestureOverlayView.getWidth() * (1 / 3.0);
                        double outfieldX2 = gestureOverlayView.getWidth() * (2 / 3.0);

                       /* double[] pitcherZone = {infieldY2, infieldX2, infieldY1, infieldX1};
                        double[] catcherZone = {infieldY1, outfieldX2, screenHeight, outfieldX1};
                        double[] firstBaseZone = {outfieldY, screenWidth, infieldY1, infieldX2};
                        double[] secondBaseZone = {outfieldY, infieldX2, infieldY2, centerWidth};
                        double[] thirdBaseZone = {outfieldY, infieldX1, infieldY1, 0.0};
                        double[] shortStopZone = {outfieldY, centerWidth, infieldY2, infieldX1};
                        double[] leftFieldZone = {0.0, outfieldX1, outfieldY, 0.0};
                        double[] centerFieldZone = {0.0, outfieldX2, outfieldY, outfieldX1};
                        double[] rightFieldZone = {0.0, 0.0, outfieldY, outfieldX2};

                        double[] gesturePositions [] = {pitcherZone, catcherZone, firstBaseZone, secondBaseZone, thirdBaseZone, shortStopZone, leftFieldZone, centerFieldZone, rightFieldZone};

                        for (int j = 0; j < gesturePositions.length; j++) {
                            if ((topStroke <= gesturePositions[j][0]) && (rightStroke <= gesturePositions[j][1]) && (topStroke > gesturePositions[j][2]) && (leftStroke <= gesturePositions[j][3])) {
                                _gestureName.append(" to " + (j+1));
                            }
                        }*/



                        // Outfield
                        if (topStroke <= outfieldY) {

                            // Centerfield
                            if ((outfieldX1 < leftStroke) && (outfieldX2 > rightStroke)) {
                                _gestureName.append(" to Centerfield");
                            }

                            // Leftfield
                            else if ((centerWidth - leftStroke) > (rightStroke - centerWidth) && (outfieldX1 > leftStroke)) {
                                _gestureName.append(" to Leftfield");
                            }

                            // Rightfield
                            else if ((centerWidth - leftStroke) < (rightStroke - centerWidth) && (outfieldX2 < rightStroke)) {
                                _gestureName.append(" to Rightfield");
                            } else {
                                _gestureName.setText("TopStroke = " + topStroke +
                                        "\noutfieldY" + outfieldY +
                                        "\n\nLeftStroke = " + leftStroke +
                                        "\noutfieldX1 = " + outfieldX1 +
                                        "\n\nRightStroke = " + rightStroke +
                                        "\noutfieldx2 " + outfieldX2);
                            }
                        }


                        // Catcher
                        else if ((topStroke >= infieldY1) && (outfieldX1 < leftStroke) && (outfieldX2 > rightStroke)) {
                            _gestureName.append(" to Catcher" +
                                    "\nTopStroke = " + topStroke +
                                    "\n\nLeftStroke = " + leftStroke +
                                    "\noutfieldX1 = " + outfieldX1 +
                                    "\n\nRightStroke = " + rightStroke +
                                    "\noutfieldx2 " + outfieldX2);
                        }

                        // Pitcher
                        else if ((topStroke <= infieldY1) && (topStroke >= infieldY2) && (infieldX1 < leftStroke) && (infieldX2 > rightStroke)) {
                            _gestureName.append(" to Pitcher");
                        }


                        // Corner Infielders
                        else if ((topStroke <= infieldY1) && (topStroke >= outfieldY)) {

                            // FirstBase
                            if ((centerWidth - leftStroke) < (rightStroke - centerWidth) && (infieldX2 < rightStroke)) {
                                _gestureName.append(" to FirstBase");
                            }

                            // ThirdBase
                            else if ((centerWidth - leftStroke) > (rightStroke - centerWidth) && (infieldX1 > leftStroke)) {
                                _gestureName.append(" to ThirdBase");
                            }

                            else {
                                _gestureName.setText("TopStroke = " + topStroke +
                                        "\ninfieldY1" + infieldY1 +
                                        "\ninfieldY2" + infieldY2 +
                                        "\n\nLeftStroke = " + leftStroke +
                                        "\ninfieldX1 = " + infieldX1 +
                                        "\n\nRightStroke = " + rightStroke +
                                        "\ninfieldx2 " + infieldX2);
                            }
                        }

                        // Middle Infielders
                        else if ((topStroke <= infieldY2) && (topStroke > outfieldY)) {
                            // ShortStop
                            if ((centerWidth - leftStroke) > (rightStroke - centerWidth) && (infieldX1 < leftStroke)) {
                                _gestureName.append(" to ShortStop");
                            }

                            // SecondBase
                            else if ((centerWidth - leftStroke) < (rightStroke - centerWidth) && (infieldX2 > rightStroke)) {
                                _gestureName.append(" to SecondBase");
                            } else {
                                _gestureName.setText("TopStroke = " + topStroke +
                                        "\ninfieldY2" + infieldY2 +
                                        "\noutfieldY" + outfieldY +
                                        "\n\nLeftStroke = " + leftStroke +
                                        "\ninfieldX1 = " + infieldX1 +
                                        "\n\nRightStroke = " + rightStroke +
                                        "\ninfieldx2 " + infieldX2);
                            }
                        }

                        // Unidentified
                        else {
                            _gestureName.setText("Unidentified Gesture" +
                                    "\nTopStroke = " + topStroke +
                                    "\nLeftStroke = " + leftStroke +
                                    "\nRightStroke = " + rightStroke);
                        }
                    }
                }
            }
        });
    }

    private int xPitch;
    private int yPitch;
    private TextView _gestureName;
    private TextView _pitchName;

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

    ArrayList <Play> gamePlays;

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

 //   Field field = new Field(firstBase, secondBase, thirdBase, shortStop, centerField, leftField, rightField, catcher, pitching);

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
    RadioButton homePlateRadioButton;
    RadioButton firstBaseRadioButton;
    RadioButton secondBaseRadioButton;
    RadioButton thirdBaseRadioButton;
    TextView homeTeamScoreTextView;
    TextView awayTeamScoreTextView;
    TextView lastPlayTextView;
    TextView playTextView;
    TextView pitcherFielder;
    TextView catcherFielder;
    TextView firstBasemenFielder;
    TextView secondBasemenFielder;
    TextView thirdBasemenFielder;
    TextView shortStopFielder;
    TextView centerFieldFielder;
    TextView rightFieldFielder;
    TextView leftFieldFielder;

    public void initializeViews ()
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
        homePlateRadioButton = (RadioButton)findViewById(R.id.homePlate_Radio);
        firstBaseRadioButton = (RadioButton)findViewById(R.id.firstBase_Radio);
        secondBaseRadioButton = (RadioButton)findViewById(R.id.secondBase_Radio);
        thirdBaseRadioButton = (RadioButton)findViewById(R.id.thirdBase_Radio);
        homeTeamScoreTextView = (TextView)findViewById(R.id.homeScoreNumber_View);
        awayTeamScoreTextView = (TextView)findViewById(R.id.awayScoreNumber_View);
        lastPlayTextView = (TextView)findViewById(R.id.lastPlay_View);
        playTextView = (TextView)findViewById(R.id.play_View);
        pitcherFielder = (TextView)findViewById(R.id.pitcherText);
        catcherFielder = (TextView)findViewById(R.id.catcherText);
        firstBasemenFielder = (TextView)findViewById(R.id.firstBaseText);
        secondBasemenFielder = (TextView)findViewById(R.id.secondBaseText);
        thirdBasemenFielder = (TextView)findViewById(R.id.thirdBaseText);
        shortStopFielder = (TextView)findViewById(R.id.shortStopText);
        centerFieldFielder = (TextView)findViewById(R.id.centerFieldText);
        rightFieldFielder = (TextView)findViewById(R.id.rightFieldText);
        leftFieldFielder = (TextView)findViewById(R.id.leftFieldText);
    }

    public void startGame (View b)
    {
        initializeViews();
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

        currentHalfInning = new HalfInning(awayTeam, homeTeam, 1, 1);
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
        currentBatter = new AtBat(currentHalfInning, currentBattingOrder.get(currentBattingOrderPosition));
        setFieldTextViews();
        setCurrentBatterTextView();
    }

    public void setFieldTextViews ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            currentFieldingPositions = homeTeamPositions;
        }

        else
            currentFieldingPositions = awayTeamPositions;

        for (int i = 0; i < currentFieldingPositions.size(); i++)
        {
            if (currentFieldingPositions.get(i).getPosition().toString() == "Pitcher")
            {
                pitcherFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The pitcher text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Catcher")
            {
                catcherFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The catcher text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "First Base")
            {
                firstBasemenFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The first basemen text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Second Base")
            {
                secondBasemenFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The second basemen text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Third Base")
            {
                thirdBasemenFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The third basemen text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Short Stop")
            {
                shortStopFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The short stop text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Center Field")
            {
                centerFieldFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The center fielder text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Right Field")
            {
                rightFieldFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The right fielder text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }

            else if (currentFieldingPositions.get(i).getPosition().toString() == "Left Field")
            {
                leftFieldFielder.setText(currentFieldingPositions.get(i).getPlayer().getFullName());
                System.out.println("The left fielder text view has been set to " + currentFieldingPositions.get(i).getPlayer().getFullName());
            }
        }
    }

    public void updatePosition (Player player, PositionsInGame position)
    {
        System.out.println("The position " + position.getPosition().toString() + "has been updated with the player " + player.getFullName());
        position.setPlayer(player);
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

    @Dao
    public interface insertPlayDao
    {
            @Insert
            void insertAll(ArrayList<Play>gamePlay);
    }

    public void createNewPlay (String pitch)
    {
        Play newPlay = new Play(currentBatter.getPlayer(), pitching.getPlayer(), Pitch.valueOf(pitch), playTextView.getText().toString(), currentBatter);
        gamePlays.add(newPlay);
        currentBatter.addPlay(newPlay);
        lastPlayTextView.setText("Batter = " + newPlay.getBatter().getFullName() + " Pitcher = " + newPlay.getPitcher().getFullName() + " Pitch = " + newPlay.getPlayPitch().toString() + " Play Text = " + playTextView.getText().toString());
    }

    public void play (View b)
    {
        createNewPlay("HIT");

        String playString = playTextView.getText().toString();

        System.out.println("The play string is " + playString);

        if (playString.equals("1B"))
        {
            System.out.println("A single");
            if (basePath.areThereAnyRunnersOnBase() == false)
            {
                System.out.println("Since there are no other runners on the bases the batter goes to first and nothing else happens");
                setRunnerOnBase(basePath.getFirstBase(), currentBatter.getPlayer());
            }

            else
            {
                if (basePath.getThirdBase().doesBaseHaveRunner() == true)
                {
                    removeRunnerFromBase(basePath.getThirdBase());
                    incrementRunsScored();
                }

                if (basePath.getSecondBase().doesBaseHaveRunner() == true)
                {
                    setRunnerOnBase(basePath.getThirdBase(), basePath.getSecondBase().getRunnerOnBase());
                    removeRunnerFromBase(basePath.getSecondBase());
                }

                if (basePath.getFirstBase().doesBaseHaveRunner() == true)
                {
                    setRunnerOnBase(basePath.getSecondBase(), basePath.getFirstBase().getRunnerOnBase());
                    removeRunnerFromBase(basePath.getFirstBase());
                }
            }

            setNewBatter(b);
        }

        else if (playString.equals("2B"))
        {
            System.out.println("A double");

            if (basePath.getThirdBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on third scored");
                removeRunnerFromBase(basePath.getThirdBase());
                incrementRunsScored();
            }

            if (basePath.getSecondBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on second scored");
                removeRunnerFromBase(basePath.getSecondBase());
                incrementRunsScored();
            }

            if (basePath.getFirstBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on first scored");
                setRunnerOnBase(basePath.getThirdBase(), basePath.getFirstBase().getRunnerOnBase());
                removeRunnerFromBase(basePath.getFirstBase());
            }
            System.out.println("The batter is now on Second base after he hit a triple");
            setRunnerOnBase(basePath.getSecondBase(), currentBatter.getPlayer());
            setNewBatter(b);
        }

        else if (playString.equals("3B"))
        {
            System.out.println("Triple");

            if (basePath.getThirdBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on third scored");
                removeRunnerFromBase(basePath.getThirdBase());
                incrementRunsScored();
            }

            if (basePath.getSecondBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on second scored");
                removeRunnerFromBase(basePath.getSecondBase());
                incrementRunsScored();
            }


            if (basePath.getFirstBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on first scored");
                removeRunnerFromBase(basePath.getFirstBase());
                incrementRunsScored();
            }
            System.out.println("The batter is now on Third base after he hit a triple");
            setRunnerOnBase(basePath.getThirdBase(), currentBatter.getPlayer());
            setNewBatter(b);
        }

        else if (playString.equals("HR"))
        {
            System.out.println("Homerum!");

            if (basePath.getFirstBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on first scored");
                removeRunnerFromBase(basePath.getFirstBase());
                incrementRunsScored();
            }

            if (basePath.getSecondBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on second scored");
                removeRunnerFromBase(basePath.getSecondBase());
                incrementRunsScored();
            }
            if (basePath.getThirdBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on third scored");
                removeRunnerFromBase(basePath.getThirdBase());
                incrementRunsScored();
            }
            incrementRunsScored();
            setNewBatter(b);
        }

        else if (playString.equals("GRD"))
        {
            System.out.println("Ground roll double!");
            if (basePath.getThirdBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on third scored!");
                removeRunnerFromBase(basePath.getThirdBase());
                incrementRunsScored();
            }

            if (basePath.getSecondBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on second scored!");
                removeRunnerFromBase(basePath.getSecondBase());
                incrementRunsScored();
            }

            if (basePath.getFirstBase().doesBaseHaveRunner() == true)
            {
                System.out.println("The runner on first advanced to third");
                setRunnerOnBase(basePath.getThirdBase(), basePath.getFirstBase().getRunnerOnBase());
                removeRunnerFromBase(basePath.getFirstBase());
                basePath.getFirstBase().removeRunnerOnBase();
            }

            System.out.println("The batter advanced to second");
            setRunnerOnBase(basePath.getSecondBase(), currentBatter.getPlayer());
            setNewBatter(b);
        }

        else if (playString.equals("FC"))
        {

        }

        playTextView.setText("");
    }

    public void setRunnerOnBase (Base base, Player player)
    {
        basePath.setRunnerOnBase(base, player);
        markBase(base);
    }

    public void removeRunnerFromBase (Base base)
    {
        basePath.removeRunnerFromBase(base);
        unMarkBase(base);
    }

    public void hitByPitchHelperMethod ()
    {;
        hitByPitch(basePath.getHomeBase(), basePath.getFirstBase());
    }

    public void hitByPitch (Base currentBase, Base nextBase)
    {
        createNewPlay("HBP");
        System.out.println("The batter was hit by a pitch");

        if (nextBase.doesBaseHaveRunner() == false)
        {
            if (nextBase == basePath.getHomeBase())
            {
                currentHalfInning.incrementRunsScored();
                currentBase.removeRunnerOnBase();
                incrementRunsScored();
            }

            else
            {
                setRunnerOnBase(nextBase, currentBatter.getPlayer());
                removeRunnerFromBase(currentBase);
            }
        }

        else
        {
            hitByPitch(nextBase, basePath.getNextBase(nextBase));
            hitByPitch(currentBase, nextBase);
        }
    }

    public void walk (Base currentBase, Base nextBase)
    {
        System.out.println("Current base is " + currentBase.getBaseNumber());
        System.out.println("Does current base have a runner on it = " + currentBase.doesBaseHaveRunner());
        System.out.println("Next base is " + nextBase.getBaseNumber());
        System.out.println("Does next base have a runner on it = " + currentBase.doesBaseHaveRunner());

        if (nextBase.doesBaseHaveRunner() == false)
        {
            if (nextBase == basePath.getHomeBase())
            {
                currentHalfInning.incrementRunsScored();
                currentBase.removeRunnerOnBase();
                incrementRunsScored();
            }

            else
            {
                setRunnerOnBase(nextBase, currentBatter.getPlayer());
                removeRunnerFromBase(currentBase);
            }
        }

        else
        {
            walk(nextBase, basePath.getNextBase(nextBase));
            walk(currentBase, nextBase);
        }
    }

    public void unMarkBase (Base baseToUnMark)
    {
        if (baseToUnMark.getBaseNumber() == 1)
        {
            System.out.println("First base has been un marked");
            firstBaseRadioButton.setChecked(false);
        }

        else if (baseToUnMark.getBaseNumber() == 2)
        {
            System.out.println("Second base has been un marked");
            secondBaseRadioButton.setChecked(false);
        }

        else if (baseToUnMark.getBaseNumber() == 3)
        {
            System.out.println("Third base has un marked");
            thirdBaseRadioButton.setChecked(false);
        }

        else
        {
            return;
        }
    }

    public void markBase (Base baseToMark)
    {
        if (baseToMark.getBaseNumber() == 1)
        {
            System.out.println("First base has been marked");
            firstBaseRadioButton.setChecked(true);
        }

        else if (baseToMark.getBaseNumber() == 2)
        {
            System.out.println("Second base has been marked");
            secondBaseRadioButton.setChecked(true);
        }

        else if (baseToMark.getBaseNumber() == 3)
        {
            System.out.println("Third base has been marked");
            thirdBaseRadioButton.setChecked(true);
        }

        else
        {
            return;
        }
    }

    public void incrementRunsScored ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            game.incrementAwayTeamScore();
            System.out.println("The away team score has been incremented");
            setScoreTextView();
        }
        else
        {
            game.incrementHomeTeamScore();
            System.out.println("The home team score has been incremented");
            setScoreTextView();
        }
    }

    public void setScoreTextView ()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            awayTeamScoreTextView.setText(Integer.toString(game.getAwayTeamScore()));
            System.out.println("The away team score text view has been set to " + game.getAwayTeamScore());
        }
        else
        {
            homeTeamScoreTextView.setText(Integer.toString(game.getHomeTeamScore()));
            System.out.println("The home team score text view has been set to " + game.getHomeTeamScore());
        }
    }

    public void ball (View b)
    {
        if (currentBatter.getBallCount() < 3)
        {
            createNewPlay("BALL");
            System.out.println("Ball");
            System.out.println("Current ball count is " + currentBatter.getBallCount());
            currentBatter.incrementBalls();
            ballCountTextView.setText(Integer.toString(currentBatter.getBallCount()));
        }
        else
        {
            createNewPlay("BALL");
            System.out.println("Ball");
            System.out.println("4 balls, New currentBatter is set");
            walk(basePath.getHomeBase(), basePath.getFirstBase());
            setNewBatter(b);
        }
    }

    public void strike (View b)
    {
        if (currentBatter.getStrikeCount() < 2)
        {
            createNewPlay("STRIKE");
            System.out.println("Strike");
            System.out.println("Current strike count is " + currentBatter.getStrikeCount());
            currentBatter.incrementStrikes();
            strikeCountTextView.setText(Integer.toString(currentBatter.getStrikeCount()));
        }
        else
        {
            createNewPlay("STRIKE");
            System.out.println("Strike");
            System.out.println("3 Strikes, increment outs and set new current batter");
            out(b);
        }
    }

    public void foul (View b)
    {
        createNewPlay("FOUL");
        if (currentBatter.getStrikeCount() < 2)
        {
            System.out.println("Foul ball");
            System.out.println("Current strike count is " + currentBatter.getStrikeCount());
            currentBatter.incrementStrikes();
            strikeCountTextView.setText(Integer.toString(currentBatter.getStrikeCount()));
        }
    }

    public void out (View b)
    {
        incrementOuts(b);
        setNewBatter(b);
    }

    public void setNewBatter (View b)
    {
        currentHalfInning.addAtBat(currentBatter);
        strikeCountTextView.setText(Integer.toString(0));
        ballCountTextView.setText(Integer.toString(0));
        incrementBattingOrderPosition(currentBattingOrderPosition);
        removeRunnerFromBase(basePath.getHomeBase());
        currentBatter = new AtBat(currentHalfInning, currentBattingOrder.get(currentBattingOrderPosition));
        currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
        System.out.println("A new batter has been set. It is " + currentBatter.getPlayer().getFullName());
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
            currentBatter = new AtBat(currentHalfInning, currentBattingOrder.get(currentBattingOrderPosition));
            currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
        }

        else
        {
            System.out.println("There are now 3 outs, setting up next half inning");
            System.out.println("Current Inning Count is " + currentInning.getInningCount());

            resetInGameViews(b);

            basePath = new BasePath();

            if (currentHalfInning.topOrBottom() == 1) //currently the top of the inning
            {
                System.out.println("The new half inning will be the bottom of the " + currentInning.getInningCount());
                currentInning.setTopInning(currentHalfInning);
                currentHalfInning = new HalfInning(awayTeam, homeTeam, 2, currentInning.getInningCount());
            }
            else
            {
                System.out.println("Game type number of innings = " + game.getGameType().getNumInnings());
                System.out.println("Current innning = " + currentInning.getInningCount());
                if (currentInning.getInningCount() == game.getGameType().getNumInnings())
                {
                    System.out.println("The game is over since the max innings have been reached!");
                    endGame(b);
                }
                else
                {
                    System.out.println("New Inning. Setting up top of the new inning. The inning number is " + currentInning.getInningCount());
                    currentInning.setBottomInning(currentHalfInning);
                    currentInning.incrementInningNumber();
                    currentHalfInning = new HalfInning(homeTeam, awayTeam, 1, currentInning.getInningCount());
                    currentInning = new Inning(game, currentInning.getInningCount(), currentHalfInning, null);
                    game.addInning(currentInning);

                    startHalfInning(currentHalfInning);
                }

            }

            setTopOrBottomTextView();
            setCurrentInningTextView();
            setBattingAndFieldingTextView();
            setCurrentFieldingPositions();
            setCurrentBattingOrder();
        }
    }

    public void endGame (View b)
    {
        resetInGameViews(b);

        Context context = getApplicationContext();
        System.out.println("The winner of the game is " + game.getGameWinner().toString());
        CharSequence text = "The winner of the game is " + game.getGameWinner().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast gameWinnerToast = Toast.makeText(context, text, duration);
        gameWinnerToast.show();
    }

    public void resetInGameViews (View b)
    {
        strikeCountTextView.setText(Integer.toString(0));
        ballCountTextView.setText(Integer.toString(0));
        outCountTextView.setText(Integer.toString(0));
        System.out.println("Strike, ball, and out count have been set to 0");

        if (firstBaseRadioButton.isChecked())
        {
            firstBaseRadioButton.setChecked(false);
            System.out.println("First base radio button has been toggled");
        }

        if (secondBaseRadioButton.isChecked())
        {
            secondBaseRadioButton.setChecked(false);
            System.out.println("Second base radio button has been toggled");
        }

        if (thirdBaseRadioButton.isChecked())
        {
            thirdBaseRadioButton.setChecked(false);
            System.out.println("Third base radio button has been toggled");
        }
    }
}
