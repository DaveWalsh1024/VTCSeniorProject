package com.example.david.scorecardpro;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        awayTeam = (EditText) findViewById(R.id.awayTeamEditText);
        homeTeam = (EditText) findViewById(R.id.homeTeamEditText);
        littleLeague = (RadioButton) findViewById(R.id.littleLeague);
        mlb = (RadioButton) findViewById(R.id.mlb);
        gameTypeGroup = (RadioGroup) findViewById(R.id.gameTypeGroup);

        gameTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.littleLeague) {
                    littleLeague.setBackgroundColor(Color.argb(255, 170,135,57));
                    littleLeague.setTextColor(Color.WHITE);
                    mlb.setBackgroundColor(Color.WHITE);
                    mlb.setTextColor(Color.BLACK);

                    inningNum = 6;
                    gameTypeName = "Little League";
                }
                else if (i == R.id.mlb) {
                    mlb.setBackgroundColor(Color.argb(255, 170,135,57));
                    mlb.setTextColor(Color.WHITE);
                    littleLeague.setBackgroundColor(Color.WHITE);
                    littleLeague.setTextColor(Color.BLACK);

                    inningNum = 9;
                    gameTypeName = "MLB";
                }

                else{
                    littleLeague.setBackgroundColor(Color.WHITE);
                    mlb.setBackgroundColor(Color.WHITE);
                    littleLeague.setTextColor(Color.BLACK);
                    mlb.setTextColor(Color.BLACK);

                    inningNum = 9;
                    gameTypeName = "MLB";
                }
            }
        });


    }

    public void startGame (View b)
    {
        Intent i = new Intent(this, RosterActivity.class);

        awayTeamName = awayTeam.getText().toString();
        homeTeamName = homeTeam.getText().toString();

        /*Log.i("StartupActivity", "" + awayTeamName);
        Log.i("StartupActivity", "" + homeTeamName);*/


        i.putExtra("Away Team", awayTeamName);
        i.putExtra("Home Team", homeTeamName);
        i.putExtra("Number of Innings", inningNum);
        i.putExtra("Game Type", gameTypeName);

        startActivity(i);
    }

    RadioGroup gameTypeGroup;
    RadioButton littleLeague;
    RadioButton mlb;

    EditText awayTeam;
    EditText homeTeam;
    String awayTeamName;
    String homeTeamName;

    int inningNum;
    String gameTypeName;
}
