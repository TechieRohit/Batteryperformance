package performance.cleaner.codebreaker.batteryperformance.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import performance.cleaner.codebreaker.batteryperformance.Activities.Activity_Main;
import performance.cleaner.codebreaker.batteryperformance.R;


public class time_left extends Service {

    int previous_level = 0;

    long output_charging_sec;
    long output_charging_min;
    long output_charging_hour;
    long output_charging_min_actual;

    long output_discharging_sec;
    long output_discharging_min;
    long output_discharging_hour;
    long output_discharging_min_actual;

    long t1;
    long t2;

    private NotificationCompat.Builder notify;
    private RemoteViews remoteViews;
    public static final int Unique_id = 58585;

    int level_global;
    double temp_global;


    @Override
    public IBinder onBind(Intent intent)
    {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        registerBatteryPerformanceReceiver();

        return START_STICKY;
    }

    private void registerBatteryPerformanceReceiver()
    {
        //WHAT IS THIS IntentFilter?
        //Used to jump from one activity to another
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //code changed from battery_receiver to battery_performance_receiver
        registerReceiver(battery_performance_receiver, filter);  //WHY WE HAVE USE FILTER?
    }

    public void get_notification(int status, long min, long hour)
    {

        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_bar);

        //Building the notification
        notify = new NotificationCompat.Builder(this);

        //For setting up image in Image view
        remoteViews.setImageViewResource(R.id.notification_icon, R.mipmap.final_9);
        remoteViews.setTextViewText(R.id.Notification_percentage, " " + level_global + "%");
        remoteViews.setTextViewText(R.id.notification_temperature, " " + temp_global + " C");

        if (status == 2)
        {
            remoteViews.setTextViewText(R.id.notification_time_left_1, "Charging Time Left" );

            if ( hour > 10 || (min == 0 && hour == 0) )
            {
                remoteViews.setTextViewText(R.id.notification_time_left, "Calculating..." );
            }
            else {
                if ( level_global <30 && hour <=1 )
                {
                    remoteViews.setTextViewText(R.id.notification_time_left, "Calculating...");
                }

                else
                {
                    remoteViews.setTextViewText(R.id.notification_time_left, "About " + hour + " hour " + min + " min");
                }
            }
        }


        if (status != 2)
        {
            remoteViews.setTextViewText(R.id.notification_time_left_1, "Battery Time Left" );

            if ( hour > 18 || (min == 0 && hour == 0) )
            {
                remoteViews.setTextViewText(R.id.notification_time_left, "Calculating..." );
            }
            else
            {
                if ( level_global >15 && hour <1 )
                {
                    remoteViews.setTextViewText(R.id.notification_time_left, "Calculating...");
                }

                else
                {
                    remoteViews.setTextViewText(R.id.notification_time_left, "About " + hour + " hour " + min + " min");
                }
            }

        }

        //Building the notification
        //for setting up notification icon
        notify.setSmallIcon(R.drawable.ic_battery_charging_full_black_36dpx);
        notify.setContent(remoteViews);

        //For making this notification sticky
        notify.setOngoing(true);

        //Applying click event on notification bar
        Intent intent = new Intent(this, Activity_Main.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notify.setContentIntent(pendingIntent);

        //This thing below builds the notification
        NotificationManager SN = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SN.notify(Unique_id, notify.build());
    }

    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int status = intent.getIntExtra("status", 0);

            int temperature = intent.getIntExtra("temperature", 0);
            double temp = (double) temperature / 10;

            level_global = level;
            temp_global = temp;

            //--------------------------------------MAIN ALGORITHM FOR TIME LEFT-----------------------------------//
            //CHARGING
            if (status == 2)
            {

                if (previous_level == 0)
                {
                    previous_level = level;
                    t1 = System.currentTimeMillis();

                    SharedPreferences t_1 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = t_1.edit();
                    editor.putLong("start_char",t1);
                    editor.apply();
                }
                if (level > previous_level)
                {


                    SharedPreferences t_1 = PreferenceManager.getDefaultSharedPreferences(context);
                    long actual_t1 = t_1.getLong("start_char", 0 );

                    output_charging_sec = (((System.currentTimeMillis() - actual_t1) / 1000) * (100 - level));
                    output_charging_min = output_charging_sec / 60;
                    output_charging_hour = output_charging_min / 60;
                    output_charging_min_actual = output_charging_min % 60;

                    SharedPreferences char_mins = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor_mins = char_mins.edit();
                    editor_mins.putLong("char_minutes", output_charging_min_actual);
                    editor_mins.apply();

                    SharedPreferences char_hours = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor_hours = char_hours.edit();
                    editor_hours.putLong("char_hours",output_charging_hour);
                    editor_hours.apply();

                    previous_level = 0;

                }

                get_notification(status, output_charging_min_actual, output_charging_hour);
            }

                //DISCHARGING
                if (status != 2)
                {


                    if (previous_level == 0)
                    {
                        // long time_fraction;
                        previous_level = level;   //80
                        //edit from here

                        t2 = System.currentTimeMillis();

                        SharedPreferences t_2 = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = t_2.edit();
                        editor.putLong("start_dis", t2);
                        editor.apply();
                    }


                    if (previous_level > level)   //previous_level==80 //level==79
                    {


                        SharedPreferences t_2 = PreferenceManager.getDefaultSharedPreferences(context);
                        long actual_t2 = t_2.getLong("start_dis", 0);

                        output_discharging_sec = ((System.currentTimeMillis() - actual_t2) / 1000) * (level);
                        output_discharging_min = output_discharging_sec / 60;
                        output_discharging_hour = output_discharging_min / 60;
                        output_discharging_min_actual = output_discharging_min % 60;


                        SharedPreferences time_output_mins = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor_mins = time_output_mins.edit();
                        editor_mins.putLong("dis_minutes", output_discharging_min_actual);
                        editor_mins.apply();

                        SharedPreferences time_output_hours = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor_hours = time_output_hours.edit();
                        editor_hours.putLong("dis_hours", output_discharging_hour);
                        editor_hours.apply();

                       // Toast.makeText(getApplicationContext(),"Open_source " + output_discharging_hour + "Hours " + output_charging_min_actual + "mins" ,Toast.LENGTH_LONG).show();
                        previous_level = 0;
                    }

                    get_notification(status, output_discharging_min, output_discharging_hour);
                }

            }
        };

}
