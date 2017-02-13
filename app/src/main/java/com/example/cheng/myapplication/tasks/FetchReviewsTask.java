package com.example.cheng.myapplication.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.adapters.MovieReviewsAdapter;
import com.example.cheng.myapplication.util.CommonUtil;
import com.example.cheng.myapplication.util.JsonParser;
import com.example.cheng.myapplication.util.UrlFactory;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cheng on 2017/1/11.
 */

public class FetchReviewsTask extends AsyncTask<Void,Void,List<HashMap<String,String>>>{

    private static final String LOG_TAG = FetchReviewsTask.class.getSimpleName();

    Context mContext;
    MovieReviewsAdapter mAdapter;
    ListView mListView;
    long mMovieId;
    TextView mTv;

    public FetchReviewsTask(Context context , MovieReviewsAdapter adapter, ListView listView ,long movieId,TextView tv) {
        mContext = context;
        mAdapter = adapter;
        mListView = listView;
        mMovieId = movieId;
        mTv = tv;
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String hotMovieStr=null;
        URL url= UrlFactory.GetReviewUrlById(mMovieId);

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
                return JsonParser.GetMovieReviews(hotMovieStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
        super.onPostExecute(hashMaps);
        mTv.setText("reviews count:"+hashMaps.size());
        mAdapter.changeData(hashMaps);
//        CommonUtil.setListViewHeightBasedOnChildren(mListView);
    }
}
