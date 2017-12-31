package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class HalfInning
{
    public Team getBattingTeam () { return battingTeam; }
    public Team getPitchingTeam () { return pitchingTeam; }
    public int getRunsScored () { return runsScored; }
    public int getOuts () { return outs; }
    public int getTopOrBottom () { return  topOrBottom; }
    public int getInningNumber () { return inning; }
    public void setInning (Inning inning)
    {
        inning = inning;
    }

    public void setTopOrBottom (int halfInningNumber)
    {
        topOrBottom = halfInningNumber;
        repOk();
    }

    public void incrementRunsScored ()
    {
        runsScored++;
        repOk();
    }

    public void incrementOuts ()
    {
        outs++;
    }

    public void setBattingTeam (Team newBattingTeam)
    {
        this.battingTeam = newBattingTeam;
    }

    public void setPitchingTeam (Team  newPitchingTeam)
    {
        this.pitchingTeam = newPitchingTeam;
    }

    public ArrayList getBatters ()
    {
        return batters;
    }

    public void addBatter (AtBat batter)
    {
        batters.add(batters.size()+1, batter);
    }

    public HalfInning (Team battingTeam, Team pitchingTeam, int topOrBottom, int inning, BasePath basePath)
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
    private int runsScored;
    private int topOrBottom; //1 for top of inning, 2 for bottom of inning
    private int inning;
}
