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
    public static final String PATH_REVIEW = "review";
    public static final String PATH_TRAILER = "trailer";

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
        public static final String COLUMN_STATUS = "status";

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

    public static class ReviewEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "review";

        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_MOVIE_ID = "movie_id";

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

        public static Uri buildUriWithReviewId(String reviewId){
            return CONTENT_URI.buildUpon()
                    .appendPath(reviewId).build();
        }

        public static String getReviewIdFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }

    public static class TrailerEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String TABLE_NAME = "trailer";

        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_MOVIE_ID = "movie_id";

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

        public static Uri buildUriWithTrailerId(String trailerId){
            return CONTENT_URI.buildUpon()
                    .appendPath(trailerId).build();
        }
        public static String getTrailerIdFromUri(Uri uri){
            return uri.getPathSegments().get(1);
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
            MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieEntry.COLUMN_STATUS
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
    public static final int COL_STATUS = 9;

    public static final String[] REVIEW_PROJECTION = {
            ReviewEntry._ID,
            ReviewEntry.COLUMN_REVIEW_ID,
            ReviewEntry.COLUMN_AUTHOR,
            ReviewEntry.COLUMN_CONTENT,
            ReviewEntry.COLUMN_URL,
            ReviewEntry.COLUMN_MOVIE_ID
    };

    public static final int REVIEW_COL_ID = 0;
    public static final int REVIEW_COL_REVIEW_ID = 1;
    public static final int REVIEW_COL_AUTHOR = 2;
    public static final int REVIEW_COL_CONTENT = 3;
    public static final int REVIEW_COL_URL = 4;
    public static final int REVIEW_COL_MOVIE_ID = 5;

    public static final String[] TRAILER_PROJECTION = {
            TrailerEntry._ID,
            TrailerEntry.COLUMN_TRAILER_ID,
            TrailerEntry.COLUMN_KEY,
            TrailerEntry.COLUMN_NAME,
            TrailerEntry.COLUMN_SITE,
            TrailerEntry.COLUMN_SIZE,
            TrailerEntry.COLUMN_MOVIE_ID
    };

    public static final int TRAILER_COL_ID = 0;
    public static final int TRAILER_COL_TRAILER_ID = 1;
    public static final int TRAILER_COL_KEY = 2;
    public static final int TRAILER_COL_NAME = 3;
    public static final int TRAILER_COL_SITE = 4;
    public static final int TRAILER_COL_SIZE = 5;
    public static final int TRAILER_COL_MOVIE_ID = 6;

    public static final String SORT_BY_VOTE = MovieEntry.COLUMN_VOTE_AVERAGE + " DESC";
    public static final String SORT_BY_POPULARITY = MovieEntry.COLUMN_POPULARITY + " DESC";

    //电影收藏状态
    public static final int STATUS_COLLECTED = 1;
    public static final int STATUS_NOT_COLLECTED = 0;
}
