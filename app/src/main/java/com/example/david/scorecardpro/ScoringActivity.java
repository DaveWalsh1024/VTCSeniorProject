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

    int inningCount = 1;
    Inning currentInning = null;
    Team homeTeam = null;
    Team awayTeam = null;
    HalfInning currentTopInning = null;
    HalfInning currentBottomInning = null;

    public void startGame (View b)
    {
        TextView homeTeamText = (TextView)findViewById(R.id.homeTeam_Text);
        TextView awayTeamText = (TextView)findViewById(R.id.awayTeam_Text);

        homeTeam = new Team(homeTeamText.getText().toString());
        awayTeam = new Team(awayTeamText.getText().toString());

        Game game = new Game(homeTeam, awayTeam);

        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

        TextView homeTeamTitle = (TextView)findViewById(R.id.homeScore_View);
        TextView awayTeamTitle = (TextView)findViewById(R.id.awayScore_View);

        homeTeamTitle.setText(homeTeamText.getText());
        awayTeamTitle.setText(awayTeamText.getText());

        startInning(game);
    }

    public void startInning (Game game)
    {
       currentInning = game.getInningFromNumber(inningCount);

       currentTopInning = currentInning.getTopInning();
       currentTopInning.setTopOrBottom(1);
       currentTopInning.setBattingTeam(awayTeam);
       currentTopInning.setPitchingTeam(homeTeam);

       startHalfInning(currentTopInning);

       currentBottomInning = currentInning.getBottomInning();
       currentBottomInning.setTopOrBottom(2);
       currentBottomInning.setBattingTeam(homeTeam);
       currentBottomInning.setPitchingTeam(awayTeam);
    }

    public void startHalfInning (HalfInning halfInning)
    {
        
    }


}
