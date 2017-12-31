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
    public TeamInGame getHomeTeam() {
        return homeTeam;
    }

    public TeamInGame getAwayTeam() {
        return awayTeam;
    }

    public void setHomeTeam (Team homeTeam)
    {
        homeTeam = homeTeam;
    }

    public void setAwayTeam (Team awayTeam)
    {
        awayTeam = awayTeam;
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

    public TeamInGame getGameWinner()
    {
        if (homeTeamScore > awayTeamScore)
        {
            return homeTeam;
        }
        else
            return awayTeam;
    }

    public TeamInGame getGameLooser ()
    {
        if (awayTeamScore > homeTeamScore )
        {
            return awayTeam;
        }
        else
            return homeTeam;
    }

    public void addInning (Inning inning)
    {
        innings.add(innings.size(), inning);
    }

    public Inning getInningFromNumber (int number)
    {
        return innings.get(number - 1);
    }


    public void repOk() {
        assert homeTeam != null;
        assert awayTeam != null;
        assert homeTeamScore > -1;
        assert awayTeamScore > -1;
        assert innings != null;
    }

    public Game(TeamInGame homeTeam, TeamInGame awayTeam, GameType gameType)
    {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameType = gameType;
        this.innings = new ArrayList<>();
        repOk();
    }

    private GameType gameType;
    private TeamInGame homeTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private TeamInGame awayTeam;
    private Date gameDate;
    private String location;
    private ArrayList <Inning> innings;
}
