package com.example.david.scorecardpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureStroke;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jacklavallee on 11/28/17.
 */

public class ScoringActivity extends AppCompatActivity {

    private GestureOverlayView gestureOverlayView;
    private FrameLayout fieldLayout;
    private FrameLayout strikeLayout;
    private FrameLayout ballLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);

        Intent arrived = getIntent();
        if (arrived.hasExtra("Home Team"))
        {
            startGame(arrived);
        }

        initializeViews();

        _gestureName = (TextView) findViewById(R.id.gestureName);
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);
        fieldLayout = (FrameLayout) findViewById(R.id.field_fragment);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {

            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                if (screenHeight == 0.0) {
                    initGestureCoordinates();
                }
                //hitGesture(gestureOverlayView);
            }
        });

        gestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                if (screenHeight == 0.0) {
                    initGestureCoordinates();
                }

                if (motionEvent.getY() > catcherY1 && motionEvent.getY() < catcherY2 && motionEvent.getX() > middleBaseX1 && motionEvent.getX() < middleBaseX2) {
                    hitGesture(gestureOverlayView);
                }
                else {
                    gestureOverlayView.cancelGesture();
                }
            }

            @Override
            public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

            }

            @Override
            public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

            }

            @Override
            public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

            }
        });

        ballLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                Log.i("Strike Coordinate", "Left = " + strikeLayout.getLeft());
                Log.i("Strike Coordinate", "Right = " + strikeLayout.getRight());
                Log.i("Strike Coordinate", "Top = " + strikeLayout.getTop());
                Log.i("Strike Coordinate", "Left = " + strikeLayout.getBottom());
                Log.i("Pitch", "Location " + ev.getX() + ", " + ev.getY());

                switch (ev.getActionMasked()) {
                    case MotionEvent.ACTION_UP: {
                        if (ev.getX() > strikeLayout.getLeft() && ev.getX() < strikeLayout.getRight() && ev.getY() > strikeLayout.getTop() && ev.getY() < strikeLayout.getBottom()) {
                            _pitchName.setText("Strike!");
                            strike();
                        }
                        else {
                            _pitchName.setText("Ball!");
                            ball();
                        }
                    }
                }
                return true;
            }
        });

        RunnerView runner = new RunnerView(this, 11);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);
        layout.setMargins((int) screenWidth, (int) secondBasePos, (int) screenWidth, (int) (screenHeight - secondBasePos));
        fieldLayout.addView(runner, layout);
    }


    public void onResume() {
        super.onResume();
        initGestureCoordinates();
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


    private void hitGesture(GestureOverlayView gestureOverlayView /*, Gesture gesture*/) {
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {

                ArrayList<GestureStroke> strokes = gesture.getStrokes();
                GestureStroke stroke = strokes.get(strokes.size() - 1);

                if (stroke.computeOrientedBoundingBox().height > 50)
                    _gestureName.setText("Groundball");
                else
                    _gestureName.setText("Fly ball");

                double topStroke = stroke.boundingBox.top;
                double leftStroke = stroke.boundingBox.left;
                double rightStroke = stroke.boundingBox.right;
                double strokeX = leftStroke;

                if ((centerWidth - leftStroke) < (rightStroke - centerWidth)) {
                    strokeX = rightStroke;
                }

                PositionsInGame[] gesturePositions = {pitcher, catcher, firstBase, secondBase, thirdBase, shortStop, leftField, centerField, rightField};
                for (PositionsInGame pos : gesturePositions) {
                    if (pos.containsPoint(strokeX, topStroke)) {
                        _gestureName.append(" to " + pos.getPosition());
                        break;
                    }
                }
            }
        });
    }

    public boolean runnerMove(RunnerView rv, int dx, int dy) {
        int left = rv.getLeft() + dx;
        int top = rv.getTop() + dy;
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);
        layout.setMargins((int) left, (int)top, (int) screenWidth-(left+rv.width), (int) screenHeight-(top+rv.height));
        fieldLayout.updateViewLayout(rv, layout);
        return true;
    }

    public void snapToBase(RunnerView rv)
    {
        int left = rv.getLeft();
        int top = rv.getTop();
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);
        if (top > catcherY1 && top < catcherY2 && left > middleBaseX1 && left < middleBaseX2) {
            Log.i("snapToBase", "X: " + rv.getLeft());
            Log.i("snapToBase", "Y: " + rv.getTop());
            layout.setMargins((int) screenWidth, (int) catcherBase, (int) screenWidth, (int) (screenHeight - catcherBase));
            fieldLayout.updateViewLayout(rv, layout);
        }

        else if (top > outfieldY && top < secondBaseY && left > middleBaseX1 && left < middleBaseX2) {
            Log.i("snapToBase", "X: " + rv.getLeft());
            Log.i("snapToBase", "Y: " + rv.getTop());
            layout.setMargins((int) screenWidth, (int) secondBasePos, (int) screenWidth, (int) (screenHeight - secondBasePos));
            fieldLayout.updateViewLayout(rv, layout);
        }

        else if (top > cornerBaseY1 && top < cornerBaseY2 && left > gestureOverlayView.getWidth() * .20 && left < gestureOverlayView.getWidth() * .30) {
            Log.i("snapToBase", "X: " + rv.getLeft());
            Log.i("snapToBase", "Y: " + rv.getTop());
            layout.setMargins((int) screenWidth, (int) secondBasePos, (int) screenWidth, (int) (screenHeight - secondBasePos));
            fieldLayout.updateViewLayout(rv, layout);
        }

        else if (top > outfieldY && top < secondBaseY && left > gestureOverlayView.getWidth() * .70 && left < gestureOverlayView.getWidth() * .80) {
            Log.i("snapToBase", "X: " + rv.getLeft());
            Log.i("snapToBase", "Y: " + rv.getTop());
            layout.setMargins((int) screenWidth, (int) secondBasePos, (int) screenWidth, (int) (screenHeight - secondBasePos));
            fieldLayout.updateViewLayout(rv, layout);
        }

        else {
            Log.i("snapToBase", "Not in ranges");
            Log.i("snapToBase", "X: " + rv.getLeft());
            Log.i("snapToBase", "Y: " + rv.getTop());
        }

    }


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

    Player player1 = new Player("David", "Walsh", 1, 22);
    Player player2 = new Player("Jack", "Lavallee", 2, 24);
    Player player3 = new Player("Joe", "Russell", 3, 35);
    Player player4 = new Player("Craig", "Damon", 4, 55);
    Player player5 = new Player("Leslie", "Damon", 5, 50);
    Player player6 = new Player("Peter", "Chapin", 6, 45);
    Player player7 = new Player("Mike", "Hall", 7, 22);
    Player player8 = new Player("Matt", "Tanneberger", 8, 21);
    Player player9 = new Player("Jake", "Morrill", 9, 22);

    ArrayList <Player> homeTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));
    ArrayList <Player> awayTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));

    private void initGestureCoordinates() {
        screenHeight = gestureOverlayView.getHeight();
        screenWidth = gestureOverlayView.getWidth();
        centerWidth = gestureOverlayView.getWidth() * .5;
        catcherY1 = gestureOverlayView.getHeight() * .8;
        catcherY2 = gestureOverlayView.getHeight() * .9;
        catcherBase = gestureOverlayView.getHeight() * .85;
        middleBaseX1 = gestureOverlayView.getWidth() * .45;
        middleBaseX2 = gestureOverlayView.getWidth() * .55;
        cornerBaseY1 = gestureOverlayView.getHeight() * .63;
        cornerBaseY2 = gestureOverlayView.getHeight() * .53;
        infieldY1 = gestureOverlayView.getHeight() * .7;
        infieldY2 = gestureOverlayView.getHeight() * .5;
        secondBaseY = gestureOverlayView.getHeight() * .4;
        secondBasePos = gestureOverlayView.getHeight() * .35;
        outfieldY = gestureOverlayView.getHeight() * .3;
        infieldX1 = gestureOverlayView.getWidth() * .25;
        infieldX2 = gestureOverlayView.getWidth() * .75;
        thirdbaseX = gestureOverlayView.getWidth() * .15;
        firstbaseX = gestureOverlayView.getWidth() * .85;
        outfieldX1 = gestureOverlayView.getWidth() * (1 / 3.0);
        outfieldX2 = gestureOverlayView.getWidth() * (2 / 3.0);




        pitcher = new PositionsInGame(player1, Positions.PITCHER, infieldY2, infieldX2, infieldY1, infieldX1);
        Log.i("Gestures", "Pitcher at " + infieldY2 + ", " + infieldX2 + ", " + infieldY1 + ", " + infieldX1);

        firstBase = new PositionsInGame(player2, Positions.FIRSTBASE, outfieldY, screenWidth, infieldY1, infieldX2);
        Log.i("Gestures", "FirstBase at " + outfieldY + ", " + screenWidth + ", " + infieldY1 + ", " + infieldX2);

        catcher = new PositionsInGame(player3, Positions.CATCHER, infieldY1, outfieldX2, screenHeight, outfieldX1);
        Log.i("Gestures", "Catcher at " + infieldY1 + ", " + outfieldX2 + ", " + screenHeight + ", " + outfieldX1);

        secondBase= new PositionsInGame(player4, Positions.SECONDBASE, outfieldY, infieldX2, infieldY2, centerWidth);
        Log.i("Gestures", "SecondBase at " + outfieldY + ", " + infieldX2 + ", " + infieldY2 + ", " + centerWidth);

        shortStop = new PositionsInGame(player5, Positions.SHORTSTOP, outfieldY, centerWidth, infieldY2, infieldX1);
        Log.i("Gestures", "ShortStop at " + outfieldY + ", " + centerWidth + ", " + infieldY2 + ", " + infieldX1);

        thirdBase = new PositionsInGame(player6, Positions.THIRDBASE, outfieldY, infieldX1, infieldY1, 0.0);
        Log.i("Gestures", "ThirdBase at " + outfieldY + ", " + infieldX1 + ", " + infieldY1 + ", " + 0.0);

        centerField = new PositionsInGame(player7, Positions.CENTERFIELD, 0.0, outfieldX2, outfieldY, outfieldX1);
        Log.i("Gestures", "Centerfield at " + 0.0 + ", " + outfieldX2 + ", " + outfieldY + ", " + outfieldX1);

        rightField = new PositionsInGame(player8, Positions.RIGHTFIELD, 0.0, screenWidth, outfieldY, outfieldX2);
        Log.i("Gestures", "RightField at " + 0.0 + ", " + 0.0 + ", " + outfieldY + ", " + outfieldX2);

        leftField = new PositionsInGame(player9, Positions.LEFTFIELD, 0.0, outfieldX1, outfieldY, 0.0);
        Log.i("Gestures", "LeftField at " + 0.0 + ", " + outfieldX1 + ", " + outfieldY + ", " + 0.0);


        field = new Field(firstBase, secondBase, thirdBase, shortStop, centerField, leftField, rightField, catcher, pitcher);

        homeTeamPositions = new ArrayList<>(Arrays.asList(pitcher, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));
        awayTeamPositions = new ArrayList<>(Arrays.asList(pitcher, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));

    }

    double screenHeight;
    double screenWidth;
    double centerWidth;
    double catcherY1;
    double catcherY2;
    double catcherBase;
    double infieldY1;
    double infieldY2;
    double secondBaseY;
    double secondBasePos;
    double outfieldY;
    double infieldX1;
    double infieldX2;
    double middleBaseX1;
    double middleBaseX2;
    double cornerBaseY1;
    double cornerBaseY2;
    double thirdbaseX;
    double firstbaseX;
    double outfieldX1;
    double outfieldX2;

    PositionsInGame pitcher = new PositionsInGame(player1, Positions.PITCHER, infieldY2, infieldX2, infieldY1, infieldX1);
    PositionsInGame firstBase = new PositionsInGame(player2, Positions.FIRSTBASE, outfieldY, 0.0, infieldY1, infieldX2);
    PositionsInGame catcher = new PositionsInGame(player3, Positions.CATCHER, infieldY1, outfieldX2, screenHeight, outfieldX1);
    PositionsInGame secondBase= new PositionsInGame(player4, Positions.SECONDBASE, outfieldY, infieldX2, infieldY2, centerWidth);
    PositionsInGame shortStop = new PositionsInGame(player5, Positions.SHORTSTOP, outfieldY, centerWidth, infieldY2, infieldX1);
    PositionsInGame thirdBase = new PositionsInGame(player6, Positions.THIRDBASE, outfieldY, infieldX1, infieldY1, 0.0);
    PositionsInGame centerField = new PositionsInGame(player7, Positions.CENTERFIELD, 0.0, outfieldX2, outfieldY, outfieldX1);
    PositionsInGame rightField = new PositionsInGame(player8, Positions.RIGHTFIELD, 0.0, 0.0, outfieldY, outfieldX2);
    PositionsInGame leftField = new PositionsInGame(player9, Positions.LEFTFIELD, 0.0, outfieldX1, outfieldY, 0.0);

    Field field = new Field(firstBase, secondBase, thirdBase, shortStop, centerField, leftField, rightField, catcher, pitcher);

    ArrayList <PositionsInGame> homeTeamPositions = new ArrayList<>(Arrays.asList(pitcher, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));
    ArrayList <PositionsInGame> awayTeamPositions = new ArrayList<>(Arrays.asList(pitcher, firstBase, catcher, secondBase, shortStop, thirdBase, centerField, leftField, rightField));

    TextView homeTeamTextView;
    TextView awayTeamTextView;
    TextView homeTeamTitleTextView;
    TextView awayTeamTitleTextView;
    TextView currentBattingTeamTextView;
    TextView currentInningTextView;
    TextView currentBatterTextView;
    RadioButton ball1Button;
    RadioButton ball2Button;
    RadioButton ball3Button;
    RadioButton strike1Button;
    RadioButton strike2Button;
    RadioButton out1Button;
    RadioButton out2Button;
    TextView bottomOrTopTextView;
    TextView homeTeamScoreTextView;
    TextView awayTeamScoreTextView;
    TextView lastPlayTextView;
    TextView playTextView;
    TextView onBaseFirst;
    TextView onBaseSecond;
    TextView onBaseThird;
    TextView pitcherTextView;
    TextView catcherTextView;
    TextView firstBasemenTextView;
    TextView secondBasemenTextView;
    TextView thirdBasemenTextView;
    TextView shortStopTextView;
    TextView centerFielderTextView;
    TextView leftFielderTextView;
    TextView rightFielderTextView;

    private String awayTeamName;
    private String homeTeamName;

    public void initializeViews ()
    {
        homeTeamTitleTextView = (TextView) findViewById(R.id.homeScore_View);
        awayTeamTitleTextView = (TextView) findViewById(R.id.awayScore_View);
        currentBattingTeamTextView = (TextView)findViewById(R.id.currentBattingTeam_View);
        currentInningTextView = (TextView)findViewById(R.id.currentInning_View);
        currentBatterTextView = (TextView)findViewById(R.id.currentBatter_View);
        ball1Button = (RadioButton)findViewById(R.id.ball1);
        ball2Button = (RadioButton)findViewById(R.id.ball2);
        ball3Button = (RadioButton)findViewById(R.id.ball3);
        strike1Button = (RadioButton)findViewById(R.id.strike1);
        strike2Button = (RadioButton)findViewById(R.id.strike2);
        out1Button = (RadioButton)findViewById(R.id.out1);
        out2Button = (RadioButton)findViewById(R.id.out2);
        bottomOrTopTextView = (TextView)findViewById(R.id.bottomOrTop_View);
        homeTeamScoreTextView = (TextView)findViewById(R.id.homeScoreNumber_View);
        awayTeamScoreTextView = (TextView)findViewById(R.id.awayScoreNumber_View);
        lastPlayTextView = (TextView)findViewById(R.id.lastPlay_View);
        playTextView = (TextView)findViewById(R.id.play_View);
        onBaseFirst = (TextView)findViewById(R.id.onbase_first);
        onBaseSecond = (TextView)findViewById(R.id.onbase_second);
        onBaseThird = (TextView)findViewById(R.id.onbase_third);
        ballLayout = (FrameLayout)findViewById(R.id.ballZoneLayout);
        strikeLayout = (FrameLayout)findViewById(R.id.strikeZoneLayout);
        pitcherTextView = (TextView)findViewById(R.id.pitcherText);
        catcherTextView = (TextView)findViewById(R.id.catcherText);
        firstBasemenTextView = (TextView)findViewById(R.id.firstBaseText);
        secondBasemenTextView = (TextView)findViewById(R.id.secondBaseText);
        thirdBasemenTextView = (TextView)findViewById(R.id.thirdBaseText);
        shortStopTextView = (TextView)findViewById(R.id.shortStopText);
        centerFielderTextView = (TextView)findViewById(R.id.centerFieldText);
        leftFielderTextView = (TextView)findViewById(R.id.leftFieldText);
        rightFielderTextView = (TextView)findViewById(R.id.rightFieldText);
    }

    public void startGame (Intent i)
    {
        initializeViews();
        Log.i("scorecard", "Start game was called");
        homeTeamName = i.getStringExtra("Home Team");
        awayTeamName = i.getStringExtra("Away Team");

        homeTeam = new Team(homeTeamName);
        awayTeam = new Team(awayTeamName);

        homeTeamInGame = new TeamInGame(homeTeam);
        awayTeamInGame = new TeamInGame(awayTeam);

        GameType gameType = new GameType("Little League", 6);

        game = new Game(homeTeamInGame, awayTeamInGame, gameType);

        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

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
        setCurrentBatterTextView();
        setFieldTextViews();
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

    public void setFieldTextViews()
    {
        if (currentHalfInning.topOrBottom() == 1)
        {
            for (int i = 0; i < homeTeamPositions.size(); i ++)
            {
                if (homeTeamPositions.get(i).getPosition().toString() == "Pitcher")
                {
                    pitcherTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Catcher")
                {
                    catcherTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "First Base")
                {
                    firstBasemenTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Second Base")
                {
                    secondBasemenTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Short Stop")
                {
                    shortStopTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Third Base")
                {
                   thirdBasemenTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Center Field")
                {
                    centerFielderTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Right Field")
                {
                    rightFielderTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (homeTeamPositions.get(i).getPosition().toString() == "Left Field")
                {
                    leftFielderTextView.setText(homeTeamPositions.get(i).getPlayer().getLastName());
                }
            }
        }
        else
        {
            for (int i = 0; i < awayTeamPositions.size(); i ++)
            {
                if (awayTeamPositions.get(i).getPosition().toString() == "Pitcher")
                {
                    pitcherTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Catcher")
                {
                    catcherTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "First Base")
                {
                    firstBasemenTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Second Base")
                {
                    secondBasemenTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Short Stop")
                {
                    shortStopTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Third Base")
                {
                    thirdBasemenTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Center Field")
                {
                    centerFielderTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Right Field")
                {
                    rightFielderTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (awayTeamPositions.get(i).getPosition().toString() == "Left Field")
                {
                    leftFielderTextView.setText(awayTeamPositions.get(i).getPlayer().getLastName());
                }
            }
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

    public void createNewPlay (String pitch)
    {
        Play newPlay = new Play(currentBatter.getPlayer(), pitcher.getPlayer(), Pitch.valueOf(pitch), playTextView.getText().toString(), currentBatter);
        currentBatter.addPlay(newPlay);
        lastPlayTextView.setText("Batter = " + newPlay.getBatter().getFullName() + " Pitcher = " + newPlay.getPitcher().getFullName() + " Pitch = " + newPlay.getPlayPitch().toString() + " Play Text = " + playTextView.getText().toString());
    }

    public void play ()
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

            setNewBatter(/*b*/);
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
            setNewBatter(/*b*/);
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
            setNewBatter(/*b*/);
        }

        else if (playString.equals("HR"))
        {
            System.out.println("Homerun!");

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
            setNewBatter(/*b*/);
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
            setNewBatter(/*b*/);
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
            onBaseFirst.setVisibility(View.INVISIBLE);
            //onBaseFirstText.setVisibility(View.INVISIBLE);
        }

        else if (baseToUnMark.getBaseNumber() == 2)
        {
            System.out.println("Second base has been un marked");
            onBaseSecond.setVisibility(View.INVISIBLE);
            //onBaseSecondText.setVisibility(View.INVISIBLE);
        }

        else if (baseToUnMark.getBaseNumber() == 3)
        {
            System.out.println("Third base has un marked");
            onBaseThird.setVisibility(View.INVISIBLE);
            //onBaseThirdText.setVisibility(View.INVISIBLE);
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
            onBaseFirst.setVisibility(View.VISIBLE);
            //onBaseFirstText.setVisibility(View.VISIBLE);
        }

        else if (baseToMark.getBaseNumber() == 2)
        {
            System.out.println("Second base has been marked");
            onBaseSecond.setVisibility(View.VISIBLE);
            //onBaseSecondText.setVisibility(View.VISIBLE);
        }

        else if (baseToMark.getBaseNumber() == 3)
        {
            System.out.println("Third base has been marked");
            onBaseThird.setVisibility(View.VISIBLE);
            //onBaseThirdText.setVisibility(View.VISIBLE);
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

    public void ball ()
    {
        if (currentBatter.getBallCount() < 3)
        {
            currentBatter.incrementBalls();
            createNewPlay("BALL");
            System.out.println("Ball");
            System.out.println("Current ball count is " + currentBatter.getBallCount());
            if (currentBatter.getBallCount() == 1) {
                Log.i("Balls", "" + currentBatter.getBallCount());
                ball1Button.setChecked(true);
            }
            else if (currentBatter.getBallCount() == 2) {
                Log.i("Balls", "" + currentBatter.getBallCount());
                ball1Button.setChecked(true);
                ball2Button.setChecked(true);
            }
            else if (currentBatter.getBallCount() == 3) {
                Log.i("Balls", "" + currentBatter.getBallCount());
                ball1Button.setChecked(true);
                ball2Button.setChecked(true);
                ball3Button.setChecked(true);
            }
            else {
                ball1Button.setChecked(false);
                ball2Button.setChecked(false);
                ball3Button.setChecked(false);
            }
        }
        else
        {
            createNewPlay("BALL");
            System.out.println("Ball");
            System.out.println("4 balls, New currentBatter is set");
            walk(basePath.getHomeBase(), basePath.getFirstBase());
            setNewBatter();
        }
    }

    public void strike ()
    {
        if (currentBatter.getStrikeCount() < 2)
        {
            currentBatter.incrementStrikes();
            createNewPlay("STRIKE");
            System.out.println("Strike");
            System.out.println("Current strike count is " + currentBatter.getStrikeCount());
            if (currentBatter.getStrikeCount() == 1) {
                Log.i("Strikes", "" + currentBatter.getStrikeCount());
                strike1Button.setChecked(true);
            }
            else if (currentBatter.getStrikeCount() == 2) {
                Log.i("Strikes", "" + currentBatter.getStrikeCount());
                strike1Button.setChecked(true);
                strike2Button.setChecked(true);
            }
            else {
                Log.i("Strikes", "" + currentBatter.getStrikeCount());
                strike1Button.setChecked(false);
                strike2Button.setChecked(false);
            }
        }
        else
        {
            createNewPlay("STRIKE");
            System.out.println("Strike");
            System.out.println("3 Strikes, increment outs and set new current batter");
            out();
        }
    }

    public void foul ()
    {
        createNewPlay("FOUL");
        if (currentBatter.getStrikeCount() < 2)
        {
            System.out.println("Foul ball");
            System.out.println("Current strike count is " + currentBatter.getStrikeCount());
            currentBatter.incrementStrikes();
            if (currentBatter.getStrikeCount() == 1) {
                strike1Button.setChecked(true);
            }
            else if (currentBatter.getStrikeCount() == 2) {
                strike1Button.setChecked(true);
                strike2Button.setChecked(true);
            }
            else {
                strike1Button.setChecked(false);
                strike2Button.setChecked(false);
            }
        }
    }

    public void out ()
    {
        incrementOuts();
        System.out.println("Out");
        System.out.println("Current out count is " + currentHalfInning.getOuts());
        if (currentHalfInning.getOuts() == 1) {
            out1Button.setChecked(true);
        }
        else if (currentHalfInning.getOuts() == 2) {
            out1Button.setChecked(true);
            out2Button.setChecked(true);
        }
        else {
            out1Button.setChecked(false);
            out2Button.setChecked(false);
        }
        setNewBatter();
    }

    public void setNewBatter ()
    {
        currentHalfInning.addAtBat(currentBatter);
        ball1Button.setChecked(false);
        ball2Button.setChecked(false);
        ball3Button.setChecked(false);
        strike1Button.setChecked(false);
        strike2Button.setChecked(false);
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

    public void incrementOuts ()
    {
        incrementBattingOrderPosition(currentBattingOrderPosition);

        if (currentBatter.getHalfInning().getOuts() < 2)
        {
            System.out.println("Current out count is " + currentBatter.getHalfInning().getOuts());
            currentHalfInning.incrementOuts();
            currentBatter = new AtBat(currentHalfInning, currentBattingOrder.get(currentBattingOrderPosition));
            currentBatterTextView.setText(currentBatter.getPlayer().getFullName());
        }

        else
        {
            System.out.println("There are now 3 outs, setting up next half inning");
            System.out.println("Current Inning Count is " + currentInning.getInningCount());

            resetInGameViews();

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
                    endGame();
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

    public void endGame ()
    {
        resetInGameViews();

        Context context = getApplicationContext();
        System.out.println("The winner of the game is " + game.getGameWinner().toString());
        CharSequence text = "The winner of the game is " + game.getGameWinner().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast gameWinnerToast = Toast.makeText(context, text, duration);
        gameWinnerToast.show();
    }

    public void resetInGameViews ()
    {
        ball1Button.setChecked(false);
        ball2Button.setChecked(false);
        ball3Button.setChecked(false);
        strike1Button.setChecked(false);
        strike2Button.setChecked(false);
        out1Button.setChecked(false);
        out2Button.setChecked(false);
        onBaseFirst.setVisibility(View.INVISIBLE);
        onBaseSecond.setVisibility(View.INVISIBLE);
        onBaseThird.setVisibility(View.INVISIBLE);

        System.out.println("Strike, ball, and out count have been set to 0");
    }
}
