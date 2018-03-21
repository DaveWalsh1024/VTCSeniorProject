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

public class AwayTeamScorecardActivity extends AppCompatActivity {

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

    public void fillTable() {
        TableRow tr;

        TableLayout tl = (TableLayout) findViewById(R.id.scorecardTable);

        int totalBatterCountAwayTeam = 0;

        for (int l = 0; l < game.getInnings().size(); l++) {
            totalBatterCountAwayTeam = totalBatterCountAwayTeam + game.getInnings().get(l).getTopInning().getBatters().size();
        }

        int batter;
        int inning;
        for (batter = 1; batter <= 9; batter++) {
            //batter is outer
            tr = new TableRow(this);
            ScorecardBox scorecardBox;
            TableRow.LayoutParams linearLayoutParams;
            tl.addView(tr);

            TextView tv = new TextView(this);
  //          tv.setText(game.getAwayTeamInGame().getBattingOrder().get(batter - 1).getLastName());
            tv.setText(game.getAwayTeamBattingOrder().get(batter-1).getLastName());

            tv.setTextSize(16);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(tv);

            for (inning = 1; inning <= 11; inning++) {
                //inning is inner
                scorecardBox = new ScorecardBox(this, inning, batter);
                linearLayoutParams = new TableRow.LayoutParams(100, 100);
                tr.addView(scorecardBox, linearLayoutParams);
                scorecardBoxes.add(scorecardBox);
            }
        }

        System.out.println("Game plays array size is " + game.getPlays().size());
        for (int k = 0; k < game.getPlays().size(); k++)
        {
            System.out.println("Top or bottom = " + game.getPlays().get(k).getTopOrBottom());
            if (game.getPlays().get(k).getTopOrBottom() == 1)
            {
                ScorecardBox newScorecardBox = findScoreCardBox(game.getPlays().get(k).getInningNumber(), game.getPlays().get(k).getLineupNumber() + 1);

                System.out.println("NewScoreCardBox = " + newScorecardBox);
                System.out.println("Inning number = " + game.getPlays().get(k).getInningNumber());
                System.out.println("Lineup number = " + game.getPlays().get(k).getLineupNumber());

                String playPitch = game.getPlays().get(k).getPlayPitch().toString();

                if (playPitch.equals("BALL")) {
                    System.out.println("Ball is being marked the ball count is " + game.getPlays().get(k).getAtBat().getBallCount());
                    newScorecardBox.setBall(game.getPlays().get(k).getAtBat().getBallCount());
                }

                if (playPitch.equals("STRIKE")) {
                    System.out.println("Strike is being marked the strike count is " + game.getPlays().get(k).getAtBat().getStrikeCount());
                    newScorecardBox.setStrike(game.getPlays().get(k).getAtBat().getStrikeCount());
                }

                if (game.getPlays().get(k).getPlayText() != null)
                {
                    if (game.getPlays().get(k).getOut())
                    {
                        newScorecardBox.centerText(game.getPlays().get(k).getPlayText());
                    }

                    else
                    {
                        newScorecardBox.bottomRightText(game.getPlays().get(k).getPlayText());
                    }

                }

                for (RunnerEvent re : game.getPlays().get(k).getRunnerEvents())
                {
                    re.updateScorecardBox(findScoreCardBox(re.getCurrentInning(), re.getRunnerBattingOrderPosiiton()));
                }
            }
        }

        Log.i("Box" , "created" + scorecardBoxes.size() + "boxes");
    }

    public ScorecardBox findScoreCardBox (int inning, int battingOrder)
    {
        for (ScorecardBox scorecardBox : scorecardBoxes)
        {
            if (scorecardBox.getInning() == inning && scorecardBox.getBattingOrder() == battingOrder)
            {
                return scorecardBox;
            }
        }

        return null;
    }

    int gameId = 1;
    Game game;
}
