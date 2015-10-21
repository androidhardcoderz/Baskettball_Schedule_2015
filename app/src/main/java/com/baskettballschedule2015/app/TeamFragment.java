package com.baskettballschedule2015.app;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbrain.AppBrainBanner;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Scott on 10/5/2015.
 */
public class TeamFragment extends Fragment {

    public static final String TEAM_DATA = "team_data";
    public static final String TEAM_POSITION = "team_position";
    ArrayList<Game> games = new ArrayList<>();
    @Bind(R.id.teamGamesLayout)
    LinearLayout mainLayout;
    @Bind(R.id.teamHeaderLayout)
    RelativeLayout headerLayout;
    @Bind(R.id.teamNameTextView)
    TextView teamNameTextView;
    @Bind(R.id.adLayout)
    CoordinatorLayout adLayout;
    @Bind(R.id.scrollView)
    ScrollView scrollV;
    int position;
    ValueAnimator colorAnimation;

    ArrayList<Months> months;

    //background classes
    AwayGamesFilterTask awayFilterTask;
    HomeGamesFilterTask homeFilterTask;
    MonthGamesFilterTask monthFilterTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(TEAM_DATA)) {
            games = getArguments().getParcelableArrayList(TEAM_DATA);
        }

        if (getArguments().containsKey(TEAM_POSITION))
            position = getArguments().getInt(TEAM_POSITION);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_fragment, container, false);

        ButterKnife.bind(this, view); //attach butterknife xml resources to view

        setUpdateColorAnimator();
        teamNameTextView.setText(new TeamNames().getTeamNames().get(position));
        new TeamFonts(getActivity()).setCustomTeamFont(teamNameTextView,position);

        //filter action bar (toolbar) item addition
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView filterImageView = (ImageView) toolbar.findViewById(R.id.filterImageView);
        filterImageView.setVisibility(View.VISIBLE);
        filterImageView.setOnClickListener(new FilterGames());

        addGamesToLayout(games);

       // mainLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),new TeamColors().getColors(position)));

        return view;
    }

    public void addBanner(ViewGroup parent, int index) {

        AppBrainBanner banner = new AppBrainBanner(getContext());
        parent.addView(banner,index);
    }

    private void addGamesToLayout(ArrayList<Game> games) {

        for ( Game game : games) {

            GameRow gRow = new GameRow(getActivity(),game,position);
            gRow.setAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left ));
            Animation anim = gRow.getAnimation();
            anim.setDuration(1000 + (games.indexOf(game) * 25));
            mainLayout.addView(gRow, mainLayout.getChildCount());
            gRow.startAnimation(anim);
        }

        int rInt = new Random().nextInt(mainLayout.getChildCount());

        if(rInt < 3){
            rInt += 5;
        }else if(rInt > mainLayout.getChildCount() - 3){
            rInt -= 10;
        }else{

        }
        addBanner(mainLayout,rInt);

        scrollV.smoothScrollTo(0,0);
        //mainLayout.addView(new GameRow(getActivity(),game),mainLayout.getChildCount());
    }



    private void startAwayFilterTask() {

        if (awayFilterTask != null && awayFilterTask.getStatus() == AsyncTask.Status.RUNNING) {
            awayFilterTask.cancel(true);
        }

        awayFilterTask = new AwayGamesFilterTask();
        awayFilterTask.execute(games);
    }

    private void startHomeFilterTask() {
        if (homeFilterTask != null && homeFilterTask.getStatus() == AsyncTask.Status.RUNNING) {
            homeFilterTask.cancel(true);
        }

        homeFilterTask = new HomeGamesFilterTask();
        homeFilterTask.execute(games);
    }

    private void resetAllGames() {
        int totalViews = mainLayout.getChildCount();
        Log.i("TAG", totalViews + " TOTAL VIEWS");
        mainLayout.removeViewsInLayout(1, totalViews - 1);
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

    private void showMonthSelector() {
        //show display with month names
        new BottomSheet.Builder(getActivity())
                .setSheet(R.menu.month_menu)
                .setTitle(R.string.select_months)
                .setListener(new FilterByMonths())
                .show();

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
                    .setCancelable(true)
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
                    showMonthSelector();
                    break;
                case R.id.all_games:
                    resetAllGames();
                    addGamesToLayout(games);
                default:
                    break;
            }

            Log.i("TAG", menuItem.getTitle() + " CLICKED");
        }

        @Override
        public void onSheetDismissed(int i) {


        }
    }

    class FilterByMonths implements BottomSheetListener {

        @Override
        public void onSheetShown() {

        }

        @Override
        public void onSheetItemSelected(MenuItem menuItem) {
            resetAllGames();
            if (monthFilterTask != null && monthFilterTask.getStatus() == AsyncTask.Status.RUNNING) {
                monthFilterTask.cancel(true);
            }
            new MonthGamesFilterTask(new Months()
                    .findMonthNumeric(menuItem.getTitle().toString())).execute(games);
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
            if(games.size() == 0 || games == null){
                showErrorSnackBar();

            }else{
                addGamesToLayout(games);
            }
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
            if(games.size() == 0 || games == null){
                showErrorSnackBar();

            }else{
                addGamesToLayout(games);
            }
        }
    }

    class MonthGamesFilterTask extends AsyncTask<ArrayList<Game>, Void, ArrayList<Game>> {

        int month;

        public MonthGamesFilterTask(int month) {
            this.month = month;
        }

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
                if (game.getMonth() == month) {
                    games.add(game);
                }
            }

            return games;
        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);

            if(games.size() == 0 || games == null){
                    showErrorSnackBar();

            }else{
                addGamesToLayout(games);
            }

        }
    }

    private void showErrorSnackBar(){
        Snackbar.make(adLayout, "No Games Found, Showing All Remaining Games", Snackbar.LENGTH_LONG).show();
        addGamesToLayout(games);
    }


}
