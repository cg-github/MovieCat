package com.example.cheng.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.util.CommonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2016/12/22.
 */

public class MyGridViewAdapter extends SimpleAdapter {
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
        ViewHolder viewHolder;
        if (list == null){
            return null;
        }
        HashMap<String,String> map=list.get(position);

        if (convertView!=null){
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }else {
            LayoutInflater inflater = LayoutInflater.from(context);
            rootView = inflater.inflate(resource,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            rootView.setTag(viewHolder);
        }

        Picasso.with(context)
                .load(map.get(CommonUtil.KEY_MOVIE_POSTER_PATE))
                .placeholder(android.R.drawable.picture_frame)
                .into(viewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context,"图片加载失败！",Toast.LENGTH_LONG).show();
                    }
                });
        return rootView;
    }

    public void changeData(List<HashMap<String, String>> hashMaps){
        list = hashMaps;
        notifyDataSetChanged();
    }

    public void clearData(){
        list = null;
        notifyDataSetChanged();
    }
    class ViewHolder{
        ImageView imageView;
    }
}

