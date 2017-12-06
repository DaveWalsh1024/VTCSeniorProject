package com.example.david.scorecardpro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by david on 10/3/2017.
 */

public class Game extends AppCompatActivity
{
    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public String getLocation() {
        return location;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public Team getGameWinner()
    {
        if (homeTeamScore > awayTeamScore)
        {
            return homeTeam;
        }
        else
            return awayTeam;
    }

    public Team getGameLooser ()
    {
        if (awayTeamScore > homeTeamScore )
        {
            return awayTeam;
        }
        else
            return homeTeam;
    }


    public void repOk() {
        assert homeTeam != null;
        assert awayTeam != null;
        assert homeTeamScore > -1;
        assert awayTeamScore > -1;
        assert innings != null;
    }

    public Game(Team homeTeam, Team awayTeam)
    {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.innings = new ArrayList<>();
        repOk();
    }

    private Team homeTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private Team awayTeam;
    private Date gameDate;
    private String location;
    private ArrayList <Inning> innings;
}
