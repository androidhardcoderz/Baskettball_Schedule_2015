package com.baskettballschedule2015.app;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 10/6/2015.
 */
public class AppPreferences {

    public static final String FAVORITE = "favorite";
    public static final String FAVORITE_SET = "favorite_set";


    public static void setFavoriteTeam(Context context,int index){

        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
                .edit().putInt(FAVORITE,index).apply();

    }

    public static int getFavoriteTeam(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getInt(FAVORITE,0);
    }

    public static boolean isFavoriteTeamSet(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean(FAVORITE_SET, false);
    }

    public static void isFavoriteTeamSetFlag(Context context){
        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit().putBoolean(FAVORITE_SET,true).apply();
    }
}
