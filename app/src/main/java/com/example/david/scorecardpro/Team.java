package com.example.david.scorecardpro;

import java.util.ArrayList;

/**
 * Created by david on 10/3/2017.
 */

public class Team
{
    public ArrayList getPlayers ()
    {
        return players;
    }

    public ArrayList getCoaches ()
    {
        return coaches;
    }

    public String getTeamName ()
    {
        return name;
    }

    public int getSizeofPlayers ()
    {
        return players.size();
    }

    public Player getPlayerFromNumber (int i)
    {
        return players.get(i);
    }


    public Season getCurrentSeason () {return currentSeason; }

    public void addPlayerToTeam (Player player)
    {
        players.add(player);
        repOk();
    }

    public void removePlayerFromTeam (Player player)
    {
        players.remove(player);
        repOk();
    }

    public void updateTeamName (String newTeamName)
    {
        name = newTeamName;
        repOk();
    }

    public void repOk()
    {
        assert players != null;
        assert coaches != null;
        assert name != null;
        assert currentSeason != null;
        assert disabledPlayerList != null;
    }

    public Team (String name)
    {
        this.players = new ArrayList<>();
        this.coaches = new ArrayList<>();
        this.disabledPlayerList = new ArrayList<>();
        this.name = name;
        repOk();
    }

    private ArrayList <Player> players;
    private ArrayList <Coach> coaches;
    private ArrayList <Player> disabledPlayerList;
    private String name;
    private Season currentSeason;
}
