package performance.cleaner.codebreaker.batteryperformance.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.Settings;


public class Ringtone_service extends Service {
    MediaPlayer ringtone;
    //Boolean IsRunning;

    //Shared Preferences
    SharedPreferences Alarm_states;

    public int actual_state;
    public int actual_seek_value;
    public int level;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Alarm_states = getSharedPreferences("ALARM",MODE_PRIVATE);

        actual_seek_value = Alarm_states.getInt("alarm_percentage", 0);
        actual_state = Alarm_states.getInt("alarm_state",0);

        ringtone = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

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

    private BroadcastReceiver battery_performance_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

            if (actual_state == 1 && actual_seek_value == level)
            {
                ringtone.start();
            }

        }
    };


    @Override
    public void onDestroy() {
        ringtone.pause();
        ringtone.stop();
        ringtone.release();

        unregisterReceiver(battery_performance_receiver);
        super.onDestroy();
    }


}
