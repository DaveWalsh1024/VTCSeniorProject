package com.example.david.scorecardpro;

/**
 * Created by david on 12/31/2017.
 */

public enum Positions
{
    PITCHER("Pitcher"),
    FIRSTBASE("First Base"),
    SECONDBASE("Second Base"),
    SHORTSTOP("Short Stop"),
    THIRDBASE("Third Base"),
    CATCHER("Catcher"),
    CENTERFIELD("Center Field"),
    LEFTFIELD("Left Field"),
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

    public void flyBallTo(Game game) {

    }

    public void groundBallTo(Game game) {

    }
}