package com.example.david.scorecardpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeTeamScorecardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorecard);

        Intent arrived = getIntent();
        if (arrived.hasExtra("Game Id"))
        {

        }

        game = Game.getById(1); //replace this with actual game id

        initializeStuff();
        fillTable();
    }

    LinearLayout scorecardBoxLinearLayout;

    public void initializeStuff ()
    {
        scorecardBoxLinearLayout = (LinearLayout)findViewById(R.id.scorecard);
    }

    ArrayList<ScorecardBox> scorecardBoxes = new ArrayList<>();

    public void fillTable()
    {
        TableRow tr;

        TableLayout tl = (TableLayout)findViewById(R.id.scorecardTable);

        int totalBatterCountHomeTeam = 0;

        for (int l = 0; l < game.getInnings().size(); l++)
        {
            System.out.println("==========================");
            totalBatterCountHomeTeam = totalBatterCountHomeTeam + game.getInnings().get(l).getTopInning().getBatters().size();
        }

        int batter;
        int inning;
        for (batter=0; batter<= 8; batter++)
        {
            //batter is outer
            tr = new TableRow(this);
            ScorecardBox scorecardBox;
            TableRow.LayoutParams linearLayoutParams;
            tl.addView(tr);

            TextView tv = new TextView(this);
            tv.setText(game.getHomeTeam().getBattingOrder().get(batter).getLastNameWithNumber());

            tv.setTextSize(16);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(tv);
            System.out.println("The scorecard was created");

            for (inning = 0; inning <= 10; inning++) {
                //inning is inner
                scorecardBox = new ScorecardBox(this, inning, batter);
                linearLayoutParams = new TableRow.LayoutParams(100, 100);
                tr.addView(scorecardBox, linearLayoutParams);

                System.out.println("Game plays array size is " + game.getPlays().size());
                for (int k = 0; k < game.getPlays().size(); k++) {
                    System.out.println("Top or bottom = " + game.getPlays().get(k).getTopOrBottom());
                    if (game.getPlays().get(k).getTopOrBottom() == 2)
                    {
                        for (RunnerEvent re : game.getPlays().get(k).getRunnerEvents())
                        {
                            String playPitch = game.getPlays().get(k).getPlayPitch().toString();

                            if (playPitch.equals("BALL"))
                            {
                                scorecardBox.setBall(game.getPlays().get(k).getAtBat().getBallCount());
                                System.out.println("Ball is being marked the ball count is " + game.getPlays().get(k).getAtBat().getBallCount());
                            }

                            if (playPitch.equals("STRIKE"))
                            {
                                scorecardBox.setStrike(game.getPlays().get(k).getAtBat().getStrikeCount());
                                System.out.println("Strike is being marked the strike count is " + game.getPlays().get(k).getAtBat().getStrikeCount());
                            }

                            re.updateScorecardBox(scorecardBox);
                        }
                    }
                    scorecardBoxes.add(scorecardBox);
                }
            }
        }

        Log.i("Box" , "created" + scorecardBoxes.size() + "boxes");
    }

    int gameId = 1;
    Game game;
}
