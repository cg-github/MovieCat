package com.example.cheng.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.cheng.myapplication.util.CommonUtil;

/**
 * Created by cheng on 2017/1/4.
 */

public class MovieProvider extends ContentProvider {

    private static final String LOG_TAG = MovieProvider.class.getSimpleName();
    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;
    static final int REVIEW = 102;
    static final int REVIEW_WITH_MOVIE_ID = 103;
    static final int REVIEW_WITH_REVIEW_ID = 104;
    static final int TRAILER = 105;
    static final int TRAILER_WITH_MOVIE_ID = 106;
    static final int TRAILER_WITH_TRAILER_ID = 107;

    MovieDbHelper mDbHelper;
    UriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        mUriMatcher = buildUriMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        long movieId = 0;
        String selection;

        switch (mUriMatcher.match(uri)){
            case MOVIE:
               return db.query(MovieContract.MovieEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1
                        );
            case MOVIE_WITH_ID:
                movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);
                Log.i("cheng","I am here movie with id"+movieId);
                selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                return db.query(MovieContract.MovieEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{Long.toString(movieId)},
                        null,
                        null,
                        null
                );
            case REVIEW:
                return db.query(MovieContract.ReviewEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
            case REVIEW_WITH_MOVIE_ID:
                movieId = MovieContract.ReviewEntry.getMovieIdFromUri(uri);
                Log.i("cheng","I am here movie with id"+movieId);
                selection = MovieContract.ReviewEntry.TABLE_NAME+"."+
                        MovieContract.ReviewEntry.COLUMN_MOVIE_ID+"=?";
                return db.query(MovieContract.ReviewEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{Long.toString(movieId)},
                        null,
                        null,
                        null
                );
            case REVIEW_WITH_REVIEW_ID:
                String reviewId = MovieContract.ReviewEntry.getReviewIdFromUri(uri);
                selection = MovieContract.ReviewEntry.TABLE_NAME+"."+
                        MovieContract.ReviewEntry.COLUMN_REVIEW_ID+"=?";
                return db.query(MovieContract.ReviewEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{reviewId},
                        null,
                        null,
                        null
                );
            case TRAILER:
                return db.query(MovieContract.TrailerEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
            case TRAILER_WITH_MOVIE_ID:
                movieId = MovieContract.TrailerEntry.getMovieIdFromUri(uri);
                Log.i("cheng","I am here movie with id"+movieId);
                selection = MovieContract.TrailerEntry.TABLE_NAME+"."+
                        MovieContract.TrailerEntry.COLUMN_MOVIE_ID+"=?";
                return db.query(MovieContract.TrailerEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{Long.toString(movieId)},
                        null,
                        null,
                        null
                );
            case TRAILER_WITH_TRAILER_ID:
                String trailerId = MovieContract.TrailerEntry.getTrailerIdFromUri(uri);
                Log.i("cheng","I am here movie with id"+movieId);
                selection = MovieContract.TrailerEntry.TABLE_NAME+"."+
                        MovieContract.TrailerEntry.COLUMN_TRAILER_ID+"=?";
                return db.query(MovieContract.TrailerEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{trailerId},
                        null,
                        null,
                        null
                );
            default:
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return MovieContract.ReviewEntry.CONTENT_TYPE;
            case REVIEW_WITH_MOVIE_ID:
            case REVIEW_WITH_REVIEW_ID:
                return MovieContract.ReviewEntry.CONTENT_ITEM_TYPE;
            case TRAILER:
                return MovieContract.TrailerEntry.CONTENT_TYPE;
            case TRAILER_WITH_MOVIE_ID:
            case TRAILER_WITH_TRAILER_ID:
                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri retUri = null ,queryUri = null,updateUri = null;
        Cursor cursor = null;
        long movieId = 0;
        String reviewId,trailerId,selection;
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                movieId = contentValues.getAsLong(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
                queryUri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
                cursor =query(queryUri,
                            MovieContract.MOVIE_PROJECTION,
                            null,
                            null,
                            null
                        );
                if (cursor!=null && cursor.moveToFirst() && (movieId == cursor.getLong(MovieContract.COL_MOVIE_ID))){
                    updateUri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
                    long _id = cursor.getLong(MovieContract.COL_ID);
                    update(updateUri,contentValues,null,null);
                    retUri = MovieContract.MovieEntry.buildMovieUri(_id);
                    cursor.close();
                }else {
                    long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME,null,contentValues);
                    if (_id>0)
                    {
                        retUri = MovieContract.MovieEntry.buildMovieUri(_id);
                    }else {
                        throw new SQLException("Failed to insert into row "+uri);
                    }
                    Log.i(LOG_TAG,"insert excute");
                }

                break;
            case REVIEW:
                reviewId = contentValues.getAsString(MovieContract.ReviewEntry.COLUMN_REVIEW_ID);
                queryUri = MovieContract.ReviewEntry.buildUriWithReviewId(reviewId);
                cursor=query(queryUri,
                        MovieContract.REVIEW_PROJECTION,
                        null,
                        null,
                        null);
                if (cursor!=null && cursor.moveToFirst() && (reviewId.equals(cursor.getString(MovieContract.REVIEW_COL_REVIEW_ID)))){
                    updateUri = MovieContract.ReviewEntry.buildUriWithReviewId(reviewId);
                    long _id = cursor.getLong(MovieContract.REVIEW_COL_ID);
                    update(updateUri,contentValues,null,null);
                    retUri = MovieContract.ReviewEntry.buildMovieUri(_id);
                    cursor.close();
                }else {
                    long _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME,null,contentValues);
                    if (_id>0)
                    {
                        retUri = MovieContract.ReviewEntry.buildMovieUri(_id);
                    }else {
                        throw new SQLException("Failed to insert into row "+uri);
                    }
                    Log.i(LOG_TAG,"insert excute");
                }
                break;
            case TRAILER:
                trailerId = contentValues.getAsString(MovieContract.TrailerEntry.COLUMN_TRAILER_ID);
                queryUri = MovieContract.TrailerEntry.buildUriWithTrailerId(trailerId);
                cursor=query(queryUri,
                        MovieContract.TRAILER_PROJECTION,
                        null,
                        null,
                        null);
                if (cursor!=null && cursor.moveToFirst() && (trailerId.equals(cursor.getString(MovieContract.TRAILER_COL_TRAILER_ID)))){
                    updateUri = MovieContract.TrailerEntry.buildUriWithTrailerId(trailerId);
                    long _id = cursor.getLong(MovieContract.TRAILER_COL_ID);
                    update(updateUri,contentValues,null,null);
                    retUri = MovieContract.TrailerEntry.buildMovieUri(_id);
                    cursor.close();
                }else {
                    long _id = db.insert(MovieContract.TrailerEntry.TABLE_NAME,null,contentValues);
                    if (_id>0)
                    {
                        retUri = MovieContract.TrailerEntry.buildMovieUri(_id);
                    }else {
                        throw new SQLException("Failed to insert into row "+uri);
                    }
                    Log.i(LOG_TAG,"insert excute");
                }
                break;
            default:
                throw new SQLException("Unknow Uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        Log.i(LOG_TAG,retUri.toString());
        return retUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long movieId = 0;
        String selection = null;
        String reviewId,trailerId;
        int rowsDeleted;
        if (null == s){
            s = "1";
        }
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,s,strings);
                break;
            case MOVIE_WITH_ID:
                movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);
                selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,
                       selection,
                        new String[]{Long.toString(movieId)}
                );
                break;
            case REVIEW:
                rowsDeleted = db.delete(MovieContract.ReviewEntry.TABLE_NAME,s,strings);
                break;
            case REVIEW_WITH_MOVIE_ID:
                movieId = MovieContract.ReviewEntry.getMovieIdFromUri(uri);
                selection = MovieContract.ReviewEntry.TABLE_NAME + "." +
                        MovieContract.ReviewEntry.COLUMN_MOVIE_ID +"=?";
                rowsDeleted =db.delete(MovieContract.ReviewEntry.TABLE_NAME,
                        selection,
                        new String[]{Long.toString(movieId)});
                break;
            case REVIEW_WITH_REVIEW_ID:
                reviewId = MovieContract.ReviewEntry.getReviewIdFromUri(uri);
                selection = MovieContract.ReviewEntry.TABLE_NAME+"."+
                        MovieContract.ReviewEntry.COLUMN_REVIEW_ID +"=?";
                rowsDeleted = db.delete(MovieContract.ReviewEntry.TABLE_NAME,
                        selection,
                        new String[]{reviewId});
                break;
            case TRAILER:
                rowsDeleted = db.delete(MovieContract.TrailerEntry.TABLE_NAME,s,strings);
                break;
            case TRAILER_WITH_MOVIE_ID:
                movieId = MovieContract.TrailerEntry.getMovieIdFromUri(uri);
                selection = MovieContract.TrailerEntry.TABLE_NAME + "." +
                        MovieContract.TrailerEntry.COLUMN_MOVIE_ID + "=?";
                rowsDeleted = db.delete(MovieContract.TrailerEntry.TABLE_NAME,
                        selection,
                        new String[]{Long.toString(movieId)});
                break;
            case TRAILER_WITH_TRAILER_ID:
                trailerId = MovieContract.TrailerEntry.getTrailerIdFromUri(uri);
                selection = MovieContract.TrailerEntry.TABLE_NAME + "." +
                        MovieContract.TrailerEntry.COLUMN_TRAILER_ID + "=?";
                rowsDeleted = db.delete(MovieContract.TrailerEntry.TABLE_NAME,
                        selection,
                        new String[]{trailerId});
                break;
            default:
                throw new SQLException("Unknown uri:"+uri);
        }

        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long movieId = 0;
        String reviewId,trailerId;
        String selection;
        int rowsUpdated;
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME,contentValues,s,strings);
                break;
            case MOVIE_WITH_ID:
                movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);

                Log.i(LOG_TAG,"update excute!");
                Log.i(LOG_TAG,"movieId: "+movieId);
                selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{Long.toString(movieId)}
                );
                break;
            case REVIEW:
                rowsUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME,contentValues,s,strings);
                break;
            case REVIEW_WITH_MOVIE_ID:
                movieId = MovieContract.ReviewEntry.getMovieIdFromUri(uri);
                selection = MovieContract.ReviewEntry.TABLE_NAME + "." +
                        MovieContract.ReviewEntry.COLUMN_MOVIE_ID + "=?";
                rowsUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{Long.toString(movieId)});
                break;
            case REVIEW_WITH_REVIEW_ID:
                reviewId = MovieContract.ReviewEntry.getReviewIdFromUri(uri);
                selection = MovieContract.ReviewEntry.TABLE_NAME + "." +
                        MovieContract.ReviewEntry.COLUMN_REVIEW_ID + "=?";
                rowsUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{reviewId
                        });
                break;
            case TRAILER:
                rowsUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME,contentValues,s,strings);
                break;
            case TRAILER_WITH_MOVIE_ID:
                movieId = MovieContract.TrailerEntry.getMovieIdFromUri(uri);
                selection = MovieContract.TrailerEntry.TABLE_NAME + "." +
                        MovieContract.TrailerEntry.COLUMN_MOVIE_ID + "=?";
                rowsUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{Long.toString(movieId)});
                break;
            case TRAILER_WITH_TRAILER_ID:
                trailerId = MovieContract.TrailerEntry.getTrailerIdFromUri(uri);
                selection = MovieContract.TrailerEntry.TABLE_NAME + "." +
                        MovieContract.TrailerEntry.COLUMN_TRAILER_ID + "=?";
                rowsUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{trailerId
                        });
                break;
            default:
                throw new SQLException("Unknown uri:"+uri);
        }

        if (rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authrity = MovieContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authrity, MovieContract.PATH_MOVIE,MOVIE);
        uriMatcher.addURI(authrity, MovieContract.PATH_MOVIE+"/#" , MOVIE_WITH_ID);
        uriMatcher.addURI(authrity, MovieContract.PATH_REVIEW,REVIEW);
        uriMatcher.addURI(authrity, MovieContract.PATH_REVIEW+"/#",REVIEW_WITH_MOVIE_ID);
        uriMatcher.addURI(authrity, MovieContract.PATH_REVIEW+"/*",REVIEW_WITH_REVIEW_ID);
        uriMatcher.addURI(authrity, MovieContract.PATH_TRAILER,TRAILER);
        uriMatcher.addURI(authrity, MovieContract.PATH_TRAILER+"/#",TRAILER_WITH_MOVIE_ID);
        uriMatcher.addURI(authrity, MovieContract.PATH_TRAILER+"/*",TRAILER_WITH_TRAILER_ID);

        return uriMatcher;
    }
}
