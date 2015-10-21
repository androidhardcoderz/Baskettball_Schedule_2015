package com.baskettballschedule2015.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Scott on 10/19/2015.
 */
public class FavoriteTeamFragment extends Fragment {

    @Bind(R.id.teamsListView)
    ListView list;

    ITeamSelected mCallback;

    public interface ITeamSelected {
        void onFavaoriteTeamSelected(int position,String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ITeamSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorite_team_fragment,container,false);

        ButterKnife.bind(this,view);

        list.setAdapter(new TeamsListAdapter(getActivity()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AppPreferences.isFavoriteTeamSetFlag(getActivity());
                    AppPreferences.setFavoriteTeam(getActivity(), position);
                mCallback.onFavaoriteTeamSelected(position,new TeamNames().getTeamNames().get(position));
            }
        });

        //filter action bar (toolbar) item retraction
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView filterImageView = (ImageView) toolbar.findViewById(R.id.filterImageView);
        filterImageView.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
}
