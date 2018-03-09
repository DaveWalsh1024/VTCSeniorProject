package com.example.david.scorecardpro;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;


/**
 * Created by jacklavallee on 3/6/18.
 */

public class FieldView extends AppCompatTextView {

    public void setPlayer(Player player) {
        this.player = player;
        setText(player.getFullName());
    }

    public FieldView (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        scoringActivity = (ScoringActivity) context;
    }

    public Player getPlayer() {
        return player;
    }

    public Positions getPositions() { return positions; }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }

    public void flyBallTo(Game game) {
        positions.flyBallTo(game);
    }

    public void groundBallTo(Game game) {
        positions.groundBallTo(game);
    }

    public boolean containsPoint(double x, double y) {
        //Log.i("Gestures", "Checking Contains " + this.getPlayer().getFullName() + " for " + x + ", " + y);
        if (x < getLeft()) {
            return false;
        }
        if (x > getRight()) {
            return false;
        }
        if (y < getTop()) {
            return false;
        }
        if (y > getBottom()) {
            return false;
        }
        return true;
    }

    private ScoringActivity scoringActivity;
    private Player player;
    private Positions positions;
}
