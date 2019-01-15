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
import com.mobvista.msdk.MobVistaConstans;
import com.mobvista.msdk.MobVistaSDK;
import com.mobvista.msdk.out.MobVistaSDKFactory;
import com.mobvista.msdk.out.MvWallHandler;

import java.util.HashMap;
import java.util.Map;


public class Help extends AppCompatActivity {

    TextView help_detail;
    TextView help;
    Toolbar helptoolbar;

    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        /**   For Tracking     **/
        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();
        /** --------  **/


        help_detail = (TextView)findViewById(R.id.textView4);
        helptoolbar = (Toolbar)findViewById(R.id.toolbar_help);
        help = (TextView)findViewById(R.id.help);

        MobVistaSDK sdk = MobVistaSDKFactory.getMobVistaSDK(); Map<String,String> Map = sdk.getMVConfigurationMap("","");
        sdk.init(Map, this);

        setSupportActionBar(helptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");

        help_detail.setTypeface(canaro);
        help.setTypeface(canaro);
        TextView toolbar_thelp = (TextView) helptoolbar.findViewById(R.id.toolbar_title);
        toolbar_thelp.setTypeface(canaro);

        helptoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void openWall()
    {
        try
        {
            Class<?> aClass = Class
                    .forName("com.mobvista.msdk.shell.MVActivity");
            Intent intent = new Intent(this, aClass);
            intent.putExtra(MobVistaConstans.PROPERTIES_UNIT_ID, "14445");   //Unit Id
            this.startActivity(intent);
        } catch (Exception e) {
            Log.e("MVActivity", "", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        preloadWall();
        loadHandler();
        mTracker.setScreenName("Help");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void preloadWall() {
        MobVistaSDK sdk = MobVistaSDKFactory.getMobVistaSDK();
        Map<String, Object> preloadMap = new HashMap<String, Object>();
        preloadMap.put(MobVistaConstans.PROPERTIES_LAYOUT_TYPE, MobVistaConstans.LAYOUT_APPWALL);
        preloadMap.put(MobVistaConstans.PROPERTIES_UNIT_ID, "14445");
        sdk.preload(preloadMap);
    }

    public void loadHandler()
    {
        Map<String, Object> properties = MvWallHandler.getWallProperties("14445");
        properties.put(MobVistaConstans.PROPERTIES_WALL_STATUS_COLOR, R.color.mobvista_facebook);
        properties.put(MobVistaConstans.PROPERTIES_WALL_NAVIGATION_COLOR, R.color.mobvista_facebook);
        properties.put(MobVistaConstans.PROPERTIES_WALL_TITLE_BACKGROUND_COLOR, R.color.mobvista_facebook);
        MvWallHandler mvHandler = new MvWallHandler(properties, this);

        mvHandler.load();
    }
}