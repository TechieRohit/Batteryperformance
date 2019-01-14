package performance.cleaner.codebreaker.batteryperformance.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import performance.cleaner.codebreaker.batteryperformance.Google_Tracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class slide_activity_2 extends Fragment {


    private TextView Plugged_type;
    private TextView Battery_status;
    private TextView health_lev;
    private TextView monitor;

    private TextView health;
    private TextView Plugged;
    private TextView Battery;

    private ImageView charging_status_logo;
    private ImageView charging_type_logo;
    //For making Linear layout clickable
    private LinearLayout Monitor;
    //Animation TextViews
    private CardView time_since_last_charged_animation;
    private CardView plugged_type_animation;
    private CardView monitor_animation;
    private CardView status_animation;

    Handler monitor_delay;

    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View slide_view_2 = inflater.inflate(R.layout.slide_activity_2, container, false);

        /**   For Tracking     **/
        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        /** --------  **/

        //Binding the text views
        health_lev = (TextView) slide_view_2.findViewById(R.id.health);
        Plugged_type = (TextView) slide_view_2.findViewById(R.id.plugged_type);
        Battery_status = (TextView) slide_view_2.findViewById(R.id.status);
        monitor = (TextView) slide_view_2.findViewById(R.id.monitor);

        health = (TextView) slide_view_2.findViewById(R.id.health_1);
        Plugged = (TextView) slide_view_2.findViewById(R.id.plug);
        Battery = (TextView) slide_view_2.findViewById(R.id.stat);

        charging_status_logo = (ImageView) slide_view_2.findViewById(R.id.charging_state);
        charging_type_logo = (ImageView) slide_view_2.findViewById(R.id.charging_type);
        Monitor = (LinearLayout) slide_view_2.findViewById(R.id.linear_layout_monitor);
        //Binding the animation Text view
        time_since_last_charged_animation = (CardView) slide_view_2.findViewById(R.id.card_view_time_since_last_charge);
        plugged_type_animation = (CardView) slide_view_2.findViewById(R.id.card_view_plugged_type);
        monitor_animation = (CardView) slide_view_2.findViewById(R.id.card_view_monitor);
        status_animation = (CardView) slide_view_2.findViewById(R.id.card_view_status);

        Typeface gota_light = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gota_light.otf");
        Typeface gogoio_regular = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gogoio_regular.otf");
        Typeface gogoio_deco = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gogoia_deco.otf");
        Typeface panton_black_caps = Typeface.createFromAsset(getActivity().getAssets(), "commercial/panton_black_caps.otf");

        health_lev.setTypeface(gota_light);
        Plugged_type.setTypeface(gota_light);
        Battery_status.setTypeface(gota_light);

        health.setTypeface(gogoio_deco);
        Plugged.setTypeface(gogoio_regular);
        Battery.setTypeface(gogoio_deco);

        monitor.setTypeface(panton_black_caps);

        Monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Monitor")
                        .setAction("Monitor has been clicked through slide activity 2")
                        .build());

                monitor_delay = new Handler();
                monitor_delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent battery_setting_consumption = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                        startActivity(battery_setting_consumption);
                    }
                },500); //.5sec

            }
        });

        //REGISTERING THE BROADCAST RECIEVER
        registerBatteryPerformanceReceiver();
        return slide_view_2;
    }

    private void registerBatteryPerformanceReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //A BROADCAST RECEIVER NAME battery_performance_receiver HAS BEEN REGISTERED HERE
        getActivity().registerReceiver(battery_performance_receiver, filter);
    }

    //battery_performance_receiver FUNCTIONALITIES ARE DEFIEND HERE
    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("status", 0);
            int health = intent.getIntExtra("health", 0);
            int plugged = intent.getIntExtra("plugged", -1);

            Battery_status.setText(" " + battery_status(status));
            Plugged_type.setText(" " + plugged_type(plugged));
            health_lev.setText(getHealth(health));

            //-------Dynamically setting up the image for charging status type
            if (plugged == 1 || plugged == 2 || plugged == 4)
            {
                charging_status_logo.setImageResource(R.drawable.ic_battery_charging_full_white_green48dp);
            }
            else
            {
                charging_status_logo.setImageResource(R.drawable.ic_battery_charging_full_white_48dp);
            }
            //--------------------------------------------------------------//

            //--------------Dynamically setting up the image for plug type
         /*   if (plugged == 1) {
                charging_type_logo.setImageResource(R.drawable.ic_power_white_24dp);
            }
            if (plugged == 2) {
                charging_type_logo.setImageResource(R.drawable.ic_usb_white_24dp);
            }
            if (plugged != 1 && plugged != 2) {
                charging_type_logo.setImageResource(R.drawable.ic_battery_alert_white_24dp);
            }*/

            //-------------------------------------------------------------//
        }
    };


    private String plugged_type(int plugged_value) {
        String plug = "Not Connected";

        if (plugged_value == 1)
            plug = "AC";
        if (plugged_value == 2)
            plug = "USB";
        if (plugged_value == 4)
            plug = "WIRELESS";


        return plug;
    }

    private String battery_status(int status_value) {
        String status = "UNKNOWN";

        if (status_value == 2)
            status = "Charging";
        if (status_value == 3)
            status = "Discharging";
        if (status_value == 5)
            status = "Battery Full";
        if (status_value == 4)
            status = "Not Charging";

        if (status_value == 1)
            status = "UNKNOWN";

        return status;
    }

    //For Health
    private String getHealth(int health) {
        String healthStatus = "Unknown";

        if (health == 7)
            healthStatus = "COLD";

        if (health == 4)
            healthStatus = "DEAD";

        if (health == 2)
            healthStatus = "GOOD";

        if (health == 3)
            healthStatus = "OVER HEAT";

        if (health == 3)
            healthStatus = "OVER VOLTAGE";

        if (health == 1)
            healthStatus = "UNKNOWN";

        if (health == 6)
            healthStatus = "UNSPECIFIED FAILURE";

        return healthStatus;
    }

    @Override
    public void onResume() {

        super.onResume();
        // Log.i(TAG, "Setting screen name: ");
        mTracker.setScreenName("slide_activity_2");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroy() {

        getActivity().unregisterReceiver(battery_performance_receiver);
        super.onDestroy();
    }

}
