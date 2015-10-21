package com.baskettballschedule2015.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 10/6/2015.
 */
public class Months {

    private String name;

    public Months(){
        monthGames = new ArrayList<>();
    }

    public List<Game> getMonthGames() {
        return monthGames;
    }

    public void setMonthGames(List<Game> monthGames) {
        this.monthGames = monthGames;
    }

    private List<Game> monthGames = new ArrayList<>();

    public int findMonthNumeric(String month){

        if(month.equals("October")){
            return 10;
        }else if(month.equals("November")){
            return 11;
        }
        else if(month.equals("December")){
            return 12;
        }
        else if(month.equals("January")){
            return 01;
        }
        else if(month.equals("February")){
            return 02;
        }
        else if(month.equals("March")){
            return 03;
        }
        else if(month.equals("April")){
            return 04;
        }

        return 0;
    }

    /*
    public int findMonthNumeric(int month){


        if(month.equals("October")){
            return 10;
        }else if(month.equals("November")){
            return 11;
        }
        else if(month.equals("December")){
            return 12;
        }
        else if(month.equals("January")){
            return 01;
        }
        else if(month.equals("February")){
            return 02;
        }
        else if(month.equals("March")){
            return 03;
        }
        else if(month.equals("April")){
            return 04;
        }

        return 0;
    }

    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
