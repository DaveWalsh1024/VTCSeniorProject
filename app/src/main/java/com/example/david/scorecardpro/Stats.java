package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 11/26/2017.
 */

public class Stats
{
    public int getStrikeouts () { return strikeouts; }
    public int getHomeruns () { return homeruns; }
    public int getWalks () { return walks; }
    public int getTimesAtBat () { return timesAtBat; }
    public Season getSeason () { return season; }
    public Player getPlayer () { return player; }
    public Team getTeam () { return team; }

    public void incrementStrikeouts ()
    {
        strikeouts++;
        repOk();
    }

    public void incrementHomeruns ()
    {
        homeruns++;
        repOk();
    }

    public void incremementWalks ()
    {
        walks++;
        repOk();
    }

    public void incrementTimesAtBat ()
    {
        timesAtBat++;
        repOk();
    }

    public float getStrikeoutPercentage ()
    {
        float percentage = (strikeouts * 100.0f) / timesAtBat;
        return percentage;
    }

    public float getWalkPercentage ()
    {
        float percentage = (walks * 100.0f) / timesAtBat;
        return percentage;
    }

    public float getHomerunPercentage ()
    {
        float percentage = (homeruns * 100.0f) / timesAtBat;
        return percentage;
    }

    public void repOk()
    {
        assert strikeouts > -1;
        assert homeruns > -1;
        assert walks > -1;
        assert timesAtBat > -1;
    }

    public Stats (Season season, Player player, Team team)
    {
        this.season = season;
        this.player = player;
        this.team = team;
        repOk();
    }

    private int strikeouts;
    private int homeruns;
    private int walks;
    private int timesAtBat;
    private Season season; //optional
    private Team team; //optional
    private Player player; //optional

}
