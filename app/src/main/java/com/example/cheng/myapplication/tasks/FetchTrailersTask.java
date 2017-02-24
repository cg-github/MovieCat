package com.example.cheng.myapplication.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.interfaces.OnTaskListener;
import com.example.cheng.myapplication.util.JsonParser;
import com.example.cheng.myapplication.util.UrlFactory;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by cheng on 2017/2/18.
 */

public class FetchTrailersTask extends AsyncTask<Void,Void,Integer> {

    private final static String LOG_TAG = FetchTrailersTask.class.getSimpleName();

    OnTaskListener mListener;
    Context mContext;
    long mMovieId;

    private final static int RESULT_OK = 0;
    private final static int RESULT_FAIL = 1;


    public FetchTrailersTask( Context context,long movidId,OnTaskListener onTaskListener) {
        mContext = context;
        mListener = onTaskListener;
        mMovieId = movidId;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String hotMovieStr=null;
        URL url= UrlFactory.GetTrailerUrlById(mMovieId);

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
                Vector<ContentValues> cVVector = JsonParser.StoreMovieTrailers(hotMovieStr);
                ContentValues[] contentValues = new ContentValues[cVVector.size()];
                cVVector.toArray(contentValues);
                mContext.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI,contentValues);
                return RESULT_OK;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return RESULT_FAIL;
        }
        return RESULT_FAIL;
//        HttpURLConnection httpURLConnection = null;
//        String jsonStr ="";
//        String tmpStr = null;
//        BufferedReader reader = null;
//        URL url = UrlFactory.GetTrailerUrlById(mMovieId);
//
//        Log.i(LOG_TAG,url.toString());
//
//        try {
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//
//            InputStream inputStream = httpURLConnection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            while((tmpStr = reader.readLine())!=null){
//                jsonStr+=tmpStr;
//            }
//
//            Log.i(LOG_TAG,jsonStr);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (httpURLConnection!=null){
//                httpURLConnection.disconnect();
//            }
//            if (reader!=null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                Vector<ContentValues> cVVector = JsonParser.StoreMovieTrailers(jsonStr);
//                ContentValues[] contentValues = new ContentValues[cVVector.size()];
//                cVVector.toArray(contentValues);
//                mContext.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI,contentValues);
//                return RESULT_OK;
//            } catch (JSONException e) {
//                Log.i("json error",e.toString());
//            }
//        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer==RESULT_OK){
            mListener.onSuccess();
        }else {
            mListener.onFailed();
        }
        super.onPostExecute(integer);
    }
}
