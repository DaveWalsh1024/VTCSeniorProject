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

    public void flyBallTo(Game game)
    {
        Play play = game.createNewPlay("INPLAY", true);
        play.setPlayText("F" + getPositionNumber());

    }

    public void groundBallTo(Game game) {

    }

    public int getPositionNumber() {
        return ordinal() + 1;
    }


}