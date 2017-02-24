package com.example.cheng.myapplication.tasks;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.interfaces.OnMovieStatusTaskListener;

/**
 * Created by cheng on 2017/2/23.
 */

public class GetMovieStatusTask extends AsyncTask<Uri,Integer,Integer> {

    final static int RESULT_COLLECTED = 0;
    final static int RESULT_NOT_COLLECTED = 1;
    final static int RESULT_FAILED = -1;

    Context mContext;
    OnMovieStatusTaskListener mListener;
    Cursor mCursor;
    int mStatus = MovieContract.STATUS_NOT_COLLECTED;


    public GetMovieStatusTask(Context context,OnMovieStatusTaskListener onMovieStatusTaskListener) {
        this.mContext = context;
        this.mListener = onMovieStatusTaskListener;
    }

    @Override
    protected Integer doInBackground(Uri... uris) {
        mCursor = mContext.getContentResolver().query(
                uris[0],
                MovieContract.MOVIE_PROJECTION,
                null,
                null,
                null
        );
        if (mCursor!=null && mCursor.moveToFirst()){
            if (mCursor.getInt(MovieContract.COL_STATUS) == MovieContract.STATUS_COLLECTED){
                return RESULT_COLLECTED;
            }else if(mCursor.getInt(MovieContract.COL_STATUS) == MovieContract.STATUS_NOT_COLLECTED){
                return RESULT_NOT_COLLECTED;
            }else {
                return RESULT_FAILED;
            }
        }
        return RESULT_FAILED;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer){
            case RESULT_COLLECTED:
                mListener.onCollected();
                break;
            case RESULT_NOT_COLLECTED:
                mListener.onNotCollected();
                break;
            case RESULT_FAILED:
                mListener.onFailed();
                break;
            default:
                break;
        }
    }
}
