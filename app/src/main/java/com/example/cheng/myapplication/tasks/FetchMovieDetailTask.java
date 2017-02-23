package com.example.cheng.myapplication.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.adapters.MovieReviewsAdapter;
import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.util.CommonUtil;
import com.example.cheng.myapplication.util.JsonParser;
import com.example.cheng.myapplication.util.UrlFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by cheng on 2017/1/11.
 */

public class FetchMovieDetailTask extends AsyncTask<Void,Void,Integer>{

    private static final String LOG_TAG = FetchMovieDetailTask.class.getSimpleName();

    final static int RESULT_OK = 0;
    final static int RESULT_FAIL = 1;

    OnTaskListener mListener;

    Context mContext;
    long mMovieId;
    TextView mTv;

    public FetchMovieDetailTask(Context context ,long movieId,OnTaskListener listener) {
        mContext = context;
        mMovieId = movieId;
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String hotMovieStr=null;
        URL url= UrlFactory.GetMovieDetialUrlById(mMovieId);

        Log.i(LOG_TAG,url.toString());
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer=new StringBuffer();
            String stringLine="";
            int value = 0;

            while ((value=reader.read())!=-1){
                char c = (char)value;
                stringBuffer.append(c);
            }
            hotMovieStr = stringBuffer.toString();
            Log.i(LOG_TAG,hotMovieStr);
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            if (connection!=null){
                connection.disconnect();
            }
            try {
                if (reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (hotMovieStr!=null && hotMovieStr.length()>0){
                int runtime = JsonParser.GetMovieRuntime(hotMovieStr);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME,runtime);
                Uri updateUri = MovieContract.MovieEntry.buildUriWithMovieId(mMovieId);
                mContext.getContentResolver().update(updateUri,contentValues,null,null);
                return RESULT_OK;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return RESULT_FAIL;
        }
        return RESULT_FAIL;
    }

    @Override
    protected void onPostExecute(Integer i) {
        super.onPostExecute(i);
        if (i == RESULT_FAIL){
            mListener.onFailed();
        }else if (i == RESULT_OK){
            mListener.onSuccess();
        }else{
            mListener.onFailed();
        }

    }
}

