package com.example.david.scorecardpro;

/**
 * Created by david on 10/17/2017.
 */

public enum Pitch
{
    STRIKE("STRIKE"),
    BALL("BALL"),
    INPLAY("INPLAY"),
    FOUL("FOUL"),
    FOULPOPPEDOUT("FOULPOOPEDOUT"),
    HITBYPITCH("HITBYPITCH");

    private String stringValue;

    private Pitch(String toString)
    {
        stringValue = toString;
    }

    @Override
    public String toString()
    {
        return stringValue;
    }
}
