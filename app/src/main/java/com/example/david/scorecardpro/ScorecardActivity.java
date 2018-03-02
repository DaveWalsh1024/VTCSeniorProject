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

public class ScorecardActivity extends AppCompatActivity {

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

        int totalBatterCountAwayTeam = 0;
  //      int totalBatterCountHomeTeam = 0;

        for (int l = 0; l < game.getInnings().size(); l++)
        {
            totalBatterCountAwayTeam = totalBatterCountAwayTeam + game.getInnings().get(l).getTopInning().getBatters().size();
  //          totalBatterCountHomeTeam = totalBatterCountHomeTeam + game.getInnings().get(l).getBottomInning().getBatters().size();
        }

        int i;
        int j;
        for (i=0; i<= 8; i++)
        {
            //batter is outer
            tr = new TableRow(this);
            ScorecardBox scorecardBox;
            TableRow.LayoutParams linearLayoutParams;
            tl.addView(tr);

            TextView tv = new TextView(this);

            for (int r = 0; r < 9; r ++)
            {
                System.out.println("Player being labeled on batting order " + game.getAwayTeam().getBattingOrder().get(r).getLastName());
                tv.setText(game.getAwayTeam().getBattingOrder().get(r).getLastName());
            }

            tv.setTextSize(16);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(tv);

            for (j = 0; j <= 8; j ++)
            {
                //inning is inner
                scorecardBox = new ScorecardBox(this);
                linearLayoutParams = new TableRow.LayoutParams(100,100);
                tr.addView(scorecardBox, linearLayoutParams);

                System.out.println("Total away team batter count = " + totalBatterCountAwayTeam);

                for (int k = 0; k < game.getPlays().size(); k ++)
                {
                    String playPitch = game.getPlays().get(k).getPlayPitch().toString();

                    if (playPitch.equals("STRIKE"))
                    {
                        if (i == game.getPlays().get(k).getLineupNumber() - 1 && j == game.getPlays().get(k).getInningNumber() - 1)
                        {
                            System.out.println("Current strike count = " + game.getPlays().get(k).getAtBat().getStrikeCount());
                            System.out.println("Total plays = " + game.getPlays().size());
                            System.out.println("Most recent play is " + game.getPlays().get(game.getPlays().size() - 1).getPlayPitch().toString());

                            if (game.getPlays().get(k).getAtBat().getStrikeCount() == 1)
                            {
                                System.out.println("Set 1 strike code has been called");
                                scorecardBox.setStrike1();
                            }

                            else if (game.getPlays().get(k).getAtBat().getStrikeCount() == 2)
                            {
                                System.out.println("Set 2 strike code has been called");
                                scorecardBox.setStrike1();
                                scorecardBox.setStrike2();
                            }

                            else
                            {
                                System.out.println("The print K code was called");
                                scorecardBox.setStrike1();
                                scorecardBox.setStrike2();
                                scorecardBox.centerText("K");
                            }
                        }
                    }

                    if (playPitch.equals("BALL"))
                    {
                        if (i == game.getPlays().get(k).getLineupNumber() - 1 && j == game.getPlays().get(k).getInningNumber() - 1)
                        {
                            System.out.println("Current ball count = " + game.getPlays().get(k).getAtBat().getBallCount());
                            if (game.getPlays().get(k).getAtBat().getBallCount() == 1)
                            {
                                scorecardBox.setBall1();
                            }

                            if (game.getPlays().get(k).getAtBat().getBallCount() == 2)
                            {
                                scorecardBox.setBall1();
                                scorecardBox.setBall2();
                            }

                            if (game.getPlays().get(k).getAtBat().getBallCount() == 3)
                            {
                                scorecardBox.setBall1();
                                scorecardBox.setBall2();
                                scorecardBox.setBall3();
                            }

                            else
                            {
                                scorecardBox.setBall1();
                                scorecardBox.setBall2();
                                scorecardBox.setBall3();
                                scorecardBox.drawBaseLine(0, 1);
                            }
                        }
                    }

                    if (playPitch.equals("HIT"))
                    {

                    }

                    if (playPitch.equals("FOUL"))
                    {

                    }

                    if (playPitch.equals("HITBYPITCH"))
                    {

                    }
                }
                scorecardBoxes.add(scorecardBox);
            }
        }

        Log.i("Box" , "created" + scorecardBoxes.size() + "boxes");
    }

    int gameId = 1;
    Game game;
}
