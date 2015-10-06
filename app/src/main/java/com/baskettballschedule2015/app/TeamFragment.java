package com.baskettballschedule2015.app;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appnext.appnextsdk.API.AppnextAPI;
import com.appnext.appnextsdk.API.AppnextAd;
import com.appnext.appnextsdk.API.AppnextAdRequest;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Scott on 10/5/2015.
 */
public class TeamFragment extends Fragment {

    public static final String TEAM_DATA = "team_data";
    public static final String TEAM_POSITION = "team_position";
    protected ArrayList<AppnextAd> adsList;
    ArrayList<Game> games = new ArrayList<>();
    @Bind(R.id.teamGamesLayout)
    LinearLayout mainLayout;
    @Bind(R.id.teamHeaderLayout)
    RelativeLayout headerLayout;
    @Bind(R.id.teamNameTextView)
    TextView teamNameTextView;
    @Bind(R.id.adLayout)
    CoordinatorLayout adLayout;
    int position;
    ValueAnimator colorAnimation;
    AppnextAPI api;
    AwayGamesFilterTask awayFilterTask;
    HomeGamesFilterTask homeFilterTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (getArguments().containsKey(TEAM_DATA)) {
            games = getArguments().getParcelableArrayList(TEAM_DATA);
        }

        if (getArguments().containsKey(TEAM_POSITION))
            position = getArguments().getInt(TEAM_POSITION);

        api = new AppnextAPI(getActivity(), "fc9291ef-f541-41cf-84d0-46d73ffa710c");
        api.setAdListener(new AppnextAPI.AppnextAdListener() {
            @Override
            public void onError(String error) {
            }

            @Override
            public void onAdsLoaded(ArrayList<AppnextAd> ads) {

            }
        });
        api.loadAds(new AppnextAdRequest());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_fragment, container, false);

        ButterKnife.bind(this, view); //attach butterknife xml resources to view

        setUpdateColorAnimator();
        teamNameTextView.setText(new TeamNames().getTeamNames().get(position));

        if (AppPreferences.getFavoriteTeam(getActivity()) != position) {
            FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.myFAB);
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //snack bar message
                    //save team as favorite
                    Snackbar
                            .make(adLayout, teamNameTextView.getText().toString() + " Set As Favorite Team", Snackbar.LENGTH_LONG)
                            .show(); // Don’t forget to show!
                    AppPreferences.setFavoriteTeam(getActivity(), position);
                }
            });
        }

        addGamesToLayout(games);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView filterImageView = (ImageView) toolbar.findViewById(R.id.filterImageView);
        filterImageView.setVisibility(View.VISIBLE);
        filterImageView.setOnClickListener(new FilterGames());

        return view;
    }

    private void addGamesToLayout(ArrayList<Game> games) {
        for (Game game : games) {
            mainLayout.addView(new GameRow(getActivity(), game), mainLayout.getChildCount());
        }
    }

    private void startAwayFilterTask() {

        if (awayFilterTask != null && awayFilterTask.getStatus() == AsyncTask.Status.RUNNING) {
            awayFilterTask.cancel(true);
        }

        awayFilterTask = new AwayGamesFilterTask();
        awayFilterTask.execute(games);
    }

    private void startHomeFilterTask(){
        if(homeFilterTask != null && homeFilterTask.getStatus() == AsyncTask.Status.RUNNING){
            homeFilterTask.cancel(true);
        }

        homeFilterTask = new HomeGamesFilterTask();
        homeFilterTask.execute(games);
    }

    private void resetAllGames() {
        int totalViews = mainLayout.getChildCount();
        Log.i("TAG",totalViews + " TOTAL VIEWS");
        mainLayout.removeViewsInLayout(1,totalViews - 1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorAnimation.start(); //start animation for header view
    }

    private void setUpdateColorAnimator() {

        Integer colorFrom = (Color.WHITE);
        Integer colorTo = ContextCompat.getColor(getActivity(), new TeamColors().getColors(position));
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(2500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                headerLayout.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });

    }

    class FilterGames implements View.OnClickListener {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Log.i("TAG", "filter clicked!");
            new BottomSheet.Builder(getActivity())
                    .setSheet(R.menu.filter_menu)
                    .setTitle(R.string.filter_title)
                    .setListener(new FilterByListener())
                    .show();
        }
    }

    class FilterByListener implements BottomSheetListener {

        @Override
        public void onSheetShown() {

        }

        @Override
        public void onSheetItemSelected(MenuItem menuItem) {
            int id = menuItem.getItemId();
            switch (id) {
                case R.id.away_games:
                    resetAllGames();
                    startAwayFilterTask();
                    break;
                case R.id.home_games:
                    resetAllGames();
                    startHomeFilterTask();
                    break;
                case R.id.month:
                    break;
                case R.id.teams:
                    break;
                default:
                    break;
            }

            Log.i("TAG", menuItem.getTitle() + " CLICKED");
        }

        @Override
        public void onSheetDismissed(int i) {

        }
    }

    class AwayGamesFilterTask extends AsyncTask<ArrayList<Game>, Void, ArrayList<Game>> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<Game> doInBackground(ArrayList<Game>... params) {
            ArrayList<Game> games = new ArrayList<>();

            for (Game game : params[0]) {
                if (!game.isHome()) {
                    games.add(game);
                }
            }

            return games;
        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);
            addGamesToLayout(games);
        }
    }

    class HomeGamesFilterTask extends AsyncTask<ArrayList<Game>, Void, ArrayList<Game>> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<Game> doInBackground(ArrayList<Game>... params) {
            ArrayList<Game> games = new ArrayList<>();

            for (Game game : params[0]) {
                if (game.isHome()) {
                    games.add(game);
                }
            }

            return games;
        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);

            addGamesToLayout(games);
        }
    }


}
