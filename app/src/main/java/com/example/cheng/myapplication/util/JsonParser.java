package com.example.cheng.myapplication.util;

import android.content.ContentValues;
import android.util.Log;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.util.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PushbackInputStream;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by cheng on 2016/12/19.
 */

public class JsonParser {


    //Parse the hot movie data
    public static List<HashMap<String,String>> GetHotMovies(String jsonStr) throws JSONException {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = null;
        JSONObject json = new JSONObject(jsonStr);
        JSONArray jsonArray = json.getJSONArray("results");
        JSONObject jsonMovie=null;
        for (int i=0;i<jsonArray.length();i++){
            jsonMovie = jsonArray.getJSONObject(i);
            map = new HashMap<String, String>();
            map.put(CommonUtil.KEY_MOVIE_TITLE,jsonMovie.get(CommonUtil.KEY_MOVIE_TITLE).toString());
            map.put(CommonUtil.KEY_MOVIE_POSTER_PATE,CommonUtil.IMAGE_BASE_URI+CommonUtil.IMAGE_SCALE_W500+jsonMovie.get(CommonUtil.KEY_MOVIE_POSTER_PATE));
            map.put(CommonUtil.KEY_MOVIE_OVERVIEW,jsonMovie.get(CommonUtil.KEY_MOVIE_OVERVIEW).toString());
            map.put(CommonUtil.KEY_MOVIE_RELEASE_DATE,jsonMovie.get(CommonUtil.KEY_MOVIE_RELEASE_DATE).toString());
            map.put(CommonUtil.KEY_MOVIE_VOTE_AVERAGE,jsonMovie.get(CommonUtil.KEY_MOVIE_VOTE_AVERAGE).toString());
            list.add(map);
            Log.i("cheng","the counts of Movies: "+i);
        }
        return list;
    }

    //parse the reviews data
    public static List<HashMap<String,String>> GetMovieReviews(String jsonStr) throws JSONException {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = null;
        JSONObject json = new JSONObject(jsonStr);
        JSONArray jsonArray = json.getJSONArray("results");
        JSONObject jsonMovie=null;
        for (int i=0;i<jsonArray.length();i++){
            jsonMovie = jsonArray.getJSONObject(i);
            map = new HashMap<String, String>();
            map.put(CommonUtil.KEY_REVIEW_ID,jsonMovie.get(CommonUtil.KEY_REVIEW_ID).toString());
            map.put(CommonUtil.KEY_REVIEW_AUTHOR,jsonMovie.get(CommonUtil.KEY_REVIEW_AUTHOR).toString());
            map.put(CommonUtil.KEY_REVIEW_CONTENT,jsonMovie.get(CommonUtil.KEY_REVIEW_CONTENT).toString());
            map.put(CommonUtil.KEY_REVIEW_URL,jsonMovie.get(CommonUtil.KEY_REVIEW_URL).toString());
            list.add(map);
        }
        return list;
    }


    //store hot movies
    public static Vector<ContentValues> StoreHotMovies(String jsonStr) throws JSONException {
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
        return cVVector;
    }

    public static Vector<ContentValues> StoreMovieReviews(String jsonStr) throws JSONException {

        JSONObject json = new JSONObject(jsonStr);
        long movieId = json.getLong("id");
        JSONArray jsonArray = json.getJSONArray("results");
        Vector<ContentValues> cVVector = new Vector<>(jsonArray.length());
        JSONObject jsonMovie=null;
        for (int i=0;i<jsonArray.length();i++){
            jsonMovie = jsonArray.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID,jsonMovie.get(CommonUtil.KEY_REVIEW_ID).toString());
            contentValues.put(MovieContract.ReviewEntry.COLUMN_AUTHOR,jsonMovie.get(CommonUtil.KEY_REVIEW_AUTHOR).toString());
            contentValues.put(MovieContract.ReviewEntry.COLUMN_CONTENT,jsonMovie.get(CommonUtil.KEY_REVIEW_CONTENT).toString());
            contentValues.put(MovieContract.ReviewEntry.COLUMN_URL,jsonMovie.get(CommonUtil.KEY_REVIEW_URL).toString());
            contentValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID,movieId);
            cVVector.add(contentValues);
        }
        return cVVector;
    }

    public static Vector<ContentValues> StoreMovieTrailers(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        long moiveId = json.getLong("id");
        JSONArray jsonArray = json.getJSONArray("results");
        Vector<ContentValues> cVVector = new Vector<>(jsonArray.length());
        JSONObject jsonTrailer = null;

        for (int i=0;i<jsonArray.length();i++){
            jsonTrailer = jsonArray.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID,jsonTrailer.get(CommonUtil.KEY_TRAILER_ID).toString());
            contentValues.put(MovieContract.TrailerEntry.COLUMN_KEY,jsonTrailer.get(CommonUtil.KEY_TRAILER_KEY).toString());
            contentValues.put(MovieContract.TrailerEntry.COLUMN_NAME,jsonTrailer.get(CommonUtil.KEY_TRAILER_NAME).toString());
            contentValues.put(MovieContract.TrailerEntry.COLUMN_SITE,jsonTrailer.get(CommonUtil.KEY_TRAILER_SITE).toString());
            contentValues.put(MovieContract.TrailerEntry.COLUMN_SIZE,jsonTrailer.get(CommonUtil.KEY_TRAILER_SIZE).toString());
            contentValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID,moiveId);
            cVVector.add(contentValues);
        }
        return cVVector;
    }

}
