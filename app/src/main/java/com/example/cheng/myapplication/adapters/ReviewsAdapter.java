package com.example.cheng.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.data.MovieContract;

/**
 * Created by cheng on 2017/2/15.
 */

public class ReviewsAdapter extends CursorAdapter {

    public ReviewsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.review_list_item,viewGroup);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvAuthor = (TextView) rootView.findViewById(R.id.tv_author);
        viewHolder.tvReview = (TextView) rootView.findViewById(R.id.tv_review);
        rootView.setTag(viewHolder);
        return rootView;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        TextView tvAuthor = viewHolder.tvAuthor;
        TextView tvReview = viewHolder.tvReview;

        tvAuthor.setText(cursor.getString(MovieContract.REVIEW_COL_AUTHOR));
        tvReview.setText(cursor.getString(MovieContract.REVIEW_COL_CONTENT));
    }

    class ViewHolder{
        TextView tvReview;
        TextView tvAuthor;
    }
}
