package com.example.david.scorecardpro;

import com.example.david.scorecardpro.AtBat;
import com.example.david.scorecardpro.Base;

/**
 * Created by david on 3/7/2018.
 */

public class RunnerEvent
{
    public int getCurrentInning () { return currentInning; }

    public int getCurrentBattingOrderPosition () { return currentBattingOrderPosition; }

    public AtBat getCurrentBatter () { return currentBatter; }

    public boolean wasOut () { return out; }

    public Player getRunner () { return runner; }

    public Base getStartingBase () { return startingBase; }

    public Base getEndingBase () { return endingBase; }

    public int getRunnerBattingOrderPosiiton () { return runnerBattingOrderPosiiton; }

    public void updateScorecardBox (ScorecardBox scorecardBox)
    {
        if (out)
        {
            return;
        }

        if (startingBase.getBaseNumber() == 4 && endingBase.getBaseNumber() == 1)
        {
            scorecardBox.drawBaseLine(0,1);
        }

        else if (startingBase.getBaseNumber() == 4 && endingBase.getBaseNumber() == 2)
        {
            scorecardBox.drawBaseLine(0,1);
            scorecardBox.drawBaseLine(1,2);
        }

        else if (startingBase.getBaseNumber() == 4 && endingBase.getBaseNumber() == 3)
        {
            scorecardBox.drawBaseLine(0,1);
            scorecardBox.drawBaseLine(1,2);
            scorecardBox.drawBaseLine(2,3);
        }

        else if (startingBase.getBaseNumber() == 4 && endingBase.getBaseNumber() == 4)
        {
            scorecardBox.drawBaseLine(0,1);
            scorecardBox.drawBaseLine(1,2);
            scorecardBox.drawBaseLine(2,3);
            scorecardBox.drawBaseLine(3,0);
        }

        else if (startingBase.getBaseNumber() == 1 && endingBase.getBaseNumber() == 2)
        {
            scorecardBox.drawBaseLine(1,2);
        }

        else if (startingBase.getBaseNumber() == 1 && endingBase.getBaseNumber() == 3)
        {
            scorecardBox.drawBaseLine(1,2);
            scorecardBox.drawBaseLine(2,3);
        }

        else if (startingBase.getBaseNumber() == 1 && endingBase.getBaseNumber() == 4)
        {
            scorecardBox.drawBaseLine(1,2);
            scorecardBox.drawBaseLine(2,3);
            scorecardBox.drawBaseLine(3,0);
        }

        else if (startingBase.getBaseNumber() == 2 && endingBase.getBaseNumber() == 3)
        {
            scorecardBox.drawBaseLine(2,3);
        }

        else if (startingBase.getBaseNumber() == 2 && endingBase.getBaseNumber() == 4)
        {
            scorecardBox.drawBaseLine(2,3);
            scorecardBox.drawBaseLine(3,0);
        }

        else if (startingBase.getBaseNumber() == 3 && endingBase.getBaseNumber() == 4)
        {
            scorecardBox.drawBaseLine(3,0);
        }

    }

    public RunnerEvent (int currentInning, int currentBattingOrderPosition, AtBat currentBatter, boolean out, Base startingBase, Base endingBase, Player runner, int runnerBattingOrderPosiiton)
    {
        this.currentInning = currentInning;
        this.currentBattingOrderPosition = currentBattingOrderPosition;
        this.currentBatter = currentBatter;
        this.out = out;
        this.startingBase = startingBase;
        this.endingBase = endingBase;
        this.runner = runner;
        this.runnerBattingOrderPosiiton = runnerBattingOrderPosiiton;
    }

    private int currentInning;
    private int currentBattingOrderPosition;
    private AtBat currentBatter;
    private boolean out;
    private Base startingBase;
    private Base endingBase;
    private Player runner;
    private int runnerBattingOrderPosiiton;
}
