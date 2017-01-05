package com.example.cheng.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.util.CommonUtil;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    static final int LOADER_MOVIE_LIST = 1;

    GridView mGridview;
    MovieListAdapter mMovieAdapter;
    ArrayList<HashMap<String,String>> mList ;
    String mSortType;


    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridview = (GridView) rootView.findViewById(R.id.gridview_movie);
        mList = new ArrayList<HashMap<String,String>>();
        mMovieAdapter = new MovieListAdapter(getContext(),null,0);
        mGridview.setAdapter(mMovieAdapter);
        mGridview.setNumColumns(2);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                long movieId = Long.parseLong(view.getTag(R.string.key_movie_id).toString());
                intent.putExtra(CommonUtil.KEY_MOVIE_ID,movieId);
                startActivity(intent);
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mSortType = prefs.getString(getContext().getString(R.string.pref_sort_type_key),getContext().getString(R.string.pref_sort_type_default));
        getLoaderManager().initLoader(LOADER_MOVIE_LIST,null,this);
        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortType = prefs.getString(getContext().getString(R.string.pref_sort_type_key),getContext().getString(R.string.pref_sort_type_default));
        if (!sortType.equals(mSortType)){
            mSortType = sortType;
            getLoaderManager().restartLoader(LOADER_MOVIE_LIST,null,this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                updateHotMovie();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //update hot movie data
    private void updateHotMovie(){
        if (!isOnLine()){
            Toast.makeText(getContext(),"请检查您的网络状态！",Toast.LENGTH_LONG).show();
            return;
        }

        new FetchMovieTask(getContext()).execute();
        getLoaderManager().restartLoader(LOADER_MOVIE_LIST,null,this);
 //       getLoaderManager().initLoader(0,null,this);
    }

    //检查网络连接
    public  boolean isOnLine(){
        ConnectivityManager cm  = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo!=null && netInfo.isConnected();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder;

        if (mSortType.equals("popularity")){
            sortOrder = MovieContract.SORT_BY_POPULARITY;
        }else {
            sortOrder = MovieContract.SORT_BY_VOTE;
        }

        return new CursorLoader(getContext(),
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MOVIE_PROJECTION,
                null,
                null,
                sortOrder
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }
}
