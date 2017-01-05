package com.example.cheng.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.util.CommonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by cheng on 2017/1/5.
 */

public class MovieListAdapter extends CursorAdapter {

    public MovieListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.grid_list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        rootView.setTag(viewHolder);
        return rootView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
         ImageView imageView;
         String posterPath;
         ViewHolder viewHolder;
         viewHolder = (ViewHolder) view.getTag();


        view.setTag(R.string.key_movie_id,cursor.getLong(MovieContract.COL_MOVIE_ID));
        imageView = viewHolder.imageView;
        posterPath = CommonUtil.IMAGE_BASE_URI+CommonUtil.IMAGE_SCALE_W500+cursor.getString(MovieContract.COL_POSTER_PATH);
        Picasso.with(context)
                .load(posterPath)
                .placeholder(android.R.drawable.picture_frame)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context,"图片加载失败！",Toast.LENGTH_LONG).show();
                    }
                });
    }

    class ViewHolder{
        ImageView imageView;
    }
}
