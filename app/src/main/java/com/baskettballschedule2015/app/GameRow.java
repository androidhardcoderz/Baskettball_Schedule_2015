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

    int tIndex;

    public GameRow(Context context,Game game,int tIndex) {
        super(context);

        setWillNotDraw(false) ;

        this.tIndex = tIndex;

        inflate(context, R.layout.game_row, this);

        ButterKnife.bind(this, this);

        date.setText(game.getDate());
        time.setText(game.getTime());

        if(game.isHome()){
            opponet.setText("VS " + game.getVisitor());
            String[] split = opponet.getText().toString().split(" ");
            opponet.setText("VS " +split[split.length -1]);
        }else{
            opponet.setText("@ " + game.getHome());
            String[] split = opponet.getText().toString().split(" ");
            opponet.setText("@ " + split[split.length -1]);
        }

        //sets custom fonts external /fonts folder
        new Fonts().setFontRoboto_BlackItalic(date);
        new Fonts().setCustomNotoSansTamilFont(time);
        new Fonts().setCustomNotoSansTamilFont(opponet);
        //new Fonts().setCustomNotoSansTamilFont(date);
    }

    private void setTeamFontForOpponet(String name){
        TeamNames teamNames =  new TeamNames();
        TeamFonts teamFonts = new TeamFonts(getContext());
        teamFonts.setCustomNotoSansTamilFont(opponet,teamNames.findTeamIndex(name));
    }
}
