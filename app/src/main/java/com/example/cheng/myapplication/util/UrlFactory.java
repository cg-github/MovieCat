package com.example.cheng.myapplication.util;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cheng on 2016/12/20.
 */

public class UrlFactory {
    private final static String LOG_TAG = UrlFactory.class.getSimpleName();
    private final static String TMDB_SCHEME = "http";
    private final static String TMDB_AUTHRITY = "api.themoviedb.org";
    private final static String LANGUAGE = "zh";
    private final static String TMDB_VERSION_PATH = "3";
    private final static String TMDB_MOVIE_PATH = "movie";
    private final static String TMDB_API_KEY = "5269bc7a3734ac2b6f73fc8425dcf655";
    private final static String TMDB_REVIEWS_PATH = "reviews";
    private final static String TMDB_TRAILERS_PATH = "videos";

    public static URL GetUrlBySortType(String sortType){
        if(sortType.equals(CommonUtil.KEY_POPULARITY)){
            sortType = "popular";
        }else {
            sortType = "top_rated";
        }
        URL url = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(TMDB_SCHEME)
                .authority(TMDB_AUTHRITY)
                .appendPath(TMDB_VERSION_PATH)
                .appendPath(TMDB_MOVIE_PATH)
                .appendPath(sortType)
                .appendQueryParameter("language",LANGUAGE)
                .appendQueryParameter("api_key",TMDB_API_KEY);

        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG,e.toString());
        }
        return url;
    }

    public static URL GetReviewUrlById(long movieId){
        URL url = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(TMDB_SCHEME)
                .authority(TMDB_AUTHRITY)
                .appendPath(TMDB_VERSION_PATH)
                .appendPath(TMDB_MOVIE_PATH)
                .appendPath(Long.toString(movieId))
                .appendPath(TMDB_REVIEWS_PATH)
                .appendQueryParameter("api_key",TMDB_API_KEY);

        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG,e.toString());
        }
        return url;
    }

    public static URL GetTrailerUrlById(long movieId) {
        URL url = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(TMDB_SCHEME)
                .authority(TMDB_AUTHRITY)
                .appendPath(TMDB_VERSION_PATH)
                .appendPath(TMDB_MOVIE_PATH)
                .appendPath(Long.toString(movieId))
                .appendPath(TMDB_TRAILERS_PATH)
                .appendQueryParameter("api_key",TMDB_API_KEY);

        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
