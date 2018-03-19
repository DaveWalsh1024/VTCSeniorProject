package com.example.david.scorecardpro;

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

    public void flyBallTo(Game game, boolean isHit)
    {

        Play play = game.createNewPlay("INPLAY", !isHit);

        if (isHit) {
            game.advanceBase(game.getCurrentBatter().getPlayer(), game.basePath.getHomeBase(), game.basePath.getFirstBase());
            play.setPlayText("S" + getPositionNumber());
            game.setNewBatter();
        }
        else {
            play.setPlayText("F" + getPositionNumber());
            game.out();
        }

    }

    public void groundBallTo(Game game, boolean isHit) {


    }

    public int getPositionNumber() {
        return ordinal() + 1;
    }


}