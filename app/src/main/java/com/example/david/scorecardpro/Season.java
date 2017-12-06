package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Season
{
    public int getWins () { return wins; }
    public int getLosses () { return losses; }

    public void wonGame (Season season)
    {
        season.wins++;
        repOk();
    }

    public void lostGame (Season season)
    {
        season.losses++;
        repOk();
    }

    public void resetSeasonRecord (Season season)
    {
        season.wins = 0;
        season.losses = 0;
        repOk();
    }

    public Season ()
    {
        this.games = new ArrayList<>();
        repOk();
    }

    public void repOk()
    {
        assert wins > -1;
        assert losses > -1;
        this.games = new ArrayList<>();
    }

    private int wins;
    private int losses;
    private ArrayList<Game> games;
}
