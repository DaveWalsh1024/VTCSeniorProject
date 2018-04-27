package com.example.david.scorecardpro;

import android.util.Log;

/**
 * Created by david on 12/31/2017.
 */

public enum Positions
{
    PITCHER("Pitcher"),
    CATCHER("Catcher"),
    FIRSTBASE("First Base"),
    SECONDBASE("Second Base"),
    THIRDBASE("Third Base"),
    SHORTSTOP("Short Stop"),
    LEFTFIELD("Left Field"),
    CENTERFIELD("Center Field"),
    RIGHTFIELD("Right Field");


    private String stringValue;
    private Positions(String toString)
    {
        stringValue = toString;
    }

    @Override
    public String toString()
    {
        return stringValue;
    }

    public void flyBallTo(Game game, boolean inPlay)
    {

        Play play = game.createNewPlay("INPLAY", false);

        if (inPlay) {
            game.advanceBase(game.getCurrentBatter().getPlayer(), game.basePath.getHomeBase(), game.basePath.getFirstBase());
            play.setPlayText("S" + getPositionNumber());

            //game.setNewBatter();
        }
        else {
            play.setPlayText("F" + getPositionNumber());
            Log.i("flyBallTo", "Out reached");
            game.batterOut();
        }

    }

    public void groundBallTo(Game game, boolean inPlay) {

        Play play = game.createNewPlay("INPLAY", false);

        game.advanceBase(game.getCurrentBatter().getPlayer(), game.basePath.getHomeBase(), game.basePath.getFirstBase());

        if (inPlay) {
            play.setPlayText("S" + getPositionNumber());
        }
        else {
            play.setPlayText("G" + getPositionNumber());
        }
        game.setNewBatter();
    }

    public int getPositionNumber() {
        return ordinal() + 1;
    }


}