package com.example.cheng.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.util.CommonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2017/1/11.
 */

public class MovieReviewsAdapter extends SimpleAdapter {

    Context context;
    List<HashMap<String,String>> list;
    int resource;
    String[] from;
    int[] to;

    public MovieReviewsAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.list = (List<HashMap<String, String>>) data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }


    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        ViewHolder viewHolder;
        if (list == null){
            return null;
        }
        HashMap<String,String> hashMap = list.get(position);
        HashMap<String,String> map=list.get(position);

        if (convertView!=null){
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }else {
            LayoutInflater inflater = LayoutInflater.from(context);
            rootView = inflater.inflate(resource,null);
            viewHolder = new ViewHolder();
            viewHolder.tvReview = (TextView) rootView.findViewById(R.id.tv_review);
            viewHolder.tvAuthor = (TextView) rootView.findViewById(R.id.tv_author);
            rootView.setTag(viewHolder);
        }
        viewHolder.tvReview.setText(hashMap.get(CommonUtil.KEY_REVIEW_CONTENT));
        viewHolder.tvAuthor.setText(hashMap.get(CommonUtil.KEY_REVIEW_AUTHOR));
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
        TextView tvReview;
        TextView tvAuthor;
    }
}
