package com.example.david.scorecardpro;

/**
 * Created by david on 12/31/2017.
 */

public class PositionsInGame
{
    public Player getPlayer () {return player; }
    public Positions getPosition () {return position; }
    public void setPlayer (Player newPlayer)
    {
        player = newPlayer;
    }

    public PositionsInGame (Player player, Positions position)
    {
        this.player = player;
        this.position = position;
    }

    private Player player;
    private Positions position;
}
