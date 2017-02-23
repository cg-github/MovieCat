package com.example.cheng.myapplication.tasks;

/**
 * Created by cheng on 2017/2/23.
 */

public interface OnMovieStatusTaskListener {
    void onCollected();
    void onNotCollected();
    void onFailed();
}
