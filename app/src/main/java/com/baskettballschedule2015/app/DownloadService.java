package com.baskettballschedule2015.app;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DownloadService {


    private static final String TAG = "DownloadService";
    String teamname;
    Context context;

    public DownloadService(Context context,String teamname) {
        this.context = context;
        this.teamname = teamname;
    }
    public String convertInputStreamToString()
            throws IOException {

        InputStreamReader isr = new InputStreamReader(context.getAssets().open("schedule.txt"));
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.i(TAG,line);
        }

		/* Close Stream */
        if (null != isr) {
            isr.close();
        }

        return result;
    }

    public ArrayList<Game> parseSpecificTeamData(String jsonString) throws JSONException {

        ArrayList<Game> games = new ArrayList<>();
        JSONArray gamesArray = new JSONObject(jsonString).getJSONArray("games");

        for( int i = 0; i < gamesArray.length();i++){
            JSONObject gameObject = gamesArray.getJSONObject(i);
            if(gameObject.getString("home").equals(teamname) ||
                    gameObject.getString("away").equals(teamname)){
                Game game = new Game();
                game.setScheduled(gameObject.getString("scheduled"));
                game.setHome(gameObject.getJSONObject("home").getString("name"));
                game.setVisitor(gameObject.getJSONObject("away").getString("name"));
                game.setPlace(gameObject.getJSONObject("venue").getString("name"));

                games.add(game);
            }else{
                continue;
            }
        }

        Log.i(TAG,games.size() + " SIZE OF GAMES" );


        return games;

    }

}
