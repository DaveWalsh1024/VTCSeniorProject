package com.example.david.scorecardpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jacklavallee on 11/28/17.
 */

public class ScoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent go;
        switch(item.getItemId())
        {
            case R.id.login:
                go = new Intent(this,LoginActivity.class);
                startActivity(go);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startGame (View b)
    {
        TextView homeTeamText = (TextView)findViewById(R.id.homeTeam_Text);
        TextView awayTeamText = (TextView)findViewById(R.id.awayTeam_Text);

        Team homeTeam = new Team(homeTeamText.getText().toString());
        Team awayTeam = new Team(awayTeamText.getText().toString());

        Game game = new Game(homeTeam, awayTeam);
    }
}
