package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

public class Play
{

    public Player getBatter () { return batter; }

    public Player getPitcher () { return  pitcher; }

    public Pitch getPlayPitch () { return playPitch; }

    public String getPlayText () { return playText; }

    public AtBat getAtBat () { return atBat; }

    public Play(Player batter, Player pitcher, Pitch playPitch, String playText, AtBat atBat, int playId)
    {
        this.batter = batter;
        this.pitcher = pitcher;
        this.playPitch = playPitch;
        this.playText = playText;
        this.atBat = atBat;
        this.playId = playId;
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
    private String playText;
    private AtBat atBat;
    private int playId;

}
