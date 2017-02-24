package com.example.cheng.myapplication.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.interfaces.OnTaskListener;
import com.example.cheng.myapplication.util.CommonUtil;
import com.example.cheng.myapplication.util.UrlFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

//import static android.provider.Settings.Global.getString;

/**
 * Created by cheng on 2017/1/5.
 */

public class FetchMovieTask extends AsyncTask<Void,Void,Void> {

    static final String LOG_TAG=FetchMovieTask.class.getSimpleName();

    Context mContext;
    OnTaskListener mListener;

    public FetchMovieTask(Context context , OnTaskListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    //store hot movies
    public int StoreHotMovies(String jsonStr) throws JSONException {

        int rowInserted=0;
        JSONObject json = new JSONObject(jsonStr);
        JSONArray jsonArray = json.getJSONArray("results");
        JSONObject jsonMovie=null;
        Vector<ContentValues> cVVector = new Vector<ContentValues>(jsonArray.length());
        for (int i=0;i<jsonArray.length();i++){
            jsonMovie = jsonArray.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            int adult = 0;
            if (jsonMovie.getBoolean(CommonUtil.KEY_ADULT)){
                adult = 1;
            }
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,jsonMovie.getString(CommonUtil.KEY_MOVIE_POSTER_PATE));
            contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT,adult);
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,jsonMovie.getString(CommonUtil.KEY_MOVIE_OVERVIEW));
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,jsonMovie.getString(CommonUtil.KEY_MOVIE_RELEASE_DATE));
            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,jsonMovie.getLong(CommonUtil.KEY_MOVIE_ID));
            contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY,jsonMovie.getDouble(CommonUtil.KEY_POPULARITY));
            contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,jsonMovie.getDouble(CommonUtil.KEY_MOVIE_VOTE_AVERAGE));
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE,jsonMovie.getString(CommonUtil.KEY_MOVIE_TITLE));
            cVVector.add(contentValues);
        }
        ContentValues[] cValues = new ContentValues[cVVector.size()];
        cVVector.toArray(cValues);
//        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,null,null);
        rowInserted = mContext.getContentResolver().bulkInsert(
                MovieContract.MovieEntry.CONTENT_URI,
                cValues
        );

        return rowInserted;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String hotMovieStr=null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortType = prefs.getString(mContext.getString(R.string.pref_sort_type_key),mContext.getString(R.string.pref_sort_type_default));
        URL url= UrlFactory.GetUrlBySortType(sortType);

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
                StoreHotMovies(hotMovieStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.onSuccess();
    }
}
