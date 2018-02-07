package com.example.david.scorecardpro;

import android.arch.persistence.room.RoomDatabase;

/**
 * Created by david on 2/7/2018.
 */

public class Database
{
    @Database(entities = {Play.class}, version = 1)
    public abstract class MyDatabase extends RoomDatabase {
        public abstract instertPlayDao instertPlayDao ();
    }
}
