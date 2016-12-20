package com.example.cheng.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
}
