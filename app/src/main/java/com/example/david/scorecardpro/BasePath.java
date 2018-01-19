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

    public boolean areThereAnyRunnersOnBase ()
    {
        if (firstBase.doesBaseHaveRunner() == false && secondBase.doesBaseHaveRunner() == false && thirdBase.doesBaseHaveRunner() == false)
        {
            return false;
        }

        else
            return true;
    }

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

    public void setRunnerOnBase (Base base, Player player)
    {
        if (base.getBaseNumber() == 1)
        {
            firstBase.setRunnerOnBase(player);
            System.out.println("Player on first base is " + firstBase.getRunnerOnBase().getFullName());
        }

        else if (base.getBaseNumber() == 2)
        {
            secondBase.setRunnerOnBase(player);
            System.out.println("Player on second base is " + secondBase.getRunnerOnBase().getFullName());
        }

        else if (base.getBaseNumber() == 3)
        {
            thirdBase.setRunnerOnBase(player);
            System.out.println("Player on third base is " + thirdBase.getRunnerOnBase().getFullName());
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
        firstBase = new Base(1);
        secondBase = new Base(2);
        thirdBase = new Base(3);
        homeBase = new Base(4);
        repOk();
    }

    public void repOk()
    {
        assert firstBase != null;
        assert secondBase != null;
        assert thirdBase != null;
        assert homeBase != null;
    }

    private Base firstBase;
    private Base secondBase;
    private Base thirdBase;
    private Base homeBase;
}
