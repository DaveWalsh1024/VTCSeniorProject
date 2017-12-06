package com.example.david.scorecardpro;

import java.text.FieldPosition;

/**
 * Created by david on 10/3/2017.
 */

public class Player
{

    public String getFName()
    {
        return fName;
    }

    public String getLName ()
    {
        return lName;
    }

    public int getPosition ()
    {
        return position;
    }

    public int getNumber ()
    {
        return number;
    }

    public int getAge () { return age; }

    public void updateFName (String newfName)
    {
        fName = newfName;
        repOk();
    }

    public void updateLName (String newlName)
    {
        lName = newlName;
        repOk();
    }

    public void updatePosition (int newPosition)
    {
        position = newPosition;
        repOk();
    }

    public void updateAge (int newAge)
    {
        age = newAge;
        repOk();
    }

    public void updateNumber (Player player, int age)
    {
        player.number = number;
        repOk();
    }

    public void repOk()
    {
        assert number > -1 && number < 100;
        assert age > -1 && age < 120;
        assert position > 0 && position < 10;
    }

    public Player(String fName, String lName, int position, int number, int age)
    {
        this.fName = fName;
        this.lName = lName;
        this.position = position;
        this.number = number;
        this.age = age;
        repOk();
    }

    private String fName;
    private String lName;
    private int position;
    private int number;
    private int age;

}
