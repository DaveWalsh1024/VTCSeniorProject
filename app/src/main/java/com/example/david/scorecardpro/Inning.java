package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Inning
{
    public int getInningCount () { return inningNumber; }

    public HalfInning getTopInning () { return topInning; }

    public HalfInning getBottomInning () { return bottomInning; }

    public void setInningNumber (int number)
    {
        inningNumber = number;
    }




    public Inning (int inningCount, HalfInning topInning, HalfInning bottomInning)
    {
        this.inningNumber = inningCount;
        this.halfInnings = new ArrayList<>();
        this.topInning = topInning;
        this.bottomInning = bottomInning;
        repOk();
    }

    public void repOk()
    {
        assert inningNumber > 0;
    }

    private ArrayList <HalfInning> halfInnings;
    private int inningNumber;
    private HalfInning topInning;
    private HalfInning bottomInning;
}
