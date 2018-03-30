package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 12/31/2017.
 */

public class TeamInGame
{
    public Team getTeam () { return team; }

    public void setBattingOrder (ArrayList<Player> newBattingOrder)
    {
        battingOrder = newBattingOrder;
    }

    public void setPositions (ArrayList<PositionsInGame> newPositions)
    {
        positions = newPositions;
    }

    public TeamInGame (Team team)
    {
        this.team = team;
        this.battingOrder = new ArrayList<>();
        this.positions = new ArrayList<>();
        repOk();
    }


    public ArrayList<Player> getBattingOrder ()
    {
        return battingOrder;
    }

    public void repOk()
    {
        assert team != null;
    }

    private Team team;
    private ArrayList <Player> battingOrder;
    private ArrayList <PositionsInGame> positions;
}
