package com.example.cheng.myapplication;

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
            map.put(CommonUtil.KEY_NAME,jsonMovie.get("title").toString());
            map.put(CommonUtil.KEY_POSTER,CommonUtil.IMAGE_BASE_URI+CommonUtil.IMAGE_SCALE_W500+jsonMovie.get("poster_path"));
            list.add(map);
        }
        return list;
    }
}
