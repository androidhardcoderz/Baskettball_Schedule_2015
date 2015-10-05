package com.baskettballschedule2015.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 10/4/2015.
 */
public class Game implements Parcelable {

    @SuppressWarnings("unused")
    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    private String scheduled;
    private String home;
    private String visitor;
    private String place;

    public Game() {

    }

    protected Game(Parcel in) {
        scheduled = in.readString();
        home = in.readString();
        visitor = in.readString();
        place = in.readString();
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(scheduled);
        dest.writeString(home);
        dest.writeString(visitor);
        dest.writeString(place);
    }
}
