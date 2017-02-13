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

        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE "+ MovieContract.ReviewEntry.TABLE_NAME+" ("+
                MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY, "+
                MovieContract.ReviewEntry.COLUMN_REVIEW_ID + " TEXT UNIQUE NOT NULL, "+
                MovieContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "+
                MovieContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, "+
                MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL );";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE "+ MovieContract.TrailerEntry.TABLE_NAME+" ("+
                MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY, "+
                MovieContract.TrailerEntry.COLUMN_TRAILER_ID + " TEXT UNIQUE NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_SITE + " TEXT NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_SIZE + " TEXT NOT NULL, "+
                MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL);";


        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.ReviewEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieContract.TrailerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
