package performance.cleaner.codebreaker.batteryperformance.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

/**
 * Created by J!MMY on 2/8/2017.
 */
public class time_since_last_charged extends Service
{

    long start_time ;
    long output ;

    long time;

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

    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            int plugged = intent.getIntExtra("plugged", -1);


            /**FOR CALCULATING TIME SINCE LAST CHARGED **/
            if (plugged == 1 || plugged == 2)
            {
                //  Toast.makeText(context, "service working", Toast.LENGTH_LONG).show();
                start_time = System.currentTimeMillis();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("start_time", start_time);
                editor.apply();

                /*Toast.makeText(getApplicationContext(),"start time 1 " + start_time,Toast.LENGTH_LONG).show();*/


            }

            else
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                time = preferences.getLong("start_time", 0);

                /*Toast.makeText(getApplicationContext(),"start time 2 " + time,Toast.LENGTH_LONG).show()*/;

                output = (System.currentTimeMillis() - time) / 60000;

                /*Toast.makeText(getApplicationContext(),"output " + time,Toast.LENGTH_LONG).show();*/
                // Toast.makeText(context, "service work" + output, Toast.LENGTH_LONG).show();
                SharedPreferences time_output = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = time_output.edit();
                editor.putLong("time_output", output);
                editor.apply();
            }
            /**----------------------------------------------------------------------**/
        }
    };
}
