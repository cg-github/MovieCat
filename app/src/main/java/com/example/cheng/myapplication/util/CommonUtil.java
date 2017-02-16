package com.example.cheng.myapplication.util;

import android.net.ConnectivityManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    //预告片数据中各键
    public static final String KEY_TRAILER_ID = "id";
    public static final String KEY_TRAILER_KEY = "key";
    public static final String KEY_TRAILER_NAME = "name";
    public static final String KEY_TRAILER_SITE = "site";
    public static final String KEY_TRAILER_SIZE = "size";

    //设置listview 高度
//    public static void setListViewHeightBasedOnChildren(ListView listView){
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null){
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i=0;i<listAdapter.getCount();i++){
//            View listItem = listAdapter.getView(i,null,listView);
//            listItem.measure(0,0);
//            totalHeight +=listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight +(listView.getDividerHeight()*(listAdapter.getCount()-1));
//        listView.setLayoutParams(params);
//    }
    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
