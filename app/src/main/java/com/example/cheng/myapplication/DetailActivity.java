package com.example.cheng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG=DetailActivity.class.getSimpleName();

    Intent mMovieIntent;
//    Bundle mMovieBundle;
    ParcelableMovie mMovieData;
    TextView mTvTitle,mTvReleaseDate,mTvVote,mTvOverView;
    ImageView mImgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mMovieIntent = getIntent();
        mMovieData = mMovieIntent.getParcelableExtra(CommonUtil.MOVIE_DETAIL_DATA);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mTvVote = (TextView) findViewById(R.id.tv_vote);
        mTvOverView = (TextView) findViewById(R.id.tv_overview);
        mImgPoster = (ImageView) findViewById(R.id.img_poster);

        initView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(DetailActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mTvTitle.setText(mMovieData.getTitle());
        mTvReleaseDate.setText(mMovieData.getReleaseData());
        mTvVote.setText(mMovieData.getVote()+"/10");
        mTvOverView.setText("  "+mMovieData.getOverView());
        Picasso.with(getApplicationContext())
                .load(mMovieData.getPosterPath())
                .placeholder(android.R.drawable.picture_frame)
                .into(mImgPoster, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getApplicationContext(),"图片加载失败！",Toast.LENGTH_LONG).show();
                    }
                });

    }

}
