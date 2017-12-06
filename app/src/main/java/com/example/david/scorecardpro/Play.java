package com.example.david.scorecardpro;

/**
 * Created by david on 10/17/2017.
 */

public class Play
{
    public Player getBatter () { return batter; }

    public Player getPitcher () { return  pitcher; }

    public Pitch getPlayPitch () { return playPitch; }

    public AtBat getAtBat () { return atBat; }

    public void testPitch (Pitch playPitch)
    {
        if (playPitch.toString() == "STRIKE")
        {
            atBat.incrementStrikes();
        }

        else if (playPitch.toString() == "BALL")
        {
            atBat.incrementBalls();
        }

        else if (playPitch.toString() == "FOUL")
        {
            if (atBat.getStrikeCount() < 2)
            {
                return;
            }

            else
                atBat.incrementStrikes();
        }

        else if (playPitch.toString() == "FOULPOOPEDOUT")
        {
       //     atBat.halfInning.incrementOuts();
        }

        else if (playPitch.toString() == "HIT")
        {
            Base homeBase = new Base(0);
            atBat.basePath.advanceRunner(homeBase, baseHitterWentTo);
        }
    }

    public Play(Player batter, Player pitcher, Pitch playPitch, AtBat atBat, Base baseHitterWentTo)
    {
        this.batter = batter;
        this.pitcher = pitcher;
        this.playPitch = playPitch;
        this.atBat = atBat;
        this.baseHitterWentTo = baseHitterWentTo;
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
    private Base baseHitterWentTo;
    private AtBat atBat;

}
