package com.example.david.scorecardpro;

import android.util.Log;

/**
 * Created by david on 12/31/2017.
 */

public class PositionsInGame
{
    public Player getPlayer () {return player; }
    public Positions getPosition () {return position; }
    public void setPlayer (Player newPlayer) { player = newPlayer; }



    public PositionsInGame (Player player, Positions position /*double topCoordinate, double rightCoordinate, double bottomCoordinate, double leftCoordinate*/)
    {
        this.player = player;
        this.position = position;
        /*this.topCoordinate = topCoordinate;
        this.rightCoordinate = rightCoordinate;
        this.bottomCoordinate = bottomCoordinate;
        this.leftCoordinate = leftCoordinate;*/
    }

    private Player player;
    private Positions position;


}
