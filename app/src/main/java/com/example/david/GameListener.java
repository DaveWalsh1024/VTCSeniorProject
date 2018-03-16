package com.example.david;

/**
 * Created by david on 3/16/2018.
 */

public interface GameListener
{
    public void strikeCalled (int n);

    public void ballCalled (int n);

    public void outOccured (int n);

    public void homeRunsScored (int n);

    public void awayRunsScored (int n);

    public void atBatEnded ();

    public void inningEnded ();

    public void gameEnded ();
}
