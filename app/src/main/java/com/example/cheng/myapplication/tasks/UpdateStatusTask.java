package com.example.cheng.myapplication.tasks;

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
    ContentValues updateValues;


    public UpdateStatusTask(Context mContext, ContentValues contentValues ,OnTaskListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.updateValues = contentValues;
    }


    @Override
    protected Integer doInBackground(Uri... uris) {
        if (uris.length>0){
            int rowUpdated=0;
            rowUpdated = mContext.getContentResolver().update(uris[0],
                    updateValues,
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
