package com.example.cheng.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView mGridview;

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridview = (GridView) rootView.findViewById(R.id.gridview_movie);
        String[] from = {"name","poster"};
        int[] to = {R.id.movie_name,R.id.movie_poster};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),getData(),R.layout.grid_list_item,from,to);
        mGridview.setAdapter(simpleAdapter);
        mGridview.setNumColumns(2);
        return rootView;
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

    //get the data of hot movies
    private List<HashMap<String,Object>> getData(){
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map = null;
        String[] movieNames = {"movie1","movie2","movie3","movie4"};
        int[] posterIds = {R.drawable.hycs,R.drawable.jl,R.drawable.sqdwznl,R.drawable.tqd};
        for (int i= 0;i<movieNames.length;i++){
            map = new HashMap<String,Object>();
            map.put("name",movieNames[i]);
            map.put("poster",posterIds[i]);
            list.add(map);
        }
        return list;
    }

    //update hot movie data
    private void updateHotMovie(){
        final String TEST_URL="https://api.douban.com/v2/book/1220562";
        FetchHotMoviesTask fetchHotMoviesTask  = new FetchHotMoviesTask();
        URL url=null;
        try {
             url = new URL(TEST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        fetchHotMoviesTask.execute(url);
    }

    private class FetchHotMoviesTask extends AsyncTask<URL,Void,List<HashMap<String,Object>>>{


        @Override
        protected List<HashMap<String, Object>> doInBackground(URL... urls) {
            HttpsURLConnection connection = null;
            BufferedReader reader = null;
            String hotMovieStr=null;
            URL url;
            if (urls.length!=0){
                url = urls[0];
            }else{
                return null;
            }
            try {
                Log.i("cheng","I am here!");
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String stringLine=null;
                while ((stringLine=reader.readLine())!=null){
                    stringBuffer.append(stringLine+"\n");
                }
                if (stringBuffer.length()==0){
                    Log.i("cheng",stringBuffer.toString());
                    return null;
                }
                hotMovieStr = stringBuffer.toString();
                Log.i("cheng",hotMovieStr);
            } catch (IOException e) {
                Log.i("cheng","I am in Io exception!");
                e.printStackTrace();
                return null;
            }finally {
                Log.i("cheng","I am in finaalll!");
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

            return null;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, Object>> hashMaps) {
            super.onPostExecute(hashMaps);
        }
    }
}
