package com.example.david.scorecardpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by jrussell on 3/3/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //Table Identifiers
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SCP";

    //Table Column Information
    private static final String KEY_BATTER = "Batter";
    private static final String KEY_PITCHER = "Pitcher";
    private static final String KEY_PLAY_PITCH = "PlayPitch";
    private static final String KEY_INNING = "Inning";
    private static final String KEY_ID = "ID";
    private static final String KEY_STRIKES = "Strikes";
    private static final String KEY_BALLS = "Balls";
    private static final String KEY_OUTS = "Outs";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        createTable = "CREATE TABLE " + DATABASE_NAME + "( " + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_BATTER + " TEXT, " + KEY_PITCHER  +" TEXT, " +
        KEY_PLAY_PITCH + " TEXT, " + KEY_STRIKES + " INTEGER, " + KEY_BALLS + " INTEGER, " + KEY_OUTS + " INTEGER, " + KEY_INNING + " INTEGER " + ")";
        db.execSQL(createTable);
        Log.d("Table:", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB Upgrade", "Upgrade Message Called");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void toDB(Play newPlay) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            Log.d("Insert: ", "Inserting...");

            cv.put("Batter", newPlay.getBatter().getFullName());
            cv.put("Inning", newPlay.getInningNumber());
    //        cv.put("Balls", AtBat.getBallCount());
            cv.put("Pitcher", newPlay.getPitcher().getFullName());
            cv.put("PlayPitch", String.valueOf(newPlay.getPlayPitch()));
     //       cv.put("Strikes", AtBat.getStrikeCount());

            db.insert(DATABASE_NAME, null, cv);
        } catch (Exception ex) {
            Log.e("Error Inserting:", ex.toString());
        }
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteTable = "DELETE FROM " + DATABASE_NAME;
        db.execSQL(deleteTable);
    }


    public List<Play> getGame() {
        List<Play> playList = new ArrayList<Play>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        ArrayList<String> plays = new ArrayList<String>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            plays.add(cursor.getString(0));
        }

        // return game
        return playList;

    }

    public Integer deletePlay(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_NAME, "ID = ?",new String[] {id});
    }


}
