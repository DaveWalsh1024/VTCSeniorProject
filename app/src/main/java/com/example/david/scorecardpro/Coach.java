package com.example.david.scorecardpro;

/**
 * Created by david on 10/3/2017.
 */

public class Coach {

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getTitle() {
        return title;
    }


    public void repOk() {
        assert fName != null;
        assert lName != null;
    }

    public Coach(String fName, String lName, String title) {
        this.fName = fName;
        this.lName = lName;
        this.title = title;
        repOk();
    }

    private String fName;
    private String lName;
    private String title; //head coach, assistant coach, batting coach, etc...
}
