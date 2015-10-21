package com.baskettballschedule2015.app;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DownloadService {


    private static final String TAG = "DownloadService";

    private final String SCHEDULED = "scheduled";
    private final String HOME = "home";
    private final String VISITOR = "visitor";
    private final String VENUE = "venue";

    String teamname;
    Context context;

    public DownloadService(Context context, String teamname) {
        this.context = context;
        this.teamname = teamname;
    }

    public String convertInputStreamToString(String filename)
            throws IOException {

        InputStreamReader isr = new InputStreamReader(context.getAssets().open("team_files/" + filename));
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.i(TAG, line);

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


        Log.i(TAG, "STARTING PARSING!");
        Log.i(TAG, gamesArray.length() + " LENGTH OF ARRAY FOR TEAM");

        for (int i = 0; i < gamesArray.length(); i++) {
            
            JSONObject gameObject = gamesArray.getJSONObject(i);

            if(new FormatGameStartTime().isGameUpcoming(gameObject.getString("scheduled")) == -1){
                continue;
            }

            Game game = new Game();
            game.setScheduled(gameObject.getString("scheduled"));
            game.setHome(gameObject.getString("home"));
            game.setVisitor(gameObject.getString("visitor"));
            game.setPlace(gameObject.getString("venue"));

            game.convertDate();
            game.convertTime();
            game.determineIsHomeGame(teamname);
            game.convertMonth();
            games.add(game);
        }

        return games;
    }

    //used to build json files for each team from main schedule file
    public void mCreateAndSaveFile(String fileName, String mJsonResponse) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mJsonResponse);
            fileWriter.flush();
            fileWriter.close();
            Log.i(TAG, "file written for: " + fileName + " " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
