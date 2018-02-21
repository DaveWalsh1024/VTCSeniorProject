package com.example.david.scorecardpro;

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

        int i;
        int j;
        for (i=0; i<= 9; i++)
        {
            tr = new TableRow(this);
            ScorecardBox scorecardBox;
            TableRow.LayoutParams linearLayoutParams;
            tl.addView(tr);

            TextView tv = new TextView(this);
            tv.setText("PLAYER");
            tv.setTextSize(16);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tr.addView(tv);

            for (j = 0; j <= 8; j ++)
            {
                scorecardBox = new ScorecardBox(this);
                linearLayoutParams = new TableRow.LayoutParams(100,100);
                tr.addView(scorecardBox, linearLayoutParams);
                scorecardBoxes.add(scorecardBox);
                scorecardBox.drawBaseLine(0,2);
            }
        }

        Log.i("Box" , "created" + scorecardBoxes.size() + "boxes");
    }
}
