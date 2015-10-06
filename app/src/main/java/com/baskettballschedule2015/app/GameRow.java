package com.baskettballschedule2015.app;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Scott on 10/6/2015.
 */
public class GameRow extends LinearLayout {

    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.opponet) TextView opponet;

    public GameRow(Context context,Game game) {
        super(context);

        inflate(context, R.layout.game_row, this);

        ButterKnife.bind(this,this);

        date.setText(game.getDate());
        time.setText(game.getTime());

        if(game.isHome()){
            opponet.setText("VS " + game.getVisitor());
        }else{
            opponet.setText("@ " + game.getHome());
        }

    }
}
