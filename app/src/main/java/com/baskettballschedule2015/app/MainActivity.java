package com.baskettballschedule2015.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.appbrain.AppBrain;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FavoriteTeamFragment.ITeamSelected {

    ScheduleLoader loader;
    LoadingFragment loadingFragment;
    String teamNameSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBrain.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView filterImageView = (ImageView) toolbar.findViewById(R.id.filterImageView);
        filterImageView.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //open drawer for first run
        if (PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getBoolean("FIRST", true) == true) {
            //show drawer open it for first time
            drawer.openDrawer(GravityCompat.START);
            PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).edit().putBoolean("FIRST", false).apply();
        }

        if(!AppPreferences.isFavoriteTeamSet(this)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer,new FavoriteTeamFragment()).commit();
        }else{
            //favorite team has been binded already
            teamNameSelected = new TeamNames().getTeamNames().get(AppPreferences.getFavoriteTeam(this));

            showLoadingFragment();
            startBackgroundThread(teamNameSelected);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(!item.getTitle().equals("Change Favorite Team")) {
            teamNameSelected = item.getTitle().toString();

            showLoadingFragment();
            startBackgroundThread(item.getTitle().toString());

            //closes drawer if open still
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer,new FavoriteTeamFragment()).commit();

            //closes drawer
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;

    }

    public void onTeamItemSelected(String item) {

            teamNameSelected = item;
            showLoadingFragment();
            startBackgroundThread(item);
            //closes drawer if open still
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


    }

    private void showTeamFragment(ArrayList<Game> games) {
        TeamFragment teamFragment = new TeamFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TeamFragment.TEAM_DATA, games);
        bundle.putInt(TeamFragment.TEAM_POSITION, new TeamNames().getTeamNames().indexOf(teamNameSelected));
        teamFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, teamFragment).commit();
    }

    private void showLoadingFragment() {
        loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, loadingFragment).commit();
    }

    private void startBackgroundThread(String teamName) {

        Log.i("TAG", " TEAM NAME " + teamName);

        if (loader != null && loader.getStatus() == AsyncTask.Status.RUNNING) {
            loader.cancel(true);
        }
        loader = new ScheduleLoader(this, new TeamNames().getTeamNames().indexOf(teamName));
        loader.execute(teamName);
    }

    @Override
    public void onFavaoriteTeamSelected(int position, String name) {
        Log.i("TAG", "TEAM SELECTED " + name);
        onTeamItemSelected(name);
    }


    class ScheduleLoader extends AsyncTask<String, Void, ArrayList<Game>> {

        Context context;
        int index;

        public ScheduleLoader(Context context, int index) {

            this.context = context;
            this.index = index;

            Log.i("TAG", "INDEX " + index);
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
        protected ArrayList<Game> doInBackground(String... params) {
            DownloadService service = new DownloadService(context, params[0]);
            try {
                TeamFiles teamFiles = new TeamFiles(context);
                return service.parseSpecificTeamData(service.convertInputStreamToString(teamFiles.getFiles().get(index)));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);
            showTeamFragment(games);
        }
    }



}
