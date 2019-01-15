package performance.cleaner.codebreaker.batteryperformance.activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import performance.cleaner.codebreaker.batteryperformance.fragmenttriggers.Home_Tab;
import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;
import performance.cleaner.codebreaker.batteryperformance.service.TimeLeftService;
import performance.cleaner.codebreaker.batteryperformance.utils.Constants;

import com.google.android.gms.analytics.HitBuilders;
import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity
{
    //Google Analytics
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    DrawerLayout batteryPerformanceDrawerLayout;
    NavigationView batteryPerformanceNavigationView;
    FragmentManager batteryPerformanceFragmentManager;
    FragmentTransaction batteryPerformanceFragmentTransaction;
    android.support.v7.widget.Toolbar toolbar;

    private Tracker mTracker;

    AnalyticsApplication application = (AnalyticsApplication) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Google analytics
        sAnalytics = GoogleAnalytics.getInstance(this);

        application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        TextView toolbar_tmain;
        toolbar_tmain = (TextView)findViewById(R.id.toolbar_title_m);

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");
        toolbar_tmain.setTypeface(canaro);

        /**
         * Setup the Toolbar
         */
         toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_battery);
         setSupportActionBar(toolbar);
        /* toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

               if(item.getItemId() == R.id.explore)
               {
                   mTracker.send(new HitBuilders.EventBuilder()
                           .setCategory("Mintegral")
                           .setAction("Main Toolbar Button Clicked")
                           .build());
               }
               return false;
           }
       });*/


        setNavigationDrawer();
        onClicks();

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Intent time_service = new Intent(MainActivity.this,TimeLeftService.class);
        startService(time_service);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_battery);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, batteryPerformanceDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        batteryPerformanceDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        setNavigationDrawer();
    }

    private void onClicks() {
        batteryPerformanceNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            Intent graph = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            Intent power_saver = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {

                if (menuItem.getItemId() == R.id.nav_menu_clean)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Cache Cleaning")
                            .setAction("Cache pop up is opened up by the user")
                            .build());

                    Intent to_cache = new Intent(MainActivity.this, Cache_Cleaning.class);
                    startActivity(to_cache);
                    batteryPerformanceDrawerLayout.closeDrawers();
                }

                if (menuItem.getItemId() == R.id.nav_menu_help)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Help")
                            .setAction("Help Button Clicked")
                            .build());
                    Intent help = new Intent(MainActivity.this, Help.class);
                    startActivity(help);
                }

                if (menuItem.getItemId() == R.id.nav_menu_explore) {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Mintegral")
                            .setAction("Navigation Button Clicked")
                            .build());
                }


                if (menuItem.getItemId() == R.id.nav_menu_alarm) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Alarm")
                            .setAction("Navigation Alarm Button Clicked")
                            .build());

                    Intent alarm = new Intent(MainActivity.this, Alarm.class);
                    startActivity(alarm);
                }

                if (menuItem.getItemId() == R.id.nav_menu_graph)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Monitor")
                            .setAction("Navigation Monitor Button Clicked")
                            .build());

                    startActivity(graph);
                }

                if (menuItem.getItemId() == R.id.nav_menu_power_saver)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Power Saver")
                            .setAction("Phone Battery is put under power saving mode")
                            .build());

                    startActivity(power_saver);
                }

                if (menuItem.getItemId() == R.id.nav_share)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Share")
                            .setAction("Share Clicked")
                            .build());

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Battery Performance + Cache Cleaner: https://play.google.com/store/apps/details?id=performance.cleaner.codebreaker.batteryperformance");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

                if (menuItem.getItemId() == R.id.nav_rate)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Rate Me")
                            .setAction("Rate me Clicked")
                            .build());
                    batteryPerformanceDrawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, RateIt.class));
                }

                if (menuItem.getItemId() == R.id.nav_menu_about) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("About")
                            .setAction("Navigation About Button Clicked")
                            .build());

                    Intent to_about = new Intent(MainActivity.this, About.class);
                    startActivity(to_about);
                }

                if (menuItem.getItemId() == R.id.nav_menu_exit)
                {
                    finish();
                }
                return false;
            }

        });
    }

    private void setNavigationDrawer() {
        /**
         *Setup the DrawerLayout and NavigationView
         */

        batteryPerformanceDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        batteryPerformanceNavigationView = (NavigationView) findViewById(R.id.nav_bar);

        /**
         * Lets inflate the very first fragment
         **/

        batteryPerformanceFragmentManager = getSupportFragmentManager();
        batteryPerformanceFragmentTransaction = batteryPerformanceFragmentManager.beginTransaction();
        batteryPerformanceFragmentTransaction.replace(R.id.containerView, new Home_Tab()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName(Constants.ScreenName.MAIN_ACTIVITY);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker()
    {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }
        return sTracker;
    }

}