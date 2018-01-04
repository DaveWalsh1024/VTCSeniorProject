package com.example.david.scorecardpro;

import java.text.FieldPosition;

/**
 * Created by david on 10/3/2017.
 */

public class Player
{
    public String getFullName ()
    {
        String fullName = new StringBuilder().append(fName).append(lName).toString();
        return fullName;
    }

    public void repOk()
    {
        assert number > -1 && number < 100;
        assert age > -1 && age < 120;
    }

    public Player(String fName, String lName, int number, int age)
    {
        this.fName = fName;
        this.lName = lName;
        this.number = number;
        this.age = age;
        repOk();
    }

    private String fName;
    private String lName;
    private int number;
    private int age;

}
