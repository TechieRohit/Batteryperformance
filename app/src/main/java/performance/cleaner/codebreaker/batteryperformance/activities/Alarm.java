package performance.cleaner.codebreaker.batteryperformance.activities;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;

import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;
import performance.cleaner.codebreaker.batteryperformance.service.Ringtone_service;
import performance.cleaner.codebreaker.batteryperformance.utils.Constants;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


public class Alarm extends AppCompatActivity
{
    private Switch on_or_off;

    private int percentage = 0 ;
    private TextView battery_alarm;
    private TextView set_to;
    private TextView message;

     public int alarm_state = 0 ;
     public int alarm_percentage;

    Toolbar alarmtoolbar;
    TextView toolbar_talarm;
    ImageView alarmView;
    //Shared Preferences
    SharedPreferences Alarm_states;

    DiscreteSeekBar seek_value;

    private Tracker mTracker;

    private NotificationCompat.Builder notify;
    private RemoteViews remoteViews;
    public static final int Unique_id = 58587;
    private NotificationManager SN;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);



        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();


        alarmView = (ImageView)findViewById(R.id.alarm_image);
         seek_value = (DiscreteSeekBar) findViewById(R.id.discrete_seek);

        SN = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Typeface canaro = Typeface.createFromAsset(getAssets(), "commercial/Canaro-LightDEMO.otf");

        message = (TextView) findViewById(R.id.toast);
        battery_alarm = (TextView)findViewById(R.id.battery_alarm);
        set_to = (TextView) findViewById(R.id.set_to_desired);
        alarmtoolbar = (Toolbar)findViewById(R.id.toolbar_alarm);

        toolbar_talarm = (TextView) alarmtoolbar.findViewById(R.id.toolbar_title_a);
        toolbar_talarm.setTypeface(canaro);
        message.setTypeface(canaro);
        battery_alarm.setTypeface(canaro);
        set_to.setTypeface(canaro);
        //SharedPreference
        Alarm_states = getSharedPreferences("ALARM", MODE_PRIVATE);

        alarmtoolbar = (Toolbar)findViewById(R.id.toolbar_alarm);
        setSupportActionBar(alarmtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*alarmtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.explore)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Mintegral")
                            .setAction("Alarm Toolbar Button Clicked")
                            .build());
                }

                return false;
            }
        });*/

        alarm_state = Alarm_states.getInt(Constants.IntentKeys.ALARM_STATE, 0);
        alarm_percentage = Alarm_states.getInt(Constants.IntentKeys.ALARM_PERCENTAGE, 0);
        seek_value.setProgress(alarm_percentage);

        seek_value.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                percentage = value;
                return value ;
            }
        });


        //Initializing the toggle button to set the alarm on or off
        on_or_off = (Switch) findViewById(R.id.on_or_off);
        on_or_off.setChecked(false);

        on_or_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (on_or_off.isChecked())
                {
                    //Alarm is on
                    //set the alarm to desired progress
                    //toggle button must be cached to on
                    //Alarm message must cached to "Alarm is set to "

                    message.setText("Alarm is set to " + percentage + " percent");
                    alarmView.setImageResource(R.drawable.ic_alarm_on_black_48dp);

                    SharedPreferences.Editor editor = Alarm_states.edit();
                    editor.putInt(Constants.IntentKeys.ALARM_STATE, 1);
                    editor.putInt(Constants.IntentKeys.ALARM_PERCENTAGE, percentage);
                    editor.apply();

                    //get_notification();

 count = Alarm_states.getInt("counter",0);

                    if (count != 1) {
                        Intent start = new Intent(Alarm.this, Ringtone_service.class);
                        startService(start);
                    }

                    editor.putInt("counter", 1);
                    editor.apply();


                    Intent start = new Intent(Alarm.this, Ringtone_service.class);
                    startService(start);

                }
                else
                 {
                    //Alarm is off
                    //toggle button must cahced to off
                    // Alarm message must cached to "Alarm if off"

                     message.setText("Alarm is off");
                     alarmView.setImageResource(R.drawable.ic_alarm_off_black_48dp);
                     SharedPreferences.Editor editor = Alarm_states.edit();
                     editor.putInt("alarm_state", 0);
                     editor.apply();

                     SN.cancel(Unique_id);

                     Intent stop = new Intent(Alarm.this, Ringtone_service.class);
                     stopService(stop);

 editor.putInt("counter", 0);
                     editor.apply();

                }
            }
        });



        if (alarm_state == 1)
        {
            Intent start = new Intent(Alarm.this, Ringtone_service.class);
            startService(start);
              on_or_off.setChecked(true);
            message.setText("Alarm is set to " + alarm_percentage);
            alarmView.setImageResource(R.drawable.ic_alarm_on_black_48dp);

            seek_value.setProgress(alarm_percentage);
        }
        if (alarm_state == 0)
        {
            Intent stop = new Intent(Alarm.this, Ringtone_service.class);
            stopService(stop);
            on_or_off.setChecked(false);
            message.setText("Alarm is off");
            alarmView.setImageResource(R.drawable.ic_alarm_off_black_48dp);
            seek_value.setProgress(0);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        mTracker.setScreenName("Alarm");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
