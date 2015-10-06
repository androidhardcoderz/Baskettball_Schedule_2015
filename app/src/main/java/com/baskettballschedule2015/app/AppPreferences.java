package com.baskettballschedule2015.app;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 10/6/2015.
 */
public class AppPreferences {

    public static final String FAVORITE = "favorite";


    public static void setFavoriteTeam(Context context,int index){

        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
                .edit().putInt(FAVORITE,index).apply();

    }

    public static int getFavoriteTeam(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getInt(FAVORITE,0);
    }
}
