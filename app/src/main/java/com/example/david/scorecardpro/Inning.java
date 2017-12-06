package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Inning
{
    public int getInningCount () { return inningCount; }

    public HalfInning getTopInning () { return topInning; }

    public HalfInning getBottomInning () { return bottomInning; }

    public void incrementInningCount ()
    {
        inningCount++;
        repOk();
    }




    public Inning (int inningCount, HalfInning topInning, HalfInning bottomInning)
    {
        this.inningCount = inningCount;
        this.halfInnings = new ArrayList<>();
        this.topInning = topInning;
        this.bottomInning = bottomInning;
        repOk();
    }

    public void repOk()
    {
        assert topInning != null;
        assert inningCount > 0;
    }

    private ArrayList <HalfInning> halfInnings;
    private int inningCount;
    private HalfInning topInning;
    private HalfInning bottomInning;
}
