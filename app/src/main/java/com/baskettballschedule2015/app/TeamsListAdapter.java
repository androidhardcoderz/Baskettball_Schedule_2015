package com.baskettballschedule2015.app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Scott on 10/19/2015.
 */
public class TeamsListAdapter extends BaseAdapter {

    Context context;
    TeamNames teamNames;
    TeamColors teamColors;
    TeamFonts teamFonts;
    LayoutInflater inflater;
    public TeamsListAdapter(Context context){
        this.context = context;

        teamNames = new TeamNames();
        teamColors = new TeamColors();
        teamFonts = new TeamFonts(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return teamNames.getTeamNames().size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.team_list_row,null,false);
        }

        TextView name = (TextView) view.findViewById(R.id.teamNameTextView);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.team_row_layout);

        name.setText(teamNames.getTeamNames().get(position));
        teamFonts.setCustomTeamFont(name, position);
        layout.setBackgroundColor(ContextCompat.getColor(context,teamColors.getColors(position)));

        return view;
    }
}
