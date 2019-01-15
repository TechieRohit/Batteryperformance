package performance.cleaner.codebreaker.batteryperformance.fragments;

/**
 * Created by J!MMY on 12/10/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import performance.cleaner.codebreaker.batteryperformance.activities.Cache_Cleaning;
import performance.cleaner.codebreaker.batteryperformance.fragmenttriggers.Trigger1;
import performance.cleaner.codebreaker.batteryperformance.fragmenttriggers.Trigger2;
import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;
import performance.cleaner.codebreaker.batteryperformance.service.SinceLastChargedService;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.UnitPosition;
import performance.cleaner.codebreaker.batteryperformance.utils.HealthClass;

/**
 * Created by J!MMY on 12/9/2016.
 */
public class MainHomeFragment extends Fragment
{

    private TextView time_left_lev;
    private TextView time_left_type;
    private TextView time_since_last_charged;
    private TextView time_since_last_charged_1;
    private TextView battery_level;

    int level_global;
    double temp_global;

    long output_charging_hour;
    long output_charging_min_actual;

    long output_discharging_min;
    long output_discharging_hour;

    private LinearLayout To_tabs_1;
    private LinearLayout To_cache;
    private LinearLayout To_tabs_2;
   // public static final int Unique_id = 58585;

    FragmentManager battery_performance_FragmentManager_2;
    FragmentTransaction battery_performance_FragmentTransaction_2;

    //For Progress View
    CircleProgressView StatusCircleView;

    Handler tab_delay;

    private Tracker mTracker;

    long time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View slide_view = inflater.inflate(R.layout.slide_activity, container, false);

      /**   For Tracking     **/
        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        /** --------  **/

        SharedPreferences time_output = PreferenceManager.getDefaultSharedPreferences(getActivity());
         time = time_output.getLong("time_output", 0);


        registerBatteryPerformanceReceiver();
        
        //Binding the text views
        battery_level = (TextView)slide_view. findViewById(R.id.status);
        time_left_lev = (TextView)slide_view. findViewById(R.id.time_left);
        time_since_last_charged = (TextView)slide_view. findViewById(R.id.time_since_last_charged);
        time_left_type = (TextView)slide_view. findViewById(R.id.time_left_1);
        time_since_last_charged_1 = (TextView)slide_view. findViewById(R.id.time_since_last_charged_1);

        time_since_last_charged.setText("" + time + " Minutes");

        //Setting Up The ProgressCircle
        StatusCircleView = (CircleProgressView)slide_view. findViewById(R.id.circleView);
        StatusCircleView.setMaxValue(100);
        StatusCircleView.setTextSize(45);
        StatusCircleView.setUnitVisible(true);
        StatusCircleView.setUnitPosition(UnitPosition.TOP);
        StatusCircleView.setUnit("Battery Status");
        StatusCircleView.setUnitSize(22);

        //Setting up side_fragments
        To_tabs_1 = (LinearLayout)slide_view. findViewById(R.id.side_fragments_1);

        To_tabs_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tab_delay = new Handler();
                tab_delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("To Tabs")
                                .setAction("User is now into slide activity")
                                .build());

                        battery_performance_FragmentManager_2 = getActivity().getSupportFragmentManager();
                        battery_performance_FragmentTransaction_2 = battery_performance_FragmentManager_2.beginTransaction();
                        battery_performance_FragmentTransaction_2.replace(R.id.containerView, new Trigger1()).addToBackStack(null).commit();
                    }
                }, 500);// .5 sec
            }
        });

        To_cache = (LinearLayout)slide_view. findViewById(R.id.Cache_Clean);

        To_cache.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tab_delay = new Handler();
                tab_delay.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Cache Cleaning")
                                .setAction("User is now into cache cleaning activity")
                                .build());

                        Intent to_cachecleaning = new Intent(getActivity(), Cache_Cleaning.class);
                        startActivity(to_cachecleaning);
                    }
                },500);// .5 sec
            }
        });


        To_tabs_2 = (LinearLayout)slide_view. findViewById(R.id.side_fragment_2);

        To_tabs_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_delay = new Handler();
                tab_delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("To Tabs")
                                .setAction("User is now into slide activity")
                                .build());

                        battery_performance_FragmentManager_2 = getActivity().getSupportFragmentManager();
                        battery_performance_FragmentTransaction_2 = battery_performance_FragmentManager_2.beginTransaction();
                        battery_performance_FragmentTransaction_2.replace(R.id.containerView, new Trigger2()).addToBackStack(null).commit();
                    }
                }, 500);// .5 sec
            }
        });


        Typeface gogoio_regular = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gogoio_regular.otf");
        Typeface canaro = Typeface.createFromAsset(getActivity().getAssets(),"commercial/Canaro-LightDEMO.otf");

        time_since_last_charged.setTypeface(gogoio_regular);
        time_left_lev.setTypeface(gogoio_regular);
        time_since_last_charged_1.setTypeface(canaro);
        time_left_type.setTypeface(canaro);

        Intent start = new Intent( getActivity(),SinceLastChargedService.class);
        getActivity().startService(start);

        //Here this function will register battery_performance_receiver

        return slide_view;
    }


    //A function to register battery_performance_receiver
    private void registerBatteryPerformanceReceiver()
    {
        //WHAT IS THIS IntentFilter?
        //Used to jump from one activity to another
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //code changed from battery_receiver to battery_performance_receiver
        getActivity().registerReceiver(battery_performance_receiver, filter);  //WHY WE HAVE USE FILTER?
    }

    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int plugged = intent.getIntExtra("plugged", -1);
            level_global = level;
            int temperature = intent.getIntExtra("temperature", 0);
            double temp = (double) temperature / 10;
            temp_global = temp;

            StatusCircleView.setValueAnimated(level_global);
            HealthClass heath = new HealthClass();

            int status = intent.getIntExtra("status", 0);

            /** For Time Since Last Charged **/
            SharedPreferences time_output = PreferenceManager.getDefaultSharedPreferences(context);
            /** For Discharging time **/
            SharedPreferences time_output_mins = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences time_output_hours = PreferenceManager.getDefaultSharedPreferences(context);
            /** For Charging time **/
            SharedPreferences char_mins = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences char_hours = PreferenceManager.getDefaultSharedPreferences(context);

            long time_2 = time_output.getLong("time_output", 0);

            /**FOR CALCULATING TIME SINCE LAST CHARGED **/
            if (plugged == 1 || plugged == 2)
            {
                time_since_last_charged.setText("Charging");
            }
            else
            {
                if (time_2 > 10000)
                {
                    time_since_last_charged.setText("Plug 2-3 times to calculate");
                }
                else {

                    time_since_last_charged.setText(" " + time_2 + " Minutes");
                }
            }
            /**----------------------------------------------------------------------**/


             output_discharging_min = time_output_mins.getLong("dis_minutes", 0);
             output_discharging_hour = time_output_hours.getLong("dis_hours", 0);

             output_charging_min_actual = char_mins.getLong("char_minutes", 0 );
             output_charging_hour = char_hours.getLong("char_hours", 0 );

            //--------------------------------------MAIN ALGORITHM FOR TIME LEFT-----------------------------------//
            //CHARGING
            if (status == 2)
            {

                time_left_type.setText("Charging Time Left");

                if ( output_charging_hour > 10 || (output_charging_min_actual == 0 && output_charging_hour == 0) )
                {
                    time_left_lev.setText("Calculating...");
                }
                else {
                    if ( level <30 && output_charging_hour <=1 )
                    {
                        time_left_lev.setText("Calculating...");
                    }

                    else
                    {
                        time_left_lev.setText("About " + output_charging_hour + " hour " + output_charging_min_actual + " min");
                    }
                }
            }

            //DISCHARGING
            if (status != 2)
            {

                time_left_type.setText("Battery Time Left");

                if (output_discharging_hour > 18 || (output_discharging_hour == 0 && output_discharging_min == 0) )
                {
                    time_left_lev.setText("Calculating...");
                }
                else {

                    if (level>15 && output_discharging_hour < 1 )
                    {
                        time_left_lev.setText("Calculating...");
                    } else
                    {
                        time_left_lev.setText("About " + output_discharging_hour + " hour " + output_discharging_min + " min");
                    }

                }
            }
            //----------------------------------------------------------------------------------------------------//
        }


    };

    @Override
    public void onResume() {

        super.onResume();

       // Log.i(TAG, "Setting screen name: ");
        mTracker.setScreenName("MainHomeFragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroy()
    {
        getActivity().unregisterReceiver(battery_performance_receiver);
        super.onDestroy();
    }

}
