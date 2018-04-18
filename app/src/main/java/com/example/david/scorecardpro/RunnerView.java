package com.example.david.scorecardpro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jacklavallee on 2/21/18.
 */

public class RunnerView extends AppCompatTextView implements View.OnTouchListener {

    public RunnerView(ScoringActivity context, Player player) {
        super(context);
        scoringActivity = context;
        this.player = player;
        this.base = null;
        setOnTouchListener(this);
        setWidth(width);
        setHeight(height);
        setMaxWidth(width);
        setMaxHeight(height);
        setGravity(Gravity.CENTER);
        setText(String.valueOf(player.getNumber()));
        setTextColor(Color.rgb(255,255,255));
        setTextSize(26);
        setBackground(getResources().getDrawable(R.drawable.onbase_img));
    }

    public RunnerView(Player player) {
        this(currentActivity, player);

    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        int newX;
        int newY;

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                totalDrag = 0;
                lastX = (int) e.getX();
                lastY = (int) e.getY();
                viewLastLeft = view.getLeft();
                viewLastTop = view.getTop();
                viewLastRight = view.getRight();
                viewLastBottom = view.getBottom();
                break;
            case MotionEvent.ACTION_MOVE:
                newX = (int) e.getX();
                newY = (int) e.getY();
                totalDrag += Math.abs(newX - lastX);
                scoringActivity.runnerMove(this,newX - lastX, newY - lastY);
                break;
            case MotionEvent.ACTION_UP:
                scoringActivity.snapToBase(this);
                if (totalDrag < 10) {
                    scoringActivity.runnerTagged(this);
                }
                break;
        }
        return true;
    }

    public int getViewLastLeft() {
        return viewLastLeft;
    }

    public int getViewLastTop() {
        return viewLastTop;
    }

    public int getViewLastRight() {
        return viewLastRight;
    }

    public int getViewLastBottom() {
        return viewLastBottom;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player newPlayer) {
        player = newPlayer;
    }

    public static void setScoringActivity(ScoringActivity sa) {
        currentActivity = sa;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base newBase) {
        if (newBase == null) {
            currentActivity.removeFromBase(this);
        }
        else if (base == null) {
            currentActivity.addToBase(this, newBase);
            this.base = newBase;

        }
        else if (base != newBase) {
            currentActivity.moveToBase(this, base, newBase);
            this.base = newBase;
        }
    }

    public void removePlayer() {
        this.player = null;
    }



    public int lastX;
    public int lastY;
    public int totalDrag;
    public int viewLastLeft;
    public int viewLastTop;
    public int viewLastRight;
    public int viewLastBottom;
    private ScoringActivity scoringActivity;
    private static ScoringActivity currentActivity;
    private Player player;
    private Base base;
    public static final int width = 125;
    public static final int height = 125;


}
