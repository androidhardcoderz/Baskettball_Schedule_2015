package com.baskettballschedule2015.app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Scott on 10/6/2015.
 */
public class MonthLayout extends LinearLayoutCompat {
    @Bind(R.id.monthNameTextView)
    TextView monthNameTextView;

    @Bind(R.id.gamesLinearLayout)
    LinearLayout layout;

    public MonthLayout(Context context,List<Game> games,String monthName,String teamName) {
        super(context);

        inflate(getContext(), R.layout.month_layout, this);

        monthNameTextView.setText(monthName);

        int index = new TeamNames().getTeamNames().indexOf(teamName);

        layout.setBackgroundColor(ContextCompat.getColor(getContext(),new TeamColors().getColors(index)));

        for(Game game: games){
            layout.addView(new GameRow(getContext(),game,0));
        }
    }
}
