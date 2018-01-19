package com.example.david.scorecardpro;

/**
 * Created by david on 1/19/2018.
 */

public class Field
{
    public void updatePosition (PositionsInGame position, Player newPlayer)
    {
        position.setPlayer(newPlayer);
    }

    public Field (PositionsInGame firstBasemen, PositionsInGame secondBasemen, PositionsInGame thirdBasemen, PositionsInGame shortStop, PositionsInGame centerField, PositionsInGame leftField, PositionsInGame rightField, PositionsInGame catcher, PositionsInGame pitcher)
    {
        this.firstBasemen = firstBasemen;
        this.secondBasemen = secondBasemen;
        this.thirdBasemen = thirdBasemen;
        this.shortStop = shortStop;
        this.centerField = centerField;
        this.leftField = leftField;
        this.rightField = rightField;
        this.catcher = catcher;
        this.pitcher = pitcher;
        repOk();
    }

    public void repOk ()
    {
        assert firstBasemen != null;
        assert secondBasemen != null;
        assert thirdBasemen != null;
        assert shortStop != null;
        assert centerField != null;
        assert leftField != null;
        assert rightField != null;
        assert catcher != null;
        assert pitcher != null;
    }

    private PositionsInGame firstBasemen;
    private PositionsInGame secondBasemen;
    private PositionsInGame thirdBasemen;
    private PositionsInGame shortStop;
    private PositionsInGame centerField;
    private PositionsInGame leftField;
    private PositionsInGame rightField;
    private PositionsInGame catcher;
    private PositionsInGame pitcher;
}
