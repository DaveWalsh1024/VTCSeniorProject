package com.example.david.scorecardpro;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import static com.example.david.scorecardpro.RunnerView.height;
import static com.example.david.scorecardpro.RunnerView.width;

/**
 * Created by david on 3/23/2018.
 */

public class LineupDragable  extends AppCompatTextView implements View.OnTouchListener
{
    public LineupDragable(RosterActivity context, Player player)
    {
        super(context);
        rosterActivity = context;
        this.player = player;
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

    public LineupDragable (Player player)
    {
        this(currentActivity, player);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        int newX;
        int newY;

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
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
    //            rosterActivity.runnerMove(this,newX - lastX, newY - lastY);
                break;
            case MotionEvent.ACTION_UP:
    //            rosterActivity.snapToBase(this);
                break;
        }
        return true;
    }


    private Player player;
    public int lastX;
    public int lastY;
    public int viewLastLeft;
    public int viewLastTop;
    public int viewLastRight;
    public int viewLastBottom;
    private static RosterActivity currentActivity;
    private RosterActivity rosterActivity;
    public static final int width = 100;
    public static final int height = 100;
}
