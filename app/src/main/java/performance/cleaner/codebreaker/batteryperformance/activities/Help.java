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

import performance.cleaner.codebreaker.batteryperformance.googletracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class Help extends AppCompatActivity {

    TextView helpDetails;
    TextView help;
    Toolbar helpToolbar;

    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        /**   For Tracking     **/
        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();
        /** --------  **/


        helpDetails = (TextView)findViewById(R.id.textView4);
        helpToolbar = (Toolbar)findViewById(R.id.toolbar_help);
        help = (TextView)findViewById(R.id.help);

        setSupportActionBar(helpToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");

        helpDetails.setTypeface(canaro);
        help.setTypeface(canaro);
        TextView toolbar_thelp = (TextView) helpToolbar.findViewById(R.id.toolbar_title);
        toolbar_thelp.setTypeface(canaro);

   /*     helpToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.explore)
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Mintegral")
                            .setAction("Help Toolbar Button Clicked")
                            .build());

                    openWall();
                }

                return false;
            }
        });*/

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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Help");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}