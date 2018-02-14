package com.example.david.scorecardpro;

import android.util.Log;

/**
 * Created by david on 12/31/2017.
 */

public class PositionsInGame
{
    public Player getPlayer () {return player; }
    public Positions getPosition () {return position; }
    public void setPlayer (Player newPlayer) { player = newPlayer; }

    public double getTopCoordinate() {
        return topCoordinate;
    }

    public double getRightCoordinate() {
        return rightCoordinate;
    }

    public double getBottomCoordinate() {
        return bottomCoordinate;
    }

    public double getLeftCoordinate() {
        return leftCoordinate;
    }

    public void setTopCoordinate(double coordinate) {
        topCoordinate = coordinate;
    }
    public void setRightCoordinate(double coordinate) {
        rightCoordinate = coordinate;
    }
    public void setBottomCoordinate(double coordinate) {
        bottomCoordinate = coordinate;
    }
    public void setLeftCoordinate(double coordinate) {
        leftCoordinate = coordinate;
    }

    public boolean containsPoint(double x, double y) {
        Log.i("Gestures", "Checking Contains " + this.getPosition() + " for " + x + ", " + y);
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


    public PositionsInGame (Player player, Positions position, double topCoordinate, double rightCoordinate, double bottomCoordinate, double leftCoordinate)
    {
        this.player = player;
        this.position = position;
        this.topCoordinate = topCoordinate;
        this.rightCoordinate = rightCoordinate;
        this.bottomCoordinate = bottomCoordinate;
        this.leftCoordinate = leftCoordinate;
    }

    private Player player;
    private Positions position;
    private double topCoordinate;
    private double rightCoordinate;
    private double bottomCoordinate;
    private double leftCoordinate;
}
