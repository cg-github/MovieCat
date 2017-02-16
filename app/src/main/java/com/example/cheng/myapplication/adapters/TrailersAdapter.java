package com.example.cheng.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.data.MovieContract;

/**
 * Created by cheng on 2017/2/16.
 */

public class TrailersAdapter extends CursorAdapter {
    public TrailersAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.trailer_list_item,viewGroup);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvTrailerName = (TextView) rootView.findViewById(R.id.tv_trailer_name);
        viewHolder.vvTrailer = (VideoView) rootView.findViewById(R.id.trailer_video);
        rootView.setTag(viewHolder);
        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        TextView tvTrailerName = viewHolder.tvTrailerName;
        VideoView vvTrailer = viewHolder.vvTrailer;

        tvTrailerName.setText(cursor.getString(MovieContract.TRAILER_COL_NAME));
    }

    class ViewHolder{
        TextView tvTrailerName;
        VideoView vvTrailer;
    }
}
