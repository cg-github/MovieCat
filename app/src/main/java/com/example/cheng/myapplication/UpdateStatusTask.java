package com.example.cheng.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.cheng.myapplication.data.MovieContract;

/**
 * Created by cheng on 2017/1/6.
 */

public class UpdateStatusTask extends AsyncTask<Uri,Integer,Integer> {

    private static final int RESULT_SUCCESS = 0;
    private static final int RESULT_FAIL = 1;

    Context mContext;
    OnTaskListener mListener;


    public UpdateStatusTask(Context mContext, OnTaskListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }


    @Override
    protected Integer doInBackground(Uri... uris) {
        if (uris.length>0){
            ContentValues contentValues = new ContentValues();
            int rowUpdated=0;
            contentValues.put(MovieContract.MovieEntry.COLUMN_STATUS
                    ,MovieContract.STATUS_COLLECTED);
            rowUpdated = mContext.getContentResolver().update(uris[0],
                    contentValues,
                    null,
                    null
            );
            if (rowUpdated ==1){
                return RESULT_SUCCESS;
            }
        }
        return RESULT_FAIL;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==RESULT_SUCCESS){
            mListener.onSuccess();
        }else{
            mListener.onFailed();
        }
    }
}
