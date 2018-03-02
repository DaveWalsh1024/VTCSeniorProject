package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Inning
{
    public int getInningCount () { return inningNumber; }

    public void incrementInningNumber ()
    {
        inningNumber++;
    }

    public void setTopInning (HalfInning topInningToSet)
    {
        topInning = topInningToSet;
    }

    public HalfInning getTopInning () { return topInning; }

    public HalfInning getBottomInning () { return bottomInning; }

    public void setBottomInning (HalfInning bottomInningToSet)
    {
        bottomInning = bottomInningToSet;
    }



    public Inning (Game game, int inningCount, HalfInning topInning, HalfInning bottomInning)
    {
        this.game = game;
        this.inningNumber = inningCount;
        this.topInning = topInning;
        this.bottomInning = bottomInning;
        repOk();
    }

    public void repOk()
    {
        assert inningNumber > 0;
    }

    private int inningNumber;
    private HalfInning topInning;
    private HalfInning bottomInning;
    private Game game;
}
