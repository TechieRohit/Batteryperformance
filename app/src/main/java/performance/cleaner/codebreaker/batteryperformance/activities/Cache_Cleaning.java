package performance.cleaner.codebreaker.batteryperformance.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import performance.cleaner.codebreaker.batteryperformance.fragments.CleanerFragment;
import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


import java.util.HashMap;
import java.util.Map;

public class Cache_Cleaning extends AppCompatActivity {

    android.support.v4.app.FragmentManager cache_fragmentmanager;
    android.support.v4.app.FragmentTransaction cache_fragmenttrancsaction;

    private Tracker mTracker;

    Toolbar cache_toolbar;
    private TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache__cleaning);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        toolbar_title = (TextView)findViewById(R.id.toolbar_title_cache);

        cache_toolbar = (Toolbar)findViewById(R.id.toolbar_cache);

        Toolbar toolbar_cache = (Toolbar)findViewById(R.id.toolbar_cache);
        setSupportActionBar(toolbar_cache);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* cache_toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.explore) {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Mintegral")
                            .setAction("Main Toolbar Button Clicked")
                            .build());
                }
                return false;
            }
        });*/

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");
        toolbar_title.setTypeface(canaro);

        cache_fragmentmanager = getSupportFragmentManager();
        cache_fragmenttrancsaction = cache_fragmentmanager.beginTransaction();
        cache_fragmenttrancsaction.replace(R.id.cache_containerView,new CleanerFragment()).commit();
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
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Cache_Cleaning");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}
