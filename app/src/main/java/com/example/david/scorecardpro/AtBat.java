package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class AtBat
{
    public int getStrikeCount () { return strikeCount; }
    public int getBallCount () { return ballCount; }
    public HalfInning getHalfInning () { return halfInning; }
    public BasePath getField () { return basePath; }
    public void setHalfInning (HalfInning halfInning)
    {
     halfInning = halfInning;
    }

    public void incrementStrikes ()
    {
        strikeCount++;
    }

    public void incrementBalls()
    {
        ballCount++;
    }

    public AtBat (HalfInning halfInning, BasePath basePath, Player batter)
    {
        this.batter = batter;
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
    Player batter;
    private int strikeCount;
    private int ballCount;
    private ArrayList <Play> plays;
    BasePath basePath;
}
