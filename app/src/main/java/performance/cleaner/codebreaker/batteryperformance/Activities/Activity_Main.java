package performance.cleaner.codebreaker.batteryperformance.Activities;


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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import performance.cleaner.codebreaker.batteryperformance.Fragment_Trigger.Home_Tab;
import performance.cleaner.codebreaker.batteryperformance.Fragments.slide_activity;
import performance.cleaner.codebreaker.batteryperformance.Google_Tracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;
import performance.cleaner.codebreaker.batteryperformance.Services.time_left;

import com.google.android.gms.analytics.HitBuilders;
import com.mobvista.msdk.MobVistaConstans;
import com.mobvista.msdk.MobVistaSDK;
import com.mobvista.msdk.out.MobVistaSDKFactory;
import com.mobvista.msdk.out.MvWallHandler;
import java.util.HashMap;
import java.util.Map;
import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class Activity_Main extends AppCompatActivity
{
    //Google Analytics
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    DrawerLayout battery_performance_DrawerLayout;
    NavigationView battery_performance_NavigationView;
    FragmentManager battery_performance_FragmentManager_1;
    FragmentTransaction battery_performance_FragmentTransaction_1;
    android.support.v7.widget.Toolbar battery_toolbar;

    private Tracker mTracker;

    //Changes Made
    //Changes Made Here
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

        MobVistaSDK sdk = MobVistaSDKFactory.getMobVistaSDK(); Map<String,String> Map = sdk.getMVConfigurationMap("90822","44ddbfa8d4de397c612bdf09e5cb1bf2");
        sdk.init(Map, this);
        /**
         * Setup the Toolbar
         */
         battery_toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_battery);
         setSupportActionBar(battery_toolbar);
         battery_toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

               if(item.getItemId() == R.id.explore)
               {
                   mTracker.send(new HitBuilders.EventBuilder()
                           .setCategory("Mintegral")
                           .setAction("Main Toolbar Button Clicked")
                           .build());
                   openWall();
               }
               return false;
           }
       });


        /**
         *Setup the DrawerLayout and NavigationView
         */

        battery_performance_DrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        battery_performance_NavigationView = (NavigationView) findViewById(R.id.nav_bar);

        /**
         * Lets inflate the very first fragment
         **/

        battery_performance_FragmentManager_1 = getSupportFragmentManager();
        battery_performance_FragmentTransaction_1 = battery_performance_FragmentManager_1.beginTransaction();
        battery_performance_FragmentTransaction_1.replace(R.id.containerView, new Home_Tab()).commit();


        /**
         * Setup click events on the Navigation View Items.
         */

        battery_performance_NavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
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

                    Intent to_cache = new Intent(Activity_Main.this, Cache_Cleaning.class);
                    startActivity(to_cache);
                    battery_performance_DrawerLayout.closeDrawers();
                }

                if (menuItem.getItemId() == R.id.nav_menu_help)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Help")
                            .setAction("Help Button Clicked")
                            .build());
                    Intent help = new Intent(Activity_Main.this, Help.class);
                    startActivity(help);
                }

                if (menuItem.getItemId() == R.id.nav_menu_explore) {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Mintegral")
                            .setAction("Navigation Button Clicked")
                            .build());
                    openWall();
                }


                if (menuItem.getItemId() == R.id.nav_menu_alarm) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Alarm")
                            .setAction("Navigation Alarm Button Clicked")
                            .build());

                    Intent alarm = new Intent(Activity_Main.this, Alarm.class);
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
                    battery_performance_DrawerLayout.closeDrawers();
                    startActivity(new Intent(Activity_Main.this, Rate_me.class));
                }

                if (menuItem.getItemId() == R.id.nav_menu_about) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("About")
                            .setAction("Navigation About Button Clicked")
                            .build());

                    Intent to_about = new Intent(Activity_Main.this, About.class);
                    startActivity(to_about);
                }

                if (menuItem.getItemId() == R.id.nav_menu_exit)
                {
                    finish();
                }
                return false;
            }

        });


        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Intent time_service = new Intent(Activity_Main.this,time_left.class);
        startService(time_service);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_battery);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, battery_performance_DrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        battery_performance_DrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    public void openWall()
    {
        try
        {
            Class<?> aClass = Class
                    .forName("com.mobvista.msdk.shell.MVActivity");
            Intent intent = new Intent(this, aClass);
            intent.putExtra(MobVistaConstans.PROPERTIES_UNIT_ID, "14445");   //Unit Id
            this.startActivity(intent);
        } catch (Exception e) {
            Log.e("MVActivity", "", e);
        }
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
        preloadWall();
        loadHandler();

        mTracker.setScreenName("Activity_Main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void preloadWall() {
        MobVistaSDK sdk = MobVistaSDKFactory.getMobVistaSDK();
        Map<String, Object> preloadMap = new HashMap<String, Object>();
        preloadMap.put(MobVistaConstans.PROPERTIES_LAYOUT_TYPE, MobVistaConstans.LAYOUT_APPWALL);
        preloadMap.put(MobVistaConstans.PROPERTIES_UNIT_ID, "14445");
        sdk.preload(preloadMap);
    }

    public void loadHandler()
    {
        Map<String, Object> properties = MvWallHandler.getWallProperties("14445");
        properties.put(MobVistaConstans.PROPERTIES_WALL_STATUS_COLOR, R.color.mobvista_facebook);
        properties.put(MobVistaConstans.PROPERTIES_WALL_NAVIGATION_COLOR, R.color.mobvista_facebook);
        properties.put(MobVistaConstans.PROPERTIES_WALL_TITLE_BACKGROUND_COLOR, R.color.mobvista_facebook);
        MvWallHandler mvHandler = new MvWallHandler(properties, this);

        mvHandler.load();
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