package com.example.david.scorecardpro;

/**
 * Created by david on 11/1/2017.
 */

public class BasePath
{
    public Base getFirstBase () { return firstBase; }
    public Base getSecondBase () { return secondBase; }
    public Base getThirdBase () { return thirdBase; }
    public Base getHomeBase () { return homeBase; }

    public Base getNextBase (Base base)
    {
        if (base.getBaseNumber() == 1)
        {
            return secondBase;
        }

        if (base.getBaseNumber() == 2)
        {
            return thirdBase;
        }

        if (base.getBaseNumber() == 3)
        {
            return homeBase;
        }

        else
            return null;
    }

    public void advanceRunner (Base currentBase, Base nextBase)
    {
        if (nextBase.doesBaseHaveRunner() == false)
        {
            if (nextBase == homeBase)
            {
                halfInning.incrementRunsScored();
                currentBase.removeRunnerOnBase();
            }

            else
            {
                nextBase.setRunnerOnBase(currentBase.getRunnerOnBase());
                currentBase.removeRunnerOnBase();
            }
        }

        else
        {
            advanceRunner(nextBase, getNextBase(nextBase));
            advanceRunner(currentBase, nextBase);
        }
    }

    public Base getBaseFromNumber (int number)
    {
        if (number == 1)
        {
            return firstBase;
        }

        if (number == 2)
        {
            return secondBase;
        }

        if (number == 3)
        {
            return thirdBase;
        }

        else
            return homeBase;
    }

    public BasePath ()
    {
        repOk();
    }

    public void repOk()
    {
        assert firstBase != null;
        assert secondBase != null;
        assert thirdBase != null;
        assert homeBase != null;
    }

    private HalfInning halfInning;
    private Base firstBase;
    private Base secondBase;
    private Base thirdBase;
    private Base homeBase;
}
