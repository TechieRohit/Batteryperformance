package performance.cleaner.codebreaker.batteryperformance.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import performance.cleaner.codebreaker.batteryperformance.Google_Tracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class Open_source extends AppCompatActivity
{
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opensource);

        Toolbar abouut_toolbar = (Toolbar)findViewById(R.id.toolbar_about);
        setSupportActionBar(abouut_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /** For Tracking **/
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        /** ------------- **/

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");
        TextView toolbar_text = (TextView)findViewById(R.id.toolbar_tabout);
        TextView library_used = (TextView)findViewById(R.id.library_used);
        TextView license_1 = (TextView)findViewById(R.id.license_mit);
        TextView following = (TextView)findViewById(R.id.text_view);
        TextView software_1 = (TextView)findViewById(R.id.software);

        TextView license_2 = (TextView)findViewById(R.id.licens_apache_discrete);
        TextView software_2 = (TextView)findViewById(R.id.DiscreteSeekBar);

        TextView license_3 = (TextView)findViewById(R.id.licens_apache_loading);
        TextView software_3 = (TextView)findViewById(R.id.loading);

        TextView license_4 = (TextView)findViewById(R.id.licens_apache_notice);
        TextView software_4 = (TextView)findViewById(R.id.notice);


        toolbar_text.setTypeface(canaro);
        library_used.setTypeface(canaro);
        license_1.setTypeface(canaro);
        software_1.setTypeface(canaro);
        following.setTypeface(canaro);

        license_2.setTypeface(canaro);
        software_2.setTypeface(canaro);

        license_3.setTypeface(canaro);
        software_3.setTypeface(canaro);

        license_4.setTypeface(canaro);
        software_4.setTypeface(canaro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Open_source");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
