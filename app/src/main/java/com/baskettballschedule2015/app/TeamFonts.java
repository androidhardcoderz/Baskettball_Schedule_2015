package com.baskettballschedule2015.app;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Scott on 10/19/2015.
 */
public class TeamFonts {

    private final String TAG = "TeamFonts";
    String [] list;

    public TeamFonts(Context context){

        String path = "team_fonts";

        try {
            list = context.getAssets().list(path);
        } catch (IOException e) {

        }
    }

    public void setCustomTeamFont(TextView tv,int index){

        Log.i(TAG,list[index]);
        Typeface tf = Typeface.createFromAsset(tv.getContext()
                .getAssets(), "team_fonts/" + list[index]);

        tv.setTypeface(tf);
    }

    public void setCustomNotoSansTamilFont(TextView textView,int index){
        Typeface tf = Typeface.createFromAsset(textView.getContext()
                .getAssets(), "team_fonts/" + list[index]);

        textView.setTypeface(tf);
    }


}
