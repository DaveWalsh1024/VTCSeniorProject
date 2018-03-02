package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class HalfInning
{
    public int getOuts () { return outs; }

    public int topOrBottom ()
    {
        return topOrBottom;
    }

    public void incrementRunsScored ()
    {
        runsScored++;
    }

    public void incrementOuts ()
    {
        outs++;
    }

    public int getInningNumber() { return inning; }

    public ArrayList<AtBat> getBatters () { return batters; }

    public void addAtBat (AtBat atBatToAdd)
    {
        batters.add(atBatToAdd);
    }


    public HalfInning (Team battingTeam, Team pitchingTeam, int topOrBottom, int inning)
    {
        this.batters = new ArrayList<>();
        this.battingTeam = battingTeam;
        this.pitchingTeam = pitchingTeam;
        this.topOrBottom = topOrBottom;
        this.inning = inning;
        repOk();
    }

    public void repOk()
    {
        assert battingTeam != null;
        assert pitchingTeam != null;
    }

    private ArrayList <AtBat> batters;
    private int outs;
    private Team battingTeam;
    private Team pitchingTeam;
    private int topOrBottom; //1 for top of inning, 2 for bottom of inning
    private int inning;
    int runsScored;
}
