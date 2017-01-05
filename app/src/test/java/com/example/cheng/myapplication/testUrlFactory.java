package com.example.cheng.myapplication;

import com.example.cheng.myapplication.util.UrlFactory;

import junit.framework.TestCase;

/**
 * Created by cheng on 2016/12/20.
 */

public class testUrlFactory extends TestCase {
    private final static String TEST_URL = "http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key=5269bc7a3734ac2b6f73fc8425dcf655";
    public void testGetUrlBySortType(){
        assertEquals(TEST_URL, UrlFactory.GetUrlBySortType("top_rated"));
    }
}
