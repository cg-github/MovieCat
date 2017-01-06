package com.example.cheng.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
                long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);
                Log.i("cheng","I am here movie with id"+movieId);
                String selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                return db.query(MovieContract.MovieEntry.TABLE_NAME,
                        strings,
                        selection,
                        new String[]{Long.toString(movieId)},
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
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri retUri = null;
        long movieId = contentValues.getAsLong(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                Uri queryUri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
                Cursor cursor =query(queryUri,
                            MovieContract.MOVIE_PROJECTION,
                            null,
                            null,
                            null
                        );
                if (cursor!=null && cursor.moveToFirst() && (movieId == cursor.getLong(MovieContract.COL_MOVIE_ID))){
                    Uri updateUri = MovieContract.MovieEntry.buildUriWithMovieId(movieId);
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
        int rowsDeleted;
        if (null == s){
            s = "1";
        }
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,s,strings);
                break;
            case MOVIE_WITH_ID:
                long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);
                String selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,
                       selection,
                        new String[]{Long.toString(movieId)}
                );
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
        int rowsUpdated;
        switch (mUriMatcher.match(uri)){
            case MOVIE:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME,contentValues,s,strings);
                break;
            case MOVIE_WITH_ID:
                long movieId = MovieContract.MovieEntry.getMovieIdFromUri(uri);

                Log.i(LOG_TAG,"update excute!");
                Log.i(LOG_TAG,"movieId: "+movieId);
                String selection = MovieContract.MovieEntry.TABLE_NAME+"."+
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        new String[]{Long.toString(movieId)}
                );
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

        return uriMatcher;
    }
}
