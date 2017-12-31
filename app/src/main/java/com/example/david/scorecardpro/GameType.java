package com.example.david.scorecardpro;

/**
 * Created by david on 12/31/2017.
 */

public class GameType
{
    public String getName () { return name; }
    public int getNumInnings () { return numInnings; }

    public GameType (String name, int numInnings)
    {
        name = name;
        numInnings = numInnings;
        repOk();
    }

    public void repOk()
    {
        assert name != null;
        assert numInnings > 0;
    }

    private String name;
    private int numInnings;
}
