package com.example.david.scorecardpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
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

import com.example.david.GameListener;

import java.util.ArrayList;

/**
 * Created by jacklavallee on 11/28/17.
 */

public class ScoringActivity extends AppCompatActivity implements GameListener {

    private GestureOverlayView gestureOverlayView;
    private FrameLayout fieldLayout;
    private FrameLayout strikeLayout;
    private FrameLayout ballLayout;
    private float startThrowX;
    private float startThrowY;
    FieldView currentFielder;
    BaseRegion currentBase;

    public enum ScoringState {
        pitching, fielding
    };

    private ScoringState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);

        Intent arrived = getIntent();

        awayTeamName = arrived.getStringExtra("awayTeamName");
        homeTeamName = arrived.getStringExtra("homeTeamName");
        inningNum = arrived.getIntExtra("numberOfInnings", 9);
        gameTypeName = arrived.getStringExtra("gameType");


        System.out.println("homeTeamName = " + homeTeamName);
        System.out.println("awayTeamName = " + awayTeamName);
        homeTeamPositions = (ArrayList<PositionsInGame>)arrived.getSerializableExtra("homeTeamPositions");
        awayTeamPositions = (ArrayList<PositionsInGame>)arrived.getSerializableExtra("awayTeamPositions");
        homeTeamLineup = (ArrayList<Player>)arrived.getSerializableExtra("homeTeamLineup");
        awayTeamLineup = (ArrayList<Player>)arrived.getSerializableExtra("awayTeamLineup");

        Log.i("Lineups", "Home Leadoff = " + homeTeamLineup.get(0) + " Away Leadoff = " + awayTeamLineup.get(0));

        startGame(arrived);

        initializeViews();

        _gestureName = (TextView) findViewById(R.id.gestureName);
        _pitchName = (TextView) findViewById(R.id.pitchName);
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);
        fieldLayout = (FrameLayout) findViewById(R.id.field_fragment);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {

            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                if (screenHeight == 0.0) {
                    initGestureCoordinates();
                }
            }
        });

        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {

                if (currentFielder == null) {
                    hitGesture(gestureOverlayView, gesture);

                    Log.i("inPlay", "true");
                }
                else {
                    fieldingGesture(gestureOverlayView, gesture);
                }
            }
        });


        gestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

                if (screenHeight == 0.0) {
                    initGestureCoordinates();
                }

                if (motionEvent.getY() > catcherY1 && motionEvent.getY() < catcherY2 && motionEvent.getX() > middleBaseX1 && motionEvent.getX() < middleBaseX2) {

                    pitcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    catcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    firstBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    secondBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    thirdBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    shortStopFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    leftFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    centerFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                    rightFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));

                    currentFielder = null;
                }

                else if (currentFielder != null && currentFielder.containsPoint(motionEvent.getX(), motionEvent.getY())) {
                    startThrowX = motionEvent.getX();
                    startThrowY = motionEvent.getY();
                }

                else if (currentBase != null && currentBase.containsPoint(motionEvent.getX(), motionEvent.getY())) {
                    startThrowX = motionEvent.getX();
                    startThrowY = motionEvent.getY();
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
                        if (state == ScoringState.fielding) {
                            game.setNewBatter();
                        }
                        if (ev.getX() > strikeLayout.getLeft() && ev.getX() < strikeLayout.getRight() && ev.getY() > strikeLayout.getTop() && ev.getY() < strikeLayout.getBottom()) {
                            _pitchName.setText("Strike!");
                            state = ScoringState.pitching;
                            game.strike();
                        }
                        else {
                            _pitchName.setText("Ball!");
                            state = ScoringState.pitching;
                            game.ball();
                        }
                    }
                }

                pitcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                catcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                firstBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                secondBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                thirdBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                shortStopFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                leftFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                centerFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
                rightFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));

                Log.i("inPlay", "true");
                return true;
            }
        });
        RunnerView.setScoringActivity(this);
 //       db.clearTable();

    }

    private void initRegions() {
        fieldRegions = new FieldRegion[] {
                new FieldRegion(Positions.PITCHER, infieldY2, infieldX2, infieldY1, infieldX1),
                new FieldRegion(Positions.FIRSTBASE, outfieldY, screenWidth, infieldY1, infieldX2),
                new FieldRegion(Positions.CATCHER, infieldY1, outfieldX2, screenHeight, outfieldX1),
                new FieldRegion(Positions.SECONDBASE, outfieldY, infieldX2, infieldY2, centerWidth),
                new FieldRegion(Positions.SHORTSTOP, outfieldY, centerWidth, infieldY2, infieldX1),
                new FieldRegion(Positions.THIRDBASE, outfieldY, infieldX1, infieldY1, 0.0),
                new FieldRegion(Positions.CENTERFIELD, homeRunY, outfieldX2, outfieldY, outfieldX1),
                new FieldRegion(Positions.RIGHTFIELD, homeRunY, screenWidth, outfieldY, outfieldX2),
                new FieldRegion(Positions.LEFTFIELD, homeRunY, outfieldX1, outfieldY, 0.0),
                new FieldRegion(null, 0.0, screenWidth, homeRunY, 0.0),
                new FieldRegion(null, infieldY1, outfieldX1, screenHeight, 0.0),
                new FieldRegion(null, infieldY1, screenWidth, screenHeight, outfieldX2)
        };

        firstBaseRegion = new BaseRegion(game.basePath.getFirstBase(), (int) (gestureOverlayView.getWidth() * .74), (int) (gestureOverlayView.getHeight() * .54), (int) (gestureOverlayView.getWidth() * .80), (int) cornerBaseY1);
        secondBaseRegion = new BaseRegion(game.basePath.getSecondBase(), (int) (gestureOverlayView.getWidth() * .46), (int) (gestureOverlayView.getHeight() * .32), (int) middleBaseX2, (int) (screenHeight - outfieldY));
        thirdBaseRegion = new BaseRegion(game.basePath.getThirdBase(), (int) thirdBaseX, (int) (gestureOverlayView.getHeight() * .54), (int) gestureOverlayView.getWidth() * .24, (int) cornerBaseY1);
        homePlateRegion = new BaseRegion(game.basePath.getHomeBase(), (int) (gestureOverlayView.getWidth() * .46), (int) homePlateY, (int) middleBaseX2, (int) (screenHeight - homePlateY));

        baseRegions = new BaseRegion[] {
               firstBaseRegion, secondBaseRegion, thirdBaseRegion, homePlateRegion
        };
    }

    private FieldRegion[] fieldRegions;
    private BaseRegion[] baseRegions;

    private BaseRegion firstBaseRegion;
    private BaseRegion secondBaseRegion;
    private BaseRegion thirdBaseRegion;
    private BaseRegion homePlateRegion;

    public void onResume() {
        super.onResume();
        initGestureCoordinates();
    }


    private void hitGesture(GestureOverlayView gestureOverlayView, Gesture gesture) {

        if (screenHeight == 0.0) {
            initGestureCoordinates();
        }

        if (state == ScoringState.fielding) {
            game.setNewBatter();
        }

        state = ScoringState.fielding;

        ArrayList<GestureStroke> strokes = gesture.getStrokes();
        GestureStroke stroke = strokes.get(strokes.size() - 1);
        boolean isFlyBall = true;

        if (stroke.computeOrientedBoundingBox().height > 60) {
            _gestureName.setText("Groundball");
            isFlyBall = false;
        }
        else {
            _gestureName.setText("Fly ball");
        }

        double topStroke = stroke.boundingBox.top;
        double leftStroke = stroke.boundingBox.left;
        double rightStroke = stroke.boundingBox.right;
        double strokeX = leftStroke;

        if ((centerWidth - leftStroke) < (rightStroke - centerWidth)) {
            strokeX = rightStroke;
        }

        FieldView[] fieldViews = {pitcherFieldView, catcherFieldView, firstBaseFieldView, secondBaseFieldView, thirdBaseFieldView, shortStopFieldView, leftFieldView, centerFieldView, rightFieldView};

        boolean handled = false;

        for (FieldView fv : fieldViews) {
            if (fv.containsPoint(strokeX, topStroke)) {
                _gestureName.append(" to " + fv.getPlayer().getFullName());
                if (isFlyBall) {
                    currentFielder = fv;
                    currentFielder.setBackgroundColor(Color.BLUE);
                    fv.flyBallTo(game);
                }
                else {
                    currentFielder = fv;
                    currentFielder.setBackgroundColor(Color.CYAN);
                    fv.groundBallTo(game);
                }
                handled = true;
                if (fv != currentFielder) {
                    currentFielder.setBackgroundColor(Color.argb(255, 144, 144, 144));
                }
                break;
            }
        }

        if (!handled) {
            for (FieldRegion reg : fieldRegions) {
                if (reg.containsPoint(strokeX, topStroke)) {

                    Log.i("reg", "" + reg);
                    if (reg.getPosition() == null && reg.topCoordinate == 0.0) {
                        if (isFlyBall) {
                            Play play = game.createNewPlay("INPLAY", false);
                            play.setPlayText("HR");
                            game.homeRun();
                            game.setNewBatter();
                            state = ScoringState.pitching;
                            _gestureName.setText("HomeRun!");
                            break;
                        } else {
                            Play play = game.createNewPlay("INPLAY", false);
                            play.setPlayText("GR2B");
                            game.groundRuleDouble();
                            game.setNewBatter();
                            state = ScoringState.pitching;
                            _gestureName.setText("GroundRule Double!");
                            break;
                        }
                    }

                    else if (reg.getPosition() == null) {
                        game.foul();
                        _gestureName.setText("Foul Ball!");
                        break;
                    }

                    else {
                        _gestureName.append(" to " + reg.getPosition().toString());
                        if (isFlyBall) {
                            reg.getPosition().flyBallTo(game, true);
                        } else {
                            reg.getPosition().groundBallTo(game, true);
                        }
                    }
                    break;
                }
            }
        }

        Log.i("BasePath", "First Base -> " + game.basePath.getFirstBase().getRunnerOnBase());
        Log.i("BasePath", "Second Base -> " + game.basePath.getSecondBase().getRunnerOnBase());
        Log.i("BasePath", "Third Base -> " + game.basePath.getThirdBase().getRunnerOnBase());
        Log.i("BasePath", "Home Plate -> " + game.basePath.getHomeBase().getRunnerOnBase());

    }


    private void fieldingGesture(GestureOverlayView gestureOverlayView, Gesture gesture) {

        if (screenHeight == 0.0) {
            initGestureCoordinates();
        }

        RectF boundingBox = gesture.getBoundingBox();

        float endThrowX;
        float endThrowY;

        FieldView[] fieldViews = {pitcherFieldView, catcherFieldView, firstBaseFieldView, secondBaseFieldView, thirdBaseFieldView, shortStopFieldView, leftFieldView, centerFieldView, rightFieldView};

        if (boundingBox.left == startThrowX) {
            endThrowX = boundingBox.right;
        }
        else {
            endThrowX = boundingBox.left;
        }

        if (boundingBox.top == startThrowY) {
            endThrowY = boundingBox.bottom;
        }
        else {
            endThrowY = boundingBox.top;
        }

        /*Log.i("fieldingGesture", "startThrowY = " + startThrowY);
        Log.i("fieldingGesture", "endThrowY = " + endThrowY);
        Log.i("fieldingGesture", "startThrowX = " + startThrowX);
        Log.i("fieldingGesture", "endThrowX = " + endThrowX);*/

        boolean assisted = false;


        for (FieldView fv : fieldViews) {
            if (fv == currentFielder) {
                continue;
            }
            if (fv.containsPoint((double) endThrowX, (double) endThrowY)) {
                Log.i("fieldingGesture", fv.getPlayer().getFullName() + " caught the ball");
                currentFielder = fv;
                currentFielder.setBackgroundColor(Color.argb(255, 255, 0, 0));
                String playText = game.currentPlay.getPlayText();
                game.currentPlay.setPlayText(playText + fv.getPositions().getPositionNumber());
                break;
            }

            assisted = true;

            if (currentFielder != fv) {
                currentFielder.setBackgroundColor(Color.argb(255, 166, 0, 166));
            }

        }

        if (assisted) {
            for (BaseRegion br : baseRegions) {
                if (br.containsPoint((double) endThrowX, (double) endThrowY)) {
                    currentBase = br;
                    Player p = currentBase.getBase().getRunnerOnBase();
                    Log.i("fieldingGesture", "tagged " + p + " @ " + currentBase.getBase().getBaseNumber());
                    if (p != null) {
                        game.runnerOut(br.getBase(), p);
                    }
                    break;
                }
            }
        }
    }



    public boolean runnerMove(RunnerView rv, int dx, int dy) {
        int left = rv.getLeft() + dx;
        int top = rv.getTop() + dy;
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);
        layout.setMargins(left, top, (int) screenWidth-(left+rv.width), (int) screenHeight-(top+rv.height));
        fieldLayout.updateViewLayout(rv, layout);
        return true;
    }



    public void snapToBase(RunnerView rv)
    {
        if (screenHeight == 0.0) {
            initGestureCoordinates();
        }

        int left = rv.getLeft();
        int top = rv.getTop();
        int right = rv.getRight();
        int bottom = rv.getBottom();

        int lastLeft = rv.getViewLastLeft();
        int lastTop = rv.getViewLastTop();
        int lastRight = rv.getViewLastRight();
        int lastBottom = rv.getViewLastBottom();

        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);

        Base currentBase = rv.getBase();
        Base newBase;

        if (top > catcherY1 && top < catcherY2 && right > middleBaseX1 && left < middleBaseX2) {
            Log.i("snapToBase", rv.getPlayer().getFullName() + " reached HomePlate");
            layout.setMargins((int) (gestureOverlayView.getWidth() * .46), (int) homePlateY, (int) middleBaseX2, (int) (screenHeight - homePlateY));
            fieldLayout.updateViewLayout(rv, layout);
            newBase = game.basePath.getHomeBase();
        }

        else if (top > outfieldY && top < secondBaseY && right > middleBaseX1 && left < middleBaseX2) {
            Log.i("snapToBase", rv.getPlayer().getFullName() + " reached SecondBase");
            layout.setMargins((int) (gestureOverlayView.getWidth() * .46), (int) (gestureOverlayView.getHeight() * .32), (int) middleBaseX2, (int) (screenHeight - outfieldY));
            fieldLayout.updateViewLayout(rv, layout);
            newBase = game.basePath.getSecondBase();
        }

        else if (top < cornerBaseY1 && top > cornerBaseY2 && right > gestureOverlayView.getWidth() * .20 && left < gestureOverlayView.getWidth() * .30) {
            Log.i("snapToBase", rv.getPlayer().getFullName() + " reached ThirdBase");
            layout.setMargins((int) thirdBaseX, (int) (gestureOverlayView.getHeight() * .54), (int) (screenWidth - thirdBaseX), (int) (screenHeight - cornerBaseY2));
            fieldLayout.updateViewLayout(rv, layout);
            newBase = game.basePath.getThirdBase();
        }

        else {
            Log.i("snapToBase", "Not in ranges");

            Log.i("snapToBase", "Left: " + left);
            Log.i("snapToBase", "Top: " + top);
            Log.i("snapToBase", "Right: " + right);
            Log.i("snapToBase", "Bottom: " + bottom);

            Log.i("snapToBase", "Last Left: " + lastLeft);
            Log.i("snapToBase", "Last Top: " + lastTop);
            Log.i("snapToBase", "Last Right: " + lastRight);
            Log.i("snapToBase", "Last Bottom: " + lastBottom);

            newBase = null;

            if (lastTop > outfieldY && lastTop < secondBaseY && lastRight > middleBaseX1 && lastLeft < middleBaseX2) {
                Log.i("snapToBase", "SecondBase");
                layout.setMargins((int) (gestureOverlayView.getWidth() * .46), (int) (gestureOverlayView.getHeight() * .32), (int) middleBaseX2, (int) (screenHeight - outfieldY));
                fieldLayout.updateViewLayout(rv, layout);
            }

            else if (lastTop < cornerBaseY1 && lastTop > cornerBaseY2 && lastRight > gestureOverlayView.getWidth() * .20 && lastLeft < gestureOverlayView.getWidth() * .30) {
                Log.i("snapToBase", "ThirdBase");
                layout.setMargins((int) thirdBaseX, (int) (gestureOverlayView.getHeight() * .54), (int) (screenWidth - thirdBaseX), (int) (screenHeight - cornerBaseY2));
                fieldLayout.updateViewLayout(rv, layout);
            }

            else if (lastTop < cornerBaseY1 && lastTop > cornerBaseY2 && lastRight > gestureOverlayView.getWidth() * .70 && lastLeft < gestureOverlayView.getWidth() * .80) {
                Log.i("snapToBase", "FirstBase");
                layout.setMargins((int) (gestureOverlayView.getWidth() * .74), (int) (gestureOverlayView.getHeight() * .54), (int) (gestureOverlayView.getWidth() * .80), (int) cornerBaseY1);
                fieldLayout.updateViewLayout(rv, layout);
            }
        }

        if (newBase != null && rv.getBase() != newBase) {
            if (rv.getPlayer() == game.getCurrentBatter().getPlayer()) {
                Play play = game.currentPlay;
                String playText = play.getPlayText().substring(1);
                if (newBase.getBaseNumber() == 2) {
                    play.setPlayText("D" + playText);
                }

                else if (newBase.getBaseNumber() == 3) {
                    play.setPlayText("T" + playText);
                }

                else if (newBase.getBaseNumber() == 4) {
                    play.setPlayText("IPHR" + playText);
                }
            }
            game.advanceBase(rv.getPlayer(), currentBase, newBase);
        }
    }

    public void addToBase(RunnerView rv, Base base) {

        if (screenHeight == 0.0) {
            initGestureCoordinates();
        }

        Log.i("addToBase", "adding " + rv.getPlayer().toString() + " to " + base.getBaseNumber());

        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height, Gravity.TOP | Gravity.LEFT);
        if (base.getBaseNumber() == 1) {
            layout.setMargins((int) firstBaseRegion.leftCoordinate, (int) firstBaseRegion.topCoordinate, (int) firstBaseRegion.rightCoordinate, (int) firstBaseRegion.bottomCoordinate);
            fieldLayout.addView(rv, layout);
        }
        else if (base.getBaseNumber() == 2) {
            layout.setMargins((int) secondBaseRegion.leftCoordinate, (int) secondBaseRegion.topCoordinate, (int) secondBaseRegion.rightCoordinate, (int) secondBaseRegion.bottomCoordinate);
            fieldLayout.addView(rv, layout);
        }
        else if (base.getBaseNumber() == 3) {
            layout.setMargins((int) thirdBaseRegion.leftCoordinate, (int) thirdBaseRegion.topCoordinate, (int) thirdBaseRegion.rightCoordinate, (int) thirdBaseRegion.bottomCoordinate);
            fieldLayout.addView(rv, layout);
        }
        else if (base.getBaseNumber() == 4) {
            layout.setMargins((int) homePlateRegion.leftCoordinate, (int) homePlateRegion.topCoordinate, (int) homePlateRegion.rightCoordinate, (int) homePlateRegion.bottomCoordinate);
            fieldLayout.addView(rv, layout);
        }
        else {
            Log.i("addToBase", "Base Number batterOut of bounds");
        }

    }

    public void moveToBase(RunnerView rv, Base oldBase, Base newBase) {

        if (screenHeight == 0.0) {
            initGestureCoordinates();
        }

        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(RunnerView.width, RunnerView.height,Gravity.TOP | Gravity.LEFT);

        Log.i("moveToBase", "moving " + rv + " from " + oldBase + " to " + newBase);

        if (newBase.getBaseNumber() == 1) {
            layout.setMargins((int) firstBaseRegion.leftCoordinate, (int) firstBaseRegion.topCoordinate, (int) firstBaseRegion.rightCoordinate, (int) firstBaseRegion.bottomCoordinate);
            fieldLayout.updateViewLayout(rv, layout);

        }
        else if (newBase.getBaseNumber() == 2) {
            layout.setMargins((int) secondBaseRegion.leftCoordinate, (int) secondBaseRegion.topCoordinate, (int) secondBaseRegion.rightCoordinate, (int) secondBaseRegion.bottomCoordinate);
            fieldLayout.updateViewLayout(rv, layout);
        }
        else if (newBase.getBaseNumber() == 3) {
            layout.setMargins((int) thirdBaseRegion.leftCoordinate, (int) thirdBaseRegion.topCoordinate, (int) thirdBaseRegion.rightCoordinate, (int) thirdBaseRegion.bottomCoordinate);
            fieldLayout.updateViewLayout(rv, layout);
        }
        else if (newBase.getBaseNumber() == 4) {
            layout.setMargins((int) homePlateRegion.leftCoordinate, (int) homePlateRegion.topCoordinate, (int) homePlateRegion.rightCoordinate, (int) homePlateRegion.bottomCoordinate);
            fieldLayout.updateViewLayout(rv, layout);
            rv.removePlayer();
            removeFromBase(rv);
        }
        else {
            Log.i("moveToBase", "Base Number batterOut of bounds");
        }

    }


    public void removeFromBase(RunnerView rv) {
        fieldLayout.removeView(rv);
    }

    private TextView _gestureName;
    private TextView _pitchName;

    Game game;

    private void initGestureCoordinates() {
        screenHeight = gestureOverlayView.getHeight();
        screenWidth = gestureOverlayView.getWidth();
        centerWidth = gestureOverlayView.getWidth() * .5;
        catcherY1 = gestureOverlayView.getHeight() * .8;
        catcherY2 = gestureOverlayView.getHeight() * .9;
        homePlateY = gestureOverlayView.getHeight() * .83;
        middleBaseX1 = gestureOverlayView.getWidth() * .45;
        middleBaseX2 = gestureOverlayView.getWidth() * .55;
        cornerBaseY1 = gestureOverlayView.getHeight() * .63;
        cornerBaseY2 = gestureOverlayView.getHeight() * .53;
        cornerBasePos = gestureOverlayView.getHeight() * .58;
        infieldY1 = gestureOverlayView.getHeight() * .7;
        infieldY2 = gestureOverlayView.getHeight() * .5;
        secondBaseY = gestureOverlayView.getHeight() * .4;
        secondBasePos = gestureOverlayView.getHeight() * .35;
        outfieldY = gestureOverlayView.getHeight() * .3;
        infieldX1 = gestureOverlayView.getWidth() * .25;
        infieldX2 = gestureOverlayView.getWidth() * .75;
        thirdBaseX = gestureOverlayView.getWidth() * .17;
        firstBaseX = gestureOverlayView.getWidth() * .83;
        outfieldX1 = gestureOverlayView.getWidth() * (1 / 3.0);
        outfieldX2 = gestureOverlayView.getWidth() * (2 / 3.0);
        homeRunY = gestureOverlayView.getHeight() * .1;

        initRegions();
    }

    double screenHeight;
    double screenWidth;
    double centerWidth;
    double catcherY1;
    double catcherY2;
    double homePlateY;
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
    double cornerBasePos;
    double thirdBaseX;
    double firstBaseX;
    double outfieldX1;
    double outfieldX2;
    double homeRunY;

    TextView homeTeamTitleTextView;
    TextView awayTeamTitleTextView;
    RadioButton ball1Button;
    RadioButton ball2Button;
    RadioButton ball3Button;
    RadioButton strike1Button;
    RadioButton strike2Button;
    RadioButton out1Button;
    RadioButton out2Button;
    TextView homeTeamScoreTextView;
    TextView awayTeamScoreTextView;
    TextView inningTextView;
    FieldView pitcherFieldView;
    FieldView catcherFieldView;
    FieldView firstBaseFieldView;
    FieldView secondBaseFieldView;
    FieldView thirdBaseFieldView;
    FieldView shortStopFieldView;
    FieldView leftFieldView;
    FieldView centerFieldView;
    FieldView rightFieldView;

    private String awayTeamName;
    private String homeTeamName;
    private int inningNum;
    private String gameTypeName;

    public void initializeViews ()
    {
        homeTeamTitleTextView = (TextView) findViewById(R.id.homeScore_View);
        awayTeamTitleTextView = (TextView) findViewById(R.id.awayScore_View);
        ball1Button = (RadioButton)findViewById(R.id.ball1);
        ball2Button = (RadioButton)findViewById(R.id.ball2);
        ball3Button = (RadioButton)findViewById(R.id.ball3);
        strike1Button = (RadioButton)findViewById(R.id.strike1);
        strike2Button = (RadioButton)findViewById(R.id.strike2);
        out1Button = (RadioButton)findViewById(R.id.out1);
        out2Button = (RadioButton)findViewById(R.id.out2);
        homeTeamScoreTextView = (TextView)findViewById(R.id.homeScoreNumber_View);
        awayTeamScoreTextView = (TextView)findViewById(R.id.awayScoreNumber_View);
        inningTextView = (TextView)findViewById(R.id.inning_View);
        ballLayout = (FrameLayout)findViewById(R.id.ballZoneLayout);
        strikeLayout = (FrameLayout)findViewById(R.id.strikeZoneLayout);
        pitcherFieldView = (FieldView)findViewById(R.id.pitcherFieldView);
        catcherFieldView = (FieldView)findViewById(R.id.catcherFieldView);
        firstBaseFieldView = (FieldView)findViewById(R.id.firstBaseFieldView);
        secondBaseFieldView = (FieldView)findViewById(R.id.secondBaseFieldView);
        thirdBaseFieldView = (FieldView)findViewById(R.id.thirdBaseFieldView);
        shortStopFieldView = (FieldView)findViewById(R.id.shortStopFieldView);
        leftFieldView = (FieldView)findViewById(R.id.leftFieldView);
        centerFieldView = (FieldView)findViewById(R.id.centerFieldView);
        rightFieldView = (FieldView)findViewById(R.id.rightFieldView);
    }

    public void viewHomeScorecard (View b)
    {
        Intent homeScorecard = new Intent(this, HomeTeamScorecardActivity.class);

        int idToPass = 1;

        homeScorecard.putExtra("ID to Pass", idToPass);

        startActivity(homeScorecard);
    }

    public void viewAwayScorecard (View b)
    {
        Intent awayScorecard = new Intent(this, AwayTeamScorecardActivity.class);

        int idToPass = 1;

        awayScorecard.putExtra("ID to Pass", idToPass);

        startActivity(awayScorecard);
    }

    public void startGame (Intent i)
    {
        initializeViews();
        Log.i("scorecard", "Start game was called");

        GameType gameType = new GameType(gameTypeName, inningNum);

        homeTeamTitleTextView.setText(homeTeamName);
        awayTeamTitleTextView.setText(awayTeamName);

        game = new Game(new Team(homeTeamName), homeTeamLineup, homeTeamPositions, new Team(awayTeamName), awayTeamLineup, awayTeamPositions, gameType);

        game.addListener(this);

        game.setHomeTeam(getHomeTeam());
        game.setAwayTeam(getAwayTeam());

        game.setCurrentHalfInning( new HalfInning(getAwayTeam(), getHomeTeam(), 1, 1));

        inningTextView.setText("Top of the 1st");

        setCurrentBattingOrder();

        setFieldTextViews();

        game.setCurrentInning( new Inning(game, 1, getCurrentHalfInning(), null));

        game.addInning(getCurrentInning());

        game.startHalfInning(getCurrentHalfInning());
    }

    private Team getAwayTeam() {
        return game.getAwayTeam();
    }

    private Team getHomeTeam() {
        return game.getHomeTeam();
    }

    private HalfInning getCurrentHalfInning() {
        return game.getCurrentHalfInning();
    }



    private ArrayList<Player> getCurrentBattingOrder() {
        return game.getCurrentBattingOrder();
    }

    private AtBat getCurrentBatter() {
        return game.getCurrentBatter();
    }

    private int getTopOrBottom() {
        return game.getTopOrBottom();
    }

    private int getCurrentBattingOrderPosition() {
        return game.getCurrentBattingOrderPosition();
    }

    private Inning getCurrentInning() {
        return game.getCurrentInning();
    }



    public void setFieldTextViews()
    {
        if (game.getTopOrBottom() == 1)
        {
            for (int i = 0; i < game.homeTeamPositions.size(); i ++)
            {
                if (game.homeTeamPositions.get(i).getPosition().toString() == "Pitcher")
                {
                    pitcherFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    pitcherFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    pitcherFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Catcher")
                {
                    catcherFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    catcherFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    catcherFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "First Base")
                {
                    firstBaseFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    firstBaseFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    firstBaseFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Second Base")
                {
                    secondBaseFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    secondBaseFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    secondBaseFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Short Stop")
                {
                    shortStopFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    shortStopFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    shortStopFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Third Base")
                {
                    thirdBaseFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    thirdBaseFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    thirdBaseFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Center Field")
                {
                    centerFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    centerFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    centerFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Right Field")
                {
                    rightFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    rightFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    rightFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.homeTeamPositions.get(i).getPosition().toString() == "Left Field")
                {
                    leftFieldView.setPositions(game.homeTeamPositions.get(i).getPosition());
                    leftFieldView.setPlayer(game.homeTeamPositions.get(i).getPlayer());
                    leftFieldView.setText(game.homeTeamPositions.get(i).getPlayer().getLastName());
                }
            }
        }
        else
        {
            for (int i = 0; i < game.awayTeamPositions.size(); i ++)
            {
                if (game.awayTeamPositions.get(i).getPosition().toString() == "Pitcher")
                {
                    pitcherFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    pitcherFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    pitcherFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Catcher")
                {
                    catcherFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    catcherFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    catcherFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "First Base")
                {
                    firstBaseFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    firstBaseFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    firstBaseFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Second Base")
                {
                    secondBaseFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    secondBaseFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    secondBaseFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Short Stop")
                {
                    shortStopFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    shortStopFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    shortStopFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Third Base")
                {
                    thirdBaseFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    thirdBaseFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    thirdBaseFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Center Field")
                {
                    centerFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    centerFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    centerFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Right Field")
                {
                    rightFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    rightFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    rightFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }

                else if (game.awayTeamPositions.get(i).getPosition().toString() == "Left Field")
                {
                    leftFieldView.setPositions(game.awayTeamPositions.get(i).getPosition());
                    leftFieldView.setPlayer(game.awayTeamPositions.get(i).getPlayer());
                    leftFieldView.setText(game.awayTeamPositions.get(i).getPlayer().getLastName());
                }
            }
        }
    }



    public void setScoreTextView ()
    {
        if (getCurrentHalfInning().topOrBottom() == 1)
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


    public void setCurrentBattingOrder ()
    {
        /*if (game.getTopOrBottom() == 1)
        {
            game.setHomeTeamBattingOrderPosition(getCurrentBattingOrderPosition());
            game.setCurrentBattingOrder(game.getAwayTeamBattingOrder());
            game.setCurrentBattingOrderPosition(getAwayTeamBattingOrderPosition());
        }
        else
        {
            game.setAwayTeamBattingOrderPosition(getCurrentBattingOrderPosition());
            game.setCurrentBattingOrder(game.getHomeTeamBattingOrder());
            game.setCurrentBattingOrderPosition(game.getHomeTeamBattingOrderPosition());
        }*/

    }

    private int getAwayTeamBattingOrderPosition() {
        return game.getAwayTeamBattingOrderPosition();
    }

    private ArrayList<Player> getHomeTeamBattingOrder() {
        return game.getHomeTeamBattingOrder();
    }

    private ArrayList<Player> getAwayTeamBattingOrder() {
        return game.getAwayTeamBattingOrder();
    }

    public void incrementBattingOrderPosition (int oldPosition)
    {
        if (oldPosition < 8)
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + getCurrentBattingOrderPosition());
            game.setCurrentBattingOrderPosition(game.getCurrentBattingOrderPosition() + 1);
            System.out.println("New position is " + getCurrentBattingOrderPosition());
        }

        else
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + getCurrentBattingOrderPosition());
            game.setCurrentBattingOrderPosition(0);
            System.out.println("New position is " + getCurrentBattingOrderPosition());
        }
    }



    public void endGame ()
    {
        Context context = getApplicationContext();
        System.out.println("The winner of the game is " + game.getGameWinner().toString());
        CharSequence text = "The winner of the game is " + game.getGameWinner().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast gameWinnerToast = Toast.makeText(context, text, duration);
        gameWinnerToast.show();
    }

    @Override
    public void strikeCalled(int n)
    {
        if (n == 1)
        {
            strike1Button.setChecked(true);
        }

        if (n == 2)
        {
            strike1Button.setChecked(true);
            strike2Button.setChecked(true);
        }
    }

    @Override
    public void ballCalled(int n)
    {
        if (n == 1)
        {
            ball1Button.setChecked(true);
        }

        if (n == 2)
        {
            ball1Button.setChecked(true);
            ball2Button.setChecked(true);
        }

        if (n == 3)
        {
            ball1Button.setChecked(true);
            ball2Button.setChecked(true);
            ball3Button.setChecked(true);
        }
    }

    @Override
    public void outOccured(int n)
    {
        if (n == 1)
        {
            out1Button.setChecked(true);
        }

        else if (n == 2)
        {
            out1Button.setChecked(true);
            out2Button.setChecked(true);
        }
    }

    @Override
    public void homeRunsScored(int n)
    {
        homeTeamScoreTextView.setText(Integer.toString(n));
        System.out.println("The home team score text view has been set to " + n);
    }

    @Override
    public void awayRunsScored(int n)
    {
        awayTeamScoreTextView.setText(Integer.toString(n));
        System.out.println("The away team score text view has been set to " + n);
    }

    @Override
    public void atBatEnded()
    {
        state = ScoringState.pitching;

        ball1Button.setChecked(false);
        ball2Button.setChecked(false);
        ball3Button.setChecked(false);
        strike1Button.setChecked(false);
        strike2Button.setChecked(false);
    }

    public void inningEnded ()
    {
        ball1Button.setChecked(false);
        ball2Button.setChecked(false);
        ball3Button.setChecked(false);
        strike1Button.setChecked(false);
        strike2Button.setChecked(false);
        out1Button.setChecked(false);
        out2Button.setChecked(false);

        state = ScoringState.pitching;

        if (game.basePath.areThereAnyRunnersOnBase()) {
            if (game.basePath.getFirstBase().doesBaseHaveRunner()) {
                game.basePath.getFirstBase().removeRunnerOnBase();
            }
            if (game.basePath.getSecondBase().doesBaseHaveRunner()) {
                game.basePath.getSecondBase().removeRunnerOnBase();
            }
            if (game.basePath.getThirdBase().doesBaseHaveRunner()) {
                game.basePath.getThirdBase().removeRunnerOnBase();
            }
        }

        setFieldTextViews();
        setCurrentBattingOrder();
        _gestureName.setText("Play");

        pitcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        catcherFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        firstBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        secondBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        thirdBaseFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        shortStopFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        leftFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        centerFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));
        rightFieldView.setBackgroundColor(Color.argb(255, 144, 144, 144));


        if (game.getCurrentHalfInning().topOrBottom() == 1) {
            inningTextView.setText("Top of the ");


        }
        else {
            inningTextView.setText("Bottom of the ");
        }

        if (game.getCurrentInningCount() == 1) {
            inningTextView.append("1st");
        }

        else if (game.getCurrentInningCount() == 2) {
            inningTextView.append("2nd");
        }

        else if (game.getCurrentInningCount() == 3) {
            inningTextView.append("3rd");
        }

        else {
            inningTextView.append(game.getCurrentInningCount() + "th");
        }


    }

    public void gameEnded ()
    {
        ball1Button.setChecked(false);
        ball2Button.setChecked(false);
        ball3Button.setChecked(false);
        strike1Button.setChecked(false);
        strike2Button.setChecked(false);
        out1Button.setChecked(false);
        out2Button.setChecked(false);
    }

    public void runnerTagged(RunnerView runnerView) {
        Player p = runnerView.getPlayer();
        Log.i("fieldingGesture", "tagged " + p + " @ " + runnerView.getBase().getBaseNumber());
        if (p != null) {
            game.runnerOut(runnerView.getBase(), p);
        }
    }

    private static class FieldRegion {
        private double topCoordinate;
        private double rightCoordinate;
        private double bottomCoordinate;
        private double leftCoordinate;
        private Positions position;


        public FieldRegion (Positions position, double topCoordinate, double rightCoordinate, double bottomCoordinate, double leftCoordinate) {
            this.position = position;
            this.topCoordinate = topCoordinate;
            this.rightCoordinate = rightCoordinate;
            this.bottomCoordinate = bottomCoordinate;
            this.leftCoordinate = leftCoordinate;
        }

        public Positions getPosition() {return position;}

        public boolean containsPoint(double x, double y) {
            /*Log.i("strokeX", "" + x);
            Log.i("topStroke", "" + y);
            Log.i("Gestures", "Checking Contains " + this.getPosition() + " " + topCoordinate + ", " + rightCoordinate + ", " + bottomCoordinate + ", " + leftCoordinate + "\n");
            */
            if (x < leftCoordinate) {
                return false;
            }
            if (x > rightCoordinate) {
                return false;
            }
            if (y < topCoordinate) {
                return false;
            }
            if (y > bottomCoordinate) {
                return false;
            }
            return true;
        }
    }

    private static class BaseRegion {
        private double topCoordinate;
        private double rightCoordinate;
        private double bottomCoordinate;
        private double leftCoordinate;
        private Base base;

        public BaseRegion (Base base, double leftCoordinate, double topCoordinate, double rightCoordinate, double bottomCoordinate) {
            this.base = base;
            this.leftCoordinate = leftCoordinate;
            this.topCoordinate = topCoordinate;
            this.rightCoordinate = rightCoordinate;
            this.bottomCoordinate = bottomCoordinate;
        }


        public Base getBase() { return base; }

        public boolean containsPoint(double x, double y) {
            /*Log.i("strokeX", "" + x);
            Log.i("topStroke", "" + y);
            Log.i("Gestures", "Checking Contains " + this.getPosition() + " " + topCoordinate + ", " + rightCoordinate + ", " + bottomCoordinate + ", " + leftCoordinate + "\n");
            */
            if (x < leftCoordinate) {
                return false;
            }
            if (x > rightCoordinate) {
                return false;
            }
            if (y < topCoordinate) {
                return false;
            }
            if (y > bottomCoordinate) {
                return false;
            }
            return true;
        }

    }

    private ArrayList<Player> homeTeamLineup = new ArrayList<>();
    private ArrayList<Player> awayTeamLineup = new ArrayList<>();
    private ArrayList<PositionsInGame> homeTeamPositions = new ArrayList<>();
    private ArrayList<PositionsInGame> awayTeamPositions = new ArrayList<>();

}
