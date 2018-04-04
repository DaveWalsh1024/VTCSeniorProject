package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Play
{

    public Player getBatter () { return batter; }

    public Player getPitcher () { return  pitcher; }

    public Pitch getPlayPitch () { return playPitch; }

    public String getPlayText () { return playText; }

    public int getInningNumber () { return inningNumber; }

    public int getLineupNumber () { return lineupNumber; }

    public AtBat getAtBat () { return atBat; }

    public int getTopOrBottom () { return topOrBottom; }

    public void setPlayText (String newText)
    {
        playText = newText;
    }

    public void addRunnerEvent (RunnerEvent runnerEvent) { runnerEvents.add(runnerEvent);}

    public boolean getOut () { return out; }

    public boolean areThereRunnerEvents ()
    {
        if (runnerEvents.size() == 0)
        {
            return false;
        }

        else
            {
            return true;
        }
    }

    public ArrayList<RunnerEvent> getRunnerEvents () {return runnerEvents;}

    public Play(Player batter, Player pitcher, Pitch playPitch, AtBat atBat, int inningNumber, int lineupNumber, int topOrBottom, boolean out )
    {
        this.batter = batter;
        this.pitcher = pitcher;
        this.playPitch = playPitch;
        this.playText = " ";
        this.atBat = atBat;
        this.inningNumber = inningNumber;
        this.lineupNumber = lineupNumber;
        this.topOrBottom = topOrBottom;
        this.playId = this.playId + 1;
        this.out = out;
        this.runnerEvents = new ArrayList<>();
        repOk();
    }

    public void repOk()
    {
        assert batter != null;
        assert pitcher != null;
        assert playPitch != null;
    }

    private Player batter;
    private Player pitcher;
    private Pitch playPitch;
    private String playText;
    private AtBat atBat;
    private int inningNumber;
    private int lineupNumber;
    private int topOrBottom;
    private boolean out;
    private static int playId;
    private ArrayList <RunnerEvent> runnerEvents;

}
