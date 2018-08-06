package com.prakashgelani.newupcomingmovies.modal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Planet on 9/19/2017.
 */

public class ListItemModal implements  Parcelable{




    public ListItemModal() {

    }


    protected ListItemModal(Parcel in) {
        strVoteAverage = in.readString();
        strTitle = in.readString();
        strImgPosterPath = in.readString();
        strOverview = in.readString();
        strReleaseDate = in.readString();
        strAdult = in.readString();
    }

    public static final Creator<ListItemModal> CREATOR = new Creator<ListItemModal>() {
        @Override
        public ListItemModal createFromParcel(Parcel in) {
            return new ListItemModal(in);
        }

        @Override
        public ListItemModal[] newArray(int size) {
            return new ListItemModal[size];
        }
    };

    public String getStrVoteAverage() {
        return strVoteAverage;
    }

    public void setStrVoteAverage(String strVoteAverage) {
        this.strVoteAverage = strVoteAverage;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrImgPosterPath() {
        return strImgPosterPath;
    }

    public void setStrImgPosterPath(String strImgPosterPath) {
        this.strImgPosterPath = strImgPosterPath;
    }

    public String getStrOverview() {
        return strOverview;
    }

    public void setStrOverview(String strOverview) {
        this.strOverview = strOverview;
    }

    public String getStrReleaseDate() {
        return strReleaseDate;
    }

    public void setStrReleaseDate(String strReleaseDate) {
        this.strReleaseDate = strReleaseDate;
    }

    public String getStrAdult() {
        return strAdult;
    }

    public void setStrAdult(String strAdult) {
        this.strAdult = strAdult;
    }

    public String strVoteAverage;
    public String strTitle;
    public String strImgPosterPath;
    public String strOverview;
    public String strReleaseDate;
    public String strAdult;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strVoteAverage);
        dest.writeString(strTitle);
        dest.writeString(strImgPosterPath);
        dest.writeString(strOverview);
        dest.writeString(strReleaseDate);
        dest.writeString(strAdult);
    }
}
