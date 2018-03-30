package com.example.david.scorecardpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }
    public void startGame (View b)
    {
        Intent i = new Intent(this, RosterActivity.class);

        String homeTeamName = "Home Team123";
        String awayTeamName = "Away Team123";

        i.putExtra("Home Team", homeTeamName);
        i.putExtra("Away Team", awayTeamName);

        startActivity(i);
    }

}
