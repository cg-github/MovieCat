package com.example.cheng.myapplication.util;

import android.net.ConnectivityManager;

import java.security.PublicKey;

/**
 * Created by cheng on 2016/12/19.
 */

public class CommonUtil {

    //图片基础地址
    public static final String IMAGE_BASE_URI = "http://image.tmdb.org/t/p/";

    //请求网络图片大小
    public static final String IMAGE_SCALE_ORIGINAL = "original";
    public static final String IMAGE_SCALE_W92 = "w92";
    public static final String IMAGE_SCALE_W154 = "w154";
    public static final String IMAGE_SCALE_W185 = "w185";
    public static final String IMAGE_SCALE_W342 = "w342";
    public static final String IMAGE_SCALE_W500 = "w500";
    public static final String IMAGE_SCALE_W780 = "w780";

    //
    public static final String MOVIE_DETAIL_DATA = "movie_detail_data";

    //后台返回电影详情信息各键值
    public static final String KEY_MOVIE_POSTER_PATE = "poster_path";
    public static final String KEY_ADULT = "adult";
    public static final String KEY_POPULARITY = "popularity";
    public static final String KEY_MOVIE_ID = "id";
    public static final String KEY_MOVIE_TITLE = "title";
    public static final String KEY_MOVIE_OVERVIEW = "overview";
    public static final String KEY_MOVIE_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_MOVIE_RELEASE_DATE = "release_date";
    public static final String KEY_MOVIE_VOTE_AVERAGE = "vote_average";



    //gridview 上绑定数据键值
    public static final int KEY_VIEW_HOLDER = 0;
    public static final int KEY_VIEW_MOVIE_ID = 1;

    //评论数据中各键
    public static final String KEY_REVIEW_RESULTS = "results";
    public static final String KEY_REVIEW_ID = "id";
    public static final String KEY_REVIEW_AUTHOR = "author";
    public static final String KEY_REVIEW_CONTENT = "content";
    public static final String KEY_REVIEW_URL = "url";

}
