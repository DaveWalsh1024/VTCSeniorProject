package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class AtBat
{
    public int getStrikeCount () { return strikeCount; }
    public int getBallCount () { return ballCount; }
    public BasePath getField () { return basePath; }

    public void incrementStrikes ()
    {
        if (strikeCount < 3)
            strikeCount++;

        else
        {
            halfInning.incrementOuts();
        }
    }

    public void incrementBalls()
    {
        if (ballCount < 4)
            ballCount++;

        else
            return;
        //call the advanced runner method that we need to write
    }

    public AtBat (HalfInning halfInning, BasePath basePath)
    {
        this.halfInning = halfInning;
        this.plays= new ArrayList<>();
        this.basePath = basePath;
        repOk();
    }

    public void repOk()
    {
        assert strikeCount > -1 && strikeCount < 4;
        assert ballCount > -1 && ballCount <  5;
    }

    HalfInning halfInning;
    private int strikeCount;
    private int ballCount;
    private ArrayList <Play> plays;
    BasePath basePath;
}
