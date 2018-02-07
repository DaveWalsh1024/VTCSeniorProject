package com.example.david.scorecardpro;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;

import java.util.ArrayList;

/**
 * Created by david on 10/17/2017.
 */

@Entity
public class Play
{
    database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).build();

    public Player getBatter () { return batter; }

    public Player getPitcher () { return  pitcher; }

    public Pitch getPlayPitch () { return playPitch; }

    public AtBat getAtBat () { return atBat; }

    public Play(Player batter, Player pitcher, Pitch playPitch, String playText, AtBat atBat)
    {
        this.batter = batter;
        this.pitcher = pitcher;
        this.playPitch = playPitch;
        this.playText = playText;
        this.atBat = atBat;
        repOk();
    }

    public void repOk()
    {
        assert batter != null;
        assert pitcher != null;
        assert playPitch != null;
    }

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;

    @ColumnInfo(name = "batter")
    private Player batter;

    @ColumnInfo(name = "pitcher")
    private Player pitcher;

    @ColumnInfo(name = "pitch")
    private Pitch playPitch;

    @ColumnInfo(name = "playText")
    private String playText;

    @ColumnInfo(name = "atBat")
    private AtBat atBat;

}
