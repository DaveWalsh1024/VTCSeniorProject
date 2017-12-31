package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 12/31/2017.
 */

public class TeamInGame
{
    public Team getTeam () { return team; }

    public TeamInGame (Team team)
    {
        this.team = team;
        repOk();
    }

    public void repOk()
    {
        assert team != null;
    }

    private Team team;
    private ArrayList <Player> battingOrder;
    private ArrayList <PositionsInGame> positions;
}
