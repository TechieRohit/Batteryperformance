package performance.cleaner.codebreaker.batteryperformance.Activities;

import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import performance.cleaner.codebreaker.batteryperformance.Google_Tracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class About extends AppCompatActivity {

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar about_toolbar = (Toolbar)findViewById(R.id.toolbar_about_1);
        setSupportActionBar(about_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** For Tracking **/
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        /** ------------- **/

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");

        TextView toolbar_text = (TextView)findViewById(R.id.toolbar_tabout_1);
        toolbar_text.setTypeface(canaro);

        TextView Version = (TextView)findViewById(R.id.version_number);
        TextView Number = (TextView)findViewById(R.id.number);
        TextView Open_Source = (TextView)findViewById(R.id.open_source);

        Version.setTypeface(canaro);
        Number.setTypeface(canaro);
        Open_Source.setTypeface(canaro);

        LinearLayout Open = (LinearLayout)findViewById(R.id.open);
        Open.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent to_open_source = new Intent(About.this,Open_source.class);
                startActivity(to_open_source);
            }
        });

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
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("About");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
