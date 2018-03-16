package com.example.david.scorecardpro;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jacklavallee on 2/27/18.
 */

public class CreatePlayer extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team);

        Button addPlayer = (Button) findViewById(R.id.addPlayerButton);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePlayer.this);
                View mView = getLayoutInflater().inflate(R.layout.add_player, null);
                final Button addPlayer = (Button) findViewById(R.id.addPlayerButton);
                final EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
                final EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
                final EditText number = (EditText) findViewById(R.id.numberEditText);
                final Button submit = (Button) findViewById(R.id.addPlayerSubmit);

                final TableLayout tableLayout = (TableLayout) findViewById(R.id.playerList_Table);
                final TableRow tableRow = new TableRow(CreatePlayer.this);
                final TextView firstNameRow = new TextView(CreatePlayer.this);
                final TextView lastNameRow = new TextView(CreatePlayer.this);
                final TextView numberRow = new TextView(CreatePlayer.this);

                tableRow.layout(5,5,5,5);

                firstNameRow.setTextSize(32);
                lastNameRow.setTextSize(32);
                numberRow.setTextSize(32);


                addPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !number.getText().toString().isEmpty()) {
                            Toast.makeText(CreatePlayer.this,
                                    "Player Added!",
                                    Toast.LENGTH_SHORT).show();

                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                                            TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
                                    ));

                                    firstNameRow.setText(firstName.getText().toString());
                                    tableRow.addView(firstNameRow);

                                    lastNameRow.setText(lastName.getText().toString());
                                    tableRow.addView(lastNameRow);

                                    numberRow.setText(number.getText().toString());
                                    tableRow.addView(numberRow);
                                }
                            });
                        }
                        else {
                            Toast.makeText(CreatePlayer.this,
                                    "Player Addition Unsuccessful",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
}
