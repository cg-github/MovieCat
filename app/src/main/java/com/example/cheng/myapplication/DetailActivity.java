package com.example.cheng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG=DetailActivity.class.getSimpleName();

    Intent mMovieIntent;
    Bundle mMovieBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mMovieIntent = getIntent();
        mMovieBundle = mMovieIntent.getBundleExtra(CommonUtil.MOVIE_DETAIL_DATA);
        Log.i(LOG_TAG,"I am here!");
        Log.i(LOG_TAG,mMovieBundle.getString(CommonUtil.KEY_MOVIE_TITLE));
        Log.i(LOG_TAG,mMovieBundle.toString());
    }

}
