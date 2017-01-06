package com.example.cheng.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cheng on 2017/1/4.
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    static final String DATEBASE_NAME = "movie.db";
    private static final int DATEBASE_VERSION = 1;


    public MovieDbHelper(Context context){
        super(context,DATEBASE_NAME,null,DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE ="CREATE TABLE "+ MovieContract.MovieEntry.TABLE_NAME + " ("+
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, "+
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_ADULT + " INTEGER NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, "+
                MovieContract.MovieEntry.COLUMN_STATUS + " INTEGER DEFAULT "+
                MovieContract.STATUS_NOT_COLLECTED+
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
