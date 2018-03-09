package com.example.david.scorecardpro;

/**
 * Created by david on 11/1/2017.
 */

public class Base
{
    public int getBaseNumber () { return baseNumber; }
    public Player getRunnerOnBase () { return runnerOnBase; }

    public boolean doesBaseHaveRunner ()
    {
        if (runnerOnBase == null)
        {
            return false;
        }

        else
            return true;
    }


    public void setRunnerOnBase (Player player)
    {
        runnerOnBase = player;
        repOk();
    }

    public void removeRunnerOnBase ()
    {
        runnerOnBase = null;
        repOk();
    }



    public Base (int number)
    {
        this.baseNumber = number;
        repOk();
    }

    public void repOk()
    {
        assert baseNumber > 0 && baseNumber < 5;
    }

    private int baseNumber;
    private Player runnerOnBase;
}
