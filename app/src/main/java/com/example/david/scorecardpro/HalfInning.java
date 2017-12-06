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
    public Inning getInning () { return inning; }

    public void setTopOrBottom (int halfInningNumber)
    {
        topOrBottom = halfInningNumber;
        repOk();
    }

    public void incrementOuts ()
    {
        //If there are less than 3 outs add one to the out count
        if (outs < 3)
        outs++;

        //This is the 3rd out so do some inning stuff
        //
        else
        {
            //If it is the top of the inning
            if(getTopOrBottom() == 1)
            {
                //Make it the bottom of the inning
                setTopOrBottom(2);
            }
            //If it is the bottom of the inning
            else
            {
                //Set the inning to the next inning
                getInning().incrementInningCount();
                // Set the half inning to the top
                setTopOrBottom(1);
            }
        }
    }

    public void incrementRunsScored ()
    {
        runsScored++;
        repOk();
    }

    public void setBattingTeam (Team newBattingTeam)
    {
        this.battingTeam = newBattingTeam;
    }

    public void setPitchingTeam (Team  newPitchingTeam)
    {
        this.pitchingTeam = newPitchingTeam;
    }


    public HalfInning (Team battingTeam, Team pitchingTeam, int topOrBottom, Inning inning, BasePath basePath)
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
        assert outs > -1 && outs < 3;
        assert runsScored > -1;
        assert battingTeam != null;
        assert pitchingTeam != null;
    }

    private ArrayList <AtBat> batters;
    private int outs;
    private Team battingTeam;
    private Team pitchingTeam;
    private int runsScored;
    private int topOrBottom; //1 for top of inning, 2 for bottom of inning
    private Inning inning;
}
