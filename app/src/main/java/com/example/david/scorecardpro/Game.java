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
    public static Game getById (int id) { return mostRecentGame; }

    public void setHomeTeam (Team homeTeam)
    {
        homeTeam = homeTeam;
    }

    public void setAwayTeam (Team awayTeam)
    {
        awayTeam = awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void incrementHomeTeamScore () { homeTeamScore++; }

    public void incrementAwayTeamScore () { awayTeamScore++; }

    public GameType getGameType () { return gameType; }

    public TeamInGame getGameWinner()
    {
        if (homeTeamScore > awayTeamScore)
        {
            return homeTeam;
        }
        else
            return awayTeam;
    }

    public TeamInGame getGameLoser ()
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

    public void addPlay (Play playToAdd)
    {
        plays.add(playToAdd);
    }

    public ArrayList<Play> getPlays () { return plays; }

    public TeamInGame getHomeTeam () { return homeTeam; }

    public TeamInGame getAwayTeam () { return awayTeam; }

    public int topOrBottom () { return topOrBottom; }

    public void setCurrentlyTopOfInning ()
    {
        topOrBottom = 1;
    }

    public void setCurrentlyBottomOfInning ()
    {
        topOrBottom = 2;
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
        topOrBottom = 1;
        this.innings = new ArrayList<>();
        mostRecentGame = this;
        this.plays= new ArrayList<>();
        repOk();
    }

    public ArrayList<Inning> getInnings () { return innings; }

    private GameType gameType;
    private TeamInGame homeTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private TeamInGame awayTeam;
    private static Game mostRecentGame;
    private ArrayList <Inning> innings;
    private int topOrBottom;
    private ArrayList <Play> plays;;
}
