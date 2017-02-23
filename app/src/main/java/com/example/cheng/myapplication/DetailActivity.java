package com.example.cheng.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myapplication.adapters.DetailAdapter;
import com.example.cheng.myapplication.adapters.MovieReviewsAdapter;
import com.example.cheng.myapplication.adapters.ReviewsAdapter;
import com.example.cheng.myapplication.adapters.TrailersAdapter;
import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.tasks.FetchMovieDetailTask;
import com.example.cheng.myapplication.tasks.FetchReviewsTask;
import com.example.cheng.myapplication.tasks.FetchTrailersTask;
import com.example.cheng.myapplication.tasks.GetMovieStatusTask;
import com.example.cheng.myapplication.tasks.OnMovieStatusTaskListener;
import com.example.cheng.myapplication.tasks.OnTaskListener;
import com.example.cheng.myapplication.tasks.UpdateStatusTask;
import com.example.cheng.myapplication.util.CommonUtil;
import com.example.cheng.myapplication.views.RecyclerViewDivider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG=DetailActivity.class.getSimpleName();

    RecyclerView mRecyclerView;
    DetailAdapter mDetailAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Cursor mCursor;
    Intent mIntent;
    long mMovieId;

    private static final int LOADER_MOVIE_DETAIL = 2;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mIntent = getIntent();
        mMovieId = mIntent.getLongExtra(CommonUtil.KEY_MOVIE_ID,0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mLayoutManager = new LinearLayoutManager(this){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 800;
            }
        };
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.detail_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDetailAdapter = new DetailAdapter(this,mCursor,2);
        mDetailAdapter.setTrailerNum(0);
        mRecyclerView.setAdapter(mDetailAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.VERTICAL,16,android.R.color.holo_green_light));

        getSupportLoaderManager().initLoader(LOADER_MOVIE_DETAIL,null,this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        final MenuItem menuItem = menu.getItem(1);
        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(mMovieId);
        new GetMovieStatusTask(getApplicationContext(), new OnMovieStatusTaskListener() {
            @Override
            public void onCollected() {
                menuItem.setTitle(R.string.action_collected);
            }

            @Override
            public void onNotCollected() {
                menuItem.setTitle(R.string.action_collect);
            }

            @Override
            public void onFailed() {
                Toast.makeText(getApplicationContext(),"获取收藏状态失败！",Toast.LENGTH_SHORT).show();
            }
        }).execute(uri);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                fetchData();
                break;
            case R.id.action_collect:
                updateMovieStatus(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = null;
        switch (id){
            case LOADER_MOVIE_DETAIL:
                uri = MovieContract.DetialEntry.buildUriWithMoiveId(mMovieId);
                return new CursorLoader(this,
                        uri,
                        null,
                        null,
                        null,
                        null
                );
            default:
                break;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int loadId = loader.getId();
        int nTrailerNum=0;
        switch (loadId){
            case LOADER_MOVIE_DETAIL:
                Toast.makeText(DetailActivity.this,"LOADER_MOVIE_DETAIL finished!",Toast.LENGTH_LONG).show();
                mCursor = data;
                if(mCursor.moveToFirst()){
                    do{
                        if (mCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID)!=-1){
                            nTrailerNum++;
                        }
                    }while (mCursor.moveToNext());
                    mCursor.moveToFirst();
                }
                mDetailAdapter.setTrailerNum(nTrailerNum);
                mDetailAdapter.swapCursor(data);
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDetailAdapter.swapCursor(null);
        mCursor = null;
    }


    private void fetchData(){
        new FetchTrailersTask(this,mMovieId,new OnTaskListener(){
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"get trailers success!",Toast.LENGTH_LONG).show();
                getSupportLoaderManager().restartLoader(LOADER_MOVIE_DETAIL,null,DetailActivity.this);
            }

            @Override
            public void onFailed() {
                Toast.makeText(getApplicationContext(),"get trailers failed!",Toast.LENGTH_LONG).show();
            }
        }).execute();
        new FetchReviewsTask(this, mMovieId, new OnTaskListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"get reviews success!",Toast.LENGTH_LONG).show();
                getSupportLoaderManager().restartLoader(LOADER_MOVIE_DETAIL,null,DetailActivity.this);
            }

            @Override
            public void onFailed() {
                Toast.makeText(getApplicationContext(),"get reviews failed!",Toast.LENGTH_LONG).show();
            }
        }).execute();
        new FetchMovieDetailTask(this, mMovieId, new OnTaskListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"get movie runtime success!",Toast.LENGTH_LONG).show();
                getSupportLoaderManager().restartLoader(LOADER_MOVIE_DETAIL,null,DetailActivity.this);
            }

            @Override
            public void onFailed() {
                Toast.makeText(getApplicationContext(),"get movie runtime failed!",Toast.LENGTH_LONG).show();
            }
        }).execute();
    }

    private void updateMovieStatus(MenuItem item) {
        final MenuItem itemCollect = item;
        String menuTitle = (String) itemCollect.getTitle();
        final int collectFlag;
        Uri uri = MovieContract.MovieEntry.buildUriWithMovieId(mMovieId);
        ContentValues updateValues = new ContentValues();
        if (menuTitle.equals(getString(R.string.action_collect))){
            updateValues.put(MovieContract.MovieEntry.COLUMN_STATUS,MovieContract.STATUS_COLLECTED);
            collectFlag = 0;
        }else {
            updateValues.put(MovieContract.MovieEntry.COLUMN_STATUS,MovieContract.STATUS_NOT_COLLECTED);
            collectFlag =1;
        }

        new UpdateStatusTask(DetailActivity.this,updateValues, new OnTaskListener() {
            @Override
            public void onSuccess() {
                if (collectFlag ==0) {
                    Toast.makeText(DetailActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
                    itemCollect.setTitle(R.string.action_collected);
                }else {
                    Toast.makeText(DetailActivity.this,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                    itemCollect.setTitle(R.string.action_collect);
                }
            }

            @Override
            public void onFailed() {
                if (collectFlag == 0){
                    Toast.makeText(DetailActivity.this,"收藏失败！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailActivity.this,"取消收藏失败！",Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(uri);
    }
}
