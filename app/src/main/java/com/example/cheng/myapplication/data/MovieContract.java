package com.example.cheng.myapplication.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cheng on 2017/1/4.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY="com.example.cheng.myapplication";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static Uri buildUriWithMovieId(long movieId){
           return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(movieId)).build();
        }
        public static long getMovieIdFromUri(Uri uri){
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }
    public static final String[] MOVIE_PROJECTION ={
            MovieEntry._ID,
            MovieEntry.COLUMN_POSTER_PATH,
            MovieEntry.COLUMN_ADULT,
            MovieEntry.COLUMN_OVERVIEW,
            MovieEntry.COLUMN_RELEASE_DATE,
            MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_TITLE,
            MovieEntry.COLUMN_POPULARITY,
            MovieEntry.COLUMN_VOTE_AVERAGE
    };

    public static final int COL_ID = 0;
    public static final int COL_POSTER_PATH = 1;
    public static final int COL_ADULT =2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_MOVIE_ID = 5;
    public static final int COL_TITLE = 6;
    public static final int COL_POPULARITY = 7;
    public static final int COL_VOTE_AVERAGE = 8;

    public static final String SORT_BY_VOTE = MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
    public static final String SORT_BY_POPULARITY = MovieEntry.COLUMN_POPULARITY + " DESC";
}
