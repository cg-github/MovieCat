package com.example.cheng.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cheng.myapplication.R;
import com.example.cheng.myapplication.data.MovieContract;
import com.example.cheng.myapplication.libs.BaseAbstractRecycleCursorAdapter;
import com.example.cheng.myapplication.util.CommonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by cheng on 2017/2/15.
 */

public class DetailAdapter extends BaseAbstractRecycleCursorAdapter{

    final static int TYPE_MOVIE = 0;
    final static int TYPE_REVIEW = 1;
    final static int TYPE_TRAILER = 2;

    private final static String VOTE_PADDING = "/10";
    private final static String OVERVIEW_PADDING = "  ";

    Context mContext ;
    Cursor mCursor;


    public DetailAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mCursor = c;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;

        if (mCursor.moveToFirst() && mCursor.move(position)){
            if (mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW) !=-1){
                viewType = TYPE_MOVIE;
            }
            if (mCursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_REVIEW_ID) !=-1){
                viewType = TYPE_REVIEW;
            }
            if (mCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID) !=-1){
                viewType = TYPE_TRAILER;
            }
            mCursor.close();
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        int viewType = -1;
        if (mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW) !=-1){
            viewType = TYPE_MOVIE;
        }
        if (mCursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_REVIEW_ID) !=-1){
            viewType = TYPE_REVIEW;
        }
        if (mCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID) !=-1){
            viewType = TYPE_TRAILER;
        }
        switch (viewType){
            case TYPE_MOVIE:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.mTvTitle.setText(cursor.getString(MovieContract.COL_TITLE));
                movieViewHolder.mTvReleaseDate.setText(cursor.getString(MovieContract.COL_RELEASE_DATE));
                String vote_average = cursor.getDouble(MovieContract.COL_VOTE_AVERAGE)+VOTE_PADDING;
                movieViewHolder.mTvVote.setText(vote_average);
                String overview =OVERVIEW_PADDING+cursor.getString(MovieContract.COL_OVERVIEW);
                movieViewHolder.mTvOverView.setText(overview);

                String posterPath = CommonUtil.IMAGE_BASE_URI+CommonUtil.IMAGE_SCALE_W500+cursor.getString(MovieContract.COL_POSTER_PATH);

                Picasso.with(mContext)
                        .load(posterPath)
                        .placeholder(android.R.drawable.picture_frame)
                        .into(movieViewHolder.mImgPoster, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mContext,"图片加载失败！",Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            case TYPE_REVIEW:
                ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
                reviewViewHolder.mTvReview.setText(cursor.getString(MovieContract.REVIEW_COL_CONTENT));
                reviewViewHolder.mTvAuthor.setText(cursor.getString(MovieContract.REVIEW_COL_AUTHOR));
                break;
            case TYPE_TRAILER:
                TrailerViewHolder trailerViewHolder = (TrailerViewHolder) holder;
                trailerViewHolder.mTvTrailerName.setText(cursor.getString(MovieContract.TRAILER_COL_NAME));
                break;
            default:
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView;
        if (viewType == TYPE_MOVIE){
            rootView = inflater.inflate(R.layout.detail_header,parent,false);
            return new MovieViewHolder(rootView);
        }else if (viewType == TYPE_REVIEW){
            rootView = inflater.inflate(R.layout.review_list_item,parent,false);
            return new ReviewViewHolder(rootView);
        }else if (viewType == TYPE_TRAILER){
            rootView = inflater.inflate(R.layout.trailer_list_item,parent,false);
            return new TrailerViewHolder(rootView);
        }else {
            return null;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle,mTvReleaseDate,mTvVote,mTvOverView,mReviewNote;
        ImageView mImgPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvReleaseDate = (TextView) itemView.findViewById(R.id.tv_release_date);
            mTvVote = (TextView) itemView.findViewById(R.id.tv_vote);
            mTvOverView = (TextView) itemView.findViewById(R.id.tv_overview);
            mImgPoster = (ImageView) itemView.findViewById(R.id.img_poster);
            mReviewNote = (TextView) itemView.findViewById(R.id.tv_reviews_note);
        }
    }
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAuthor,mTvReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mTvReview = (TextView) itemView.findViewById(R.id.tv_review);
        }
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTrailerName;
        VideoView mVvTrailer;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTvTrailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            mVvTrailer = (VideoView) itemView.findViewById(R.id.trailer_video);
        }
    }
}