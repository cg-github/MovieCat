package com.example.cheng.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by cheng on 2016/12/20.
 */
@RunWith(AndroidJUnit4.class)
public class testUrlFactoryAndroid {

    private final static String TEST_URL = "http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key=5269bc7a3734ac2b6f73fc8425dcf655";
    @Test
    public void testGetUrlBySortType(){
        assertEquals(TEST_URL,UrlFactory.GetUrlBySortType("top_rated").toString());
    }
}
