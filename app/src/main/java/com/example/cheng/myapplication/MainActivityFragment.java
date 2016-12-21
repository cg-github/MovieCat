package com.example.cheng.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import javax.net.ssl.HttpsURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  implements LoaderManager.LoaderCallbacks<List<HashMap<String,String>>>{

    GridView mGridview;
    MyGridViewAdapter mMovieAdapter;
    ArrayList<HashMap<String,String>> mList ;


    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridview = (GridView) rootView.findViewById(R.id.gridview_movie);
        mList = new ArrayList<HashMap<String,String>>();
        mMovieAdapter = new MyGridViewAdapter(getContext(),mList,R.layout.grid_list_item,null,null);
        mGridview.setAdapter(mMovieAdapter);
        mGridview.setNumColumns(2);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                HashMap<String,String> map = mList.get(i);
                ParcelableMovie pMovie = new ParcelableMovie(map);
                intent.putExtra(CommonUtil.MOVIE_DETAIL_DATA,pMovie);
                startActivity(intent);
            }
        });
        updateHotMovie();
        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();
        updateHotMovie();
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

        Log.i("cheng","I am in update");
        getLoaderManager().initLoader(0,null,this);
    }

    //检查网络连接
    public  boolean isOnLine(){
        ConnectivityManager cm  = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo!=null && netInfo.isConnected();
    }

    @Override
    public Loader<List<HashMap<String, String>>> onCreateLoader(int id, final Bundle args) {
        Log.i("cheng","I an in onCreateLoader!");
        return new AsyncTaskLoader<List<HashMap<String, String>>>(getContext()) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
                Log.i("cheng","I an in onStartLoading!");
            }

            @Override
            public List<HashMap<String, String>> loadInBackground() {
                Log.i("cheng","I an in loadInBackground!");
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String hotMovieStr=null;
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sortType = prefs.getString(getString(R.string.pref_sort_type_key),getString(R.string.pref_sort_type_default));
                URL url=UrlFactory.GetUrlBySortType(sortType);
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuffer=new StringBuffer();
                    String stringLine="";
                    int value = 0;

                    while ((value=reader.read())!=-1){
                        char c = (char)value;
                        stringBuffer.append(c);
                    }
                    hotMovieStr = stringBuffer.toString();
                    Log.i("cheng",String.valueOf(hotMovieStr.length()));
                    Log.i("cheng",hotMovieStr);
                } catch (IOException e) {
                    Log.i("cheng","I am in Io exception!");
                    e.printStackTrace();
                    return null;
                }finally {
                    Log.i("cheng","I am in final!");
                    if (connection!=null){
                        connection.disconnect();
                    }
                    try {
                        if (reader!=null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                try {
                    return JsonParser.GetHotMovies(hotMovieStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<HashMap<String, String>>> loader, List<HashMap<String, String>> data) {
        mList.clear();
        mList.addAll(data);
        mMovieAdapter.changeData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<HashMap<String, String>>> loader) {
//        mMovieAdapter.clearData();
    }


    private class FetchHotMoviesTask extends AsyncTask<URL,Void,List<HashMap<String,String>>>{


        @Override
        protected List<HashMap<String, String>> doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String hotMovieStr=null;
            URL url;
            if (urls.length!=0){
                url = urls[0];
            }else{
                return null;
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String stringLine="";
                int value = 0;

                while ((value=reader.read())!=-1){
                    char c = (char)value;
                    stringBuffer.append(c);
                }
                hotMovieStr = stringBuffer.toString();
                Log.i("cheng",String.valueOf(hotMovieStr.length()));
                Log.i("cheng",hotMovieStr);
            } catch (IOException e) {
                Log.i("cheng","I am in Io exception!");
                e.printStackTrace();
                return null;
            }finally {
                Log.i("cheng","I am in final!");
                if (connection!=null){
                    connection.disconnect();
                }
                try {
                    if (reader!=null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            try {
                return JsonParser.GetHotMovies(hotMovieStr);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);
            mList.clear();
            mList.addAll(hashMaps);
            mMovieAdapter.changeData(hashMaps);
        }
    }

    class MyGridViewAdapter extends SimpleAdapter{
        Context context;
        List<HashMap<String,String>> list;
        int resource;
        String[] from;
        int[] to;

        public MyGridViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.list = (List<HashMap<String, String>>) data;
            this.resource = resource;
            this.from = from;
            this.to = to;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView;
            ImageView imageView;
//            TextView textView;
            if (list == null){
                return null;
            }
            HashMap<String,String> map=list.get(position);

            if (convertView!=null){
                rootView = convertView;
            }else {
                LayoutInflater inflater = LayoutInflater.from(context);
                rootView = inflater.inflate(resource,null);
            }

            imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
//            textView = (TextView) rootView.findViewById(R.id.movie_name);
//            textView.setText(map.get(KEY_NAME));
            Picasso.with(context)
                    .load(map.get(CommonUtil.KEY_MOVIE_POSTER_PATE))
                    .placeholder(android.R.drawable.picture_frame)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getContext(),"图片加载失败！",Toast.LENGTH_LONG).show();
                        }
                    });
            return rootView;
//            return super.getView(position, convertView, parent);
        }

        public void changeData(List<HashMap<String, String>> hashMaps){
            list = hashMaps;
            notifyDataSetChanged();
        }

        public void clearData(){
            list = null;
            notifyDataSetChanged();
        }
    }
}
