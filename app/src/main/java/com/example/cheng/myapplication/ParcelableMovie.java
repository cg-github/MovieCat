package com.example.cheng.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cheng.myapplication.util.CommonUtil;

import java.util.HashMap;

/**
 * Created by cheng on 2016/12/21.
 */

public class ParcelableMovie implements Parcelable {
    private String title;
    private String posterPath;
    private String vote;
    private String overView;
    private String releaseData;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(vote);
        parcel.writeString(overView);
        parcel.writeString(releaseData);
    }

    public static final Parcelable.Creator<ParcelableMovie> CREATOR = new Creator<ParcelableMovie>() {
        @Override
        public ParcelableMovie createFromParcel(Parcel parcel) {
            ParcelableMovie pMovie = new ParcelableMovie();
            pMovie.setTitle(parcel.readString());
            pMovie.setPosterPath(parcel.readString());
            pMovie.setVote(parcel.readString());
            pMovie.setOverView(parcel.readString());
            pMovie.setReleaseData(parcel.readString());
            return pMovie;
        }

        @Override
        public ParcelableMovie[] newArray(int i) {
            return new ParcelableMovie[0];
        }
    };

    public ParcelableMovie() {
    }

    public ParcelableMovie(HashMap<String,String> map){
        this.title = map.get(CommonUtil.KEY_MOVIE_TITLE);
        this.posterPath = map.get(CommonUtil.KEY_MOVIE_POSTER_PATE);
        this.vote = map.get(CommonUtil.KEY_MOVIE_VOTE_AVERAGE);
        this.overView = map.get(CommonUtil.KEY_MOVIE_OVERVIEW);
        this.releaseData = map.get(CommonUtil.KEY_MOVIE_RELEASE_DATE);
    }

    public ParcelableMovie(String title, String posterPath, String vote, String overView, String releaseData) {
        this.title = title;
        this.posterPath = posterPath;
        this.vote = vote;
        this.overView = overView;
        this.releaseData = releaseData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }
}
