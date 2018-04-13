package com.example.david.scorecardpro;

import java.io.Serializable;
import java.text.FieldPosition;

/**
 * Created by david on 10/3/2017.
 */

public class Player implements Serializable
{
    public String getFullName ()
    {
        String fullName = new StringBuilder().append(fName).append(" ").append(lName).toString();
        return fullName;
    }

    public String getLastName ()
    {
        return lName;
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
        this.base = null;
        this.rv = null;
        repOk();
    }

    public void noLongerOnBase(Base base) {
        if (this.base != base) {
            return;
        }

        if (rv != null) {
            rv.setBase(null);
            rv = null;
        }

        this.base = null;
    }

    public void nowOnBase(Base newBase) {
        if (rv == null) {
            rv = new RunnerView(this);
        }
        rv.setBase(newBase);
        base = newBase;
    }

    public Base getCurrentBase() {
        return base;
    }

    public RunnerView getRv() {
        return rv;
    }

    public int getNumber() {
        return number;
    }

    public String toString() { return getFullName(); };

    private String fName;
    private String lName;
    private int number;
    private int age;
    private Base base;
    private RunnerView rv;

}
