package com.example.cheng.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myapplication.adapters.MovieReviewsAdapter;
import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.tasks.FetchReviewsTask;
import com.example.cheng.myapplication.tasks.OnTaskListener;
import com.example.cheng.myapplication.tasks.UpdateStatusTask;
import com.example.cheng.myapplication.util.CommonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String LOG_TAG=DetailActivity.class.getSimpleName();

    Intent mMovieIntent;
    TextView mTvTitle,mTvReleaseDate,mTvVote,mTvOverView,mReviewNote;
    ImageView mImgPoster;
    Button mBtnReview;
    ListView mListReview;
    Cursor mCursor;

    MovieReviewsAdapter mReviewsAdapter;
    List<HashMap<String,String>> mList;

    long mMovieId;

    private static final int LOADER_MOVIE_DETAIL = 2;
    private static final int LOADER_UPDATE_STATUS = 3;

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

                int status = mCursor.getInt(MovieContract.COL_STATUS);
                if (status==1){
                    Snackbar.make(view, "该电影已收藏！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(mMovieId);
                    new UpdateStatusTask(DetailActivity.this, new OnTaskListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DetailActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(DetailActivity.this,"收藏失败！",Toast.LENGTH_SHORT).show();
                        }
                    }).execute(uri);
                }

            }
        });

        mMovieIntent = getIntent();
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mTvVote = (TextView) findViewById(R.id.tv_vote);
        mTvOverView = (TextView) findViewById(R.id.tv_overview);
        mImgPoster = (ImageView) findViewById(R.id.img_poster);
        mReviewNote = (TextView) findViewById(R.id.tv_reviews_note);
        mBtnReview = (Button) findViewById(R.id.btn_refresh_reviews);
        mListReview = (ListView) findViewById(R.id.list_reviews);

        mMovieId = mMovieIntent.getLongExtra(CommonUtil.KEY_MOVIE_ID,550);
        mReviewsAdapter = new MovieReviewsAdapter(this,null,R.layout.review_list_item,null,null);
        mListReview.setAdapter(mReviewsAdapter);

        mBtnReview.setOnClickListener(this);

        getSupportLoaderManager().initLoader(LOADER_MOVIE_DETAIL,null,this);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void initView(Cursor cursor) {
        mTvTitle.setText(cursor.getString(MovieContract.COL_TITLE));
        mTvReleaseDate.setText(cursor.getString(MovieContract.COL_RELEASE_DATE));
        mTvVote.setText(cursor.getDouble(MovieContract.COL_VOTE_AVERAGE)+"/10");
        mTvOverView.setText("  "+cursor.getString(MovieContract.COL_OVERVIEW));

        String posterPath = CommonUtil.IMAGE_BASE_URI+CommonUtil.IMAGE_SCALE_W500+cursor.getString(MovieContract.COL_POSTER_PATH);

        Picasso.with(getApplicationContext())
                .load(posterPath)
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(mMovieId);
        return new CursorLoader(this,
                uri,
                MovieContract.MOVIE_PROJECTION,
                null,
                null,
                null
                );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()){
            mCursor = data;
            initView(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_refresh_reviews:
                new FetchReviewsTask(this,mReviewsAdapter,mMovieId).execute();
                break;
            default:
                break;
        }
    }
}
