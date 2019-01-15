package performance.cleaner.codebreaker.batteryperformance.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;


public class SlideFragment1 extends Fragment {

    private TextView voltage_lev;
    private TextView temperature_lev;
    private TextView technology_type;

    private TextView voltage;
    private TextView temperature;
    private TextView technology;

    private TextView alarm;

    private static DecimalFormat Up_to_2 = new DecimalFormat(".##");

    private Tracker mTracker;

    private LinearLayout Alarm;

    Handler alarm_delay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View slide_view_1 = inflater.inflate(R.layout.slide_activity_1, container, false);

        /**   For Tracking     **/
        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        /** --------  **/

        Alarm = (LinearLayout)slide_view_1.findViewById(R.id.battery_alarm);
        alarm = (TextView)slide_view_1.findViewById(R.id.t_alarm);
        //Binding the text views
        voltage_lev = (TextView) slide_view_1.findViewById(R.id.voltage);
        temperature_lev = (TextView) slide_view_1.findViewById(R.id.temperature);
        technology_type = (TextView) slide_view_1.findViewById(R.id.technology);


        voltage = (TextView) slide_view_1.findViewById(R.id.voltage_1);
        temperature = (TextView) slide_view_1.findViewById(R.id.temperature_1);
        technology = (TextView) slide_view_1.findViewById(R.id.technology_1);

        Typeface gota_light = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gota_light.otf");
        Typeface gogoio_regular = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gogoio_regular.otf");
        Typeface gogoio_deco = Typeface.createFromAsset(getActivity().getAssets(), "commercial/gogoia_deco.otf");
        Typeface panton_black_caps = Typeface.createFromAsset(getActivity().getAssets(), "commercial/panton_black_caps.otf");


        voltage_lev.setTypeface(gota_light);
        temperature_lev.setTypeface(gota_light);
        technology_type.setTypeface(gota_light);

        voltage.setTypeface(gogoio_deco);
        temperature.setTypeface(gogoio_regular);
        technology.setTypeface(gogoio_regular);

        alarm.setTypeface(panton_black_caps);

        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Alarm")
                        .setAction("User is into Battery Alarm through slide activity")
                        .build());

                alarm_delay = new Handler();
                alarm_delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent to_alarm = new Intent(getActivity(),
                                performance.cleaner.codebreaker.batteryperformance.activities.Alarm.class);
                        startActivity(to_alarm);

                    }
                }, 500);

            }
        });

        //Here this function will register battery_performance_receiver
        registerBatteryPerformanceReceiver();
        return slide_view_1;
    }


    //A function to register battery_performance_receiver
    private void registerBatteryPerformanceReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getActivity().registerReceiver(battery_performance_receiver, filter);  //WHY WE HAVE USE FILTER?
    }

    //code change from battery_receiver to battery_performance_receiver
    //
    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int temperature = intent.getIntExtra("temperature", 0);
            int voltage = intent.getIntExtra("voltage", 0);
            String technology = intent.getStringExtra("technology");
            double temp = (double) temperature / 10;
            double volt = (voltage * 0.001);

            voltage_lev.setText(" " + Up_to_2.format(volt) + " V");
            temperature_lev.setText(" " + temp + " C");
            technology_type.setText(" " + technology);
        }
    };


    @Override
    public void onResume() {

        super.onResume();
        // Log.i(TAG, "Setting screen name: ");
        mTracker.setScreenName("SlideFragment1");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroy() {

        getActivity().unregisterReceiver(battery_performance_receiver);
        super.onDestroy();
    }
}
