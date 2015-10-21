package com.baskettballschedule2015.app;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 10/4/2015.
 */
public class TeamNames {

    public List<String> getTeamNames() {
        return teamNames;
    }

    List<String> teamNames = new ArrayList<>();

    public TeamNames() {
        teamNames.add("Atlanta Hawks");
        teamNames.add("Boston Celtics");
        teamNames.add("Brooklyn Nets");
        teamNames.add("Charlotte Hornets");
        teamNames.add("Chicago Bulls");
        teamNames.add("Cleveland Cavaliers");
        teamNames.add("Dallas Mavericks");
        teamNames.add("Denver Nuggets");
        teamNames.add("Detroit Pistons");
        teamNames.add("Golden State Warriors");
        teamNames.add("Houston Rockets");
        teamNames.add("Indiana Pacers");
        teamNames.add("Los Angeles Clippers");
        teamNames.add("Los Angeles Lakers");
        teamNames.add("Memphis Grizzlies");
        teamNames.add("Miami Heat");
        teamNames.add("Milwaukee Bucks");
        teamNames.add("Minnesota Timberwolves");
        teamNames.add("New Orleans Pelicans");
        teamNames.add("New York Knicks");
        teamNames.add("Oklahoma City Thunder");
        teamNames.add("Orlando Magic");
        teamNames.add("Philadelphia 76ers");
        teamNames.add("Phoenix Suns");
        teamNames.add("Portland Trail Blazers");
        teamNames.add("Sacramento Kings");
        teamNames.add("San Antonio Spurs");
        teamNames.add("Toronto Raptors");
        teamNames.add("Utah Jazz");
        teamNames.add("Washington Wizards");
    }

    public int findTeamIndex(String name){
        Log.i("TEAM NAMES",name);
        return teamNames.indexOf(name);
    }
}
