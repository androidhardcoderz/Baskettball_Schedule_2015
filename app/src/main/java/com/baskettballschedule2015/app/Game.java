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
    private String date;
    private String time;
    private boolean isHome;

    public Game() {

    }

    protected Game(Parcel in) {
        scheduled = in.readString();
        home = in.readString();
        visitor = in.readString();
        place = in.readString();
        date = in.readString();
        time = in.readString();
        isHome = in.readByte() != 0x00;

    }

    public void determineIsHomeGame(String teamname){
        if(getHome().equals(teamname)){
            isHome = true;
        }else{
            isHome = false;
        }
    }

    public boolean isHome() {
        return isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    public void convertDate() {
        setDate(new FormatGameStartTime().getDateOfGame(getScheduled()));
    }

    public void convertTime() {
        setTime(new FormatGameStartTime().getTimeOfGame(getScheduled()));
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
        dest.writeString(date);
        dest.writeString(time);
        dest.writeByte((byte) (isHome ? 0x01 : 0x00));

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
