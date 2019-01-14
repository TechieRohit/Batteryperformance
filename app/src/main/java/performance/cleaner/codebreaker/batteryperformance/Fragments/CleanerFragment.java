package performance.cleaner.codebreaker.batteryperformance.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import performance.cleaner.codebreaker.batteryperformance.Google_Tracker.AnalyticsApplication;
import performance.cleaner.codebreaker.batteryperformance.R;
import performance.cleaner.codebreaker.batteryperformance.Services.CleanerService;
import performance.cleaner.codebreaker.batteryperformance.Utilities.AppsListItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.victor.loading.book.BookLoading;

import java.text.DecimalFormat;
import java.util.List;


public class CleanerFragment extends Fragment implements CleanerService.OnActionListener
{

    private static final int REQUEST_STORAGE = 0;

    private static final String[] PERMISSIONS_STORAGE =
            {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private CleanerService mCleanerService;
    //private AppsListAdapter mAppsListAdapter;
    private SharedPreferences mSharedPreferences;
    private ProgressDialog mProgressDialog;
    private View mProgressBar;
    private LinearLayoutManager mLayoutManager;
    private Menu mOptionsMenu;

    private boolean mAlreadyScanned = false;
    private boolean mAlreadyCleaned = false;
    private String mSearchQuery;

    private String mSortByKey;
    private String mCleanOnAppStartupKey;
    private String mExitAfterCleanKey;

    private BookLoading loading;
    private LinearLayout first_layout;
    private LinearLayout Clean;

    private double Total_cache;
    private double Total_mem;
    private double Low_mem;
    private double High_mem;

    private static DecimalFormat Up_to_2 = new DecimalFormat(".##");
    
    private Handler loading_delay;
    private Handler per_delay;

    private TextView Calulating_cache_1;
    private TextView About_cache_1;
    private TextView Cache_info_1;
    private TextView Clean_1;

    private TextView About_cache_2;
    private TextView Cache_info_2;
    private TextView Clean_2;

    private TextView Total_Cache;
    private TextView Total_Memory;
    private TextView Available_Memory;
    private TextView Used_Memory;

    private TextView Total_Cache_1;
    private TextView Total_Memory_1;
    private TextView Available_Memory_1;
    private TextView Used_Memory_1;

    private TextView Percentage;

    public ProgressDialog progres;

    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View cleanerView = inflater.inflate(R.layout.fragment_cleaner, container, false);

        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        first_layout = (LinearLayout)cleanerView.findViewById(R.id.first_layout);
        Clean = (LinearLayout)cleanerView.findViewById(R.id.clean);
        loading = (BookLoading)cleanerView.findViewById(R.id.rotateloading);
        loading.start();

        Typeface canaro = Typeface.createFromAsset(getActivity().getAssets(),"commercial/Canaro-LightDEMO.otf");
        //cache_size.setTypeface(canaro);

        Calulating_cache_1 = (TextView)cleanerView.findViewById(R.id.cal_cache_1);
        About_cache_1 = (TextView)cleanerView.findViewById(R.id.about_cache_1);
        Cache_info_1 = (TextView)cleanerView.findViewById(R.id.cache_info_1);
        Clean_1 = (TextView)cleanerView.findViewById(R.id.clean_1);


        About_cache_2 = (TextView)cleanerView.findViewById(R.id.about_cache_2);
        Cache_info_2 = (TextView)cleanerView.findViewById(R.id.cache_info_2);
        Clean_2 = (TextView)cleanerView.findViewById(R.id.clean_2);

        Total_Cache = (TextView)cleanerView.findViewById(R.id.total_cache);
        Total_Memory = (TextView)cleanerView.findViewById(R.id.total);
        Available_Memory = (TextView)cleanerView.findViewById(R.id.available);
        Used_Memory = (TextView)cleanerView.findViewById(R.id.used);

        Total_Cache_1 = (TextView)cleanerView.findViewById(R.id.total_cache_1);
        Total_Memory_1 = (TextView)cleanerView.findViewById(R.id.total_1);
        Available_Memory_1 = (TextView)cleanerView.findViewById(R.id.available_1);
        Used_Memory_1 = (TextView)cleanerView.findViewById(R.id.used_1);

        Percentage = (TextView)cleanerView.findViewById(R.id.cal_per);

        Calulating_cache_1.setTypeface(canaro);
        About_cache_1.setTypeface(canaro);
        Cache_info_1.setTypeface(canaro);
        Clean_1.setTypeface(canaro);

        About_cache_2.setTypeface(canaro);
        Cache_info_2.setTypeface(canaro);
        Clean_2.setTypeface(canaro);

        Total_Cache.setTypeface(canaro);
        Total_Memory.setTypeface(canaro);
        Available_Memory.setTypeface(canaro);
        Used_Memory.setTypeface(canaro);

        Total_Cache_1.setTypeface(canaro);
        Total_Memory_1.setTypeface(canaro);
        Available_Memory_1.setTypeface(canaro);
        Used_Memory_1.setTypeface(canaro);

        Percentage.setTypeface(canaro);

        Percentage.setText("30" + " percent");

        mLayoutManager = new LinearLayoutManager(getActivity());

        Clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCleanerService != null && !mCleanerService.isScanning() &&
                        !mCleanerService.isCleaning() && mCleanerService.getCacheSize() > 0) {
                    mAlreadyCleaned = false;

                    /*Toast.makeText(getActivity(),"Button Clicked",Toast.LENGTH_LONG).show();*/
                    cleanCache();

                    /*Intent to_cleaned = new Intent(getActivity(), Cache_cleaned.class);
                    startActivity(to_cleaned);*/

                    progres = ProgressDialog.show(getActivity(), "Cleaning Cache...","CLEAR_APP_CACHE permission is not available to external application in Android 6.0" +
                            " and above as a result internal cache cannot be cleaned.However external cache is still cleaned !" );

                }
            }
        });

        /*Toast.makeText(getActivity(),"On Create view",Toast.LENGTH_SHORT).show();*/
        return cleanerView;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            mCleanerService.setOnActionListener(CleanerFragment.this);

            //updateStorageUsage();

            if (!mCleanerService.isCleaning() && !mCleanerService.isScanning()) {
                if (mSharedPreferences.getBoolean(mCleanOnAppStartupKey, false) &&
                        !mAlreadyCleaned) {
                    mAlreadyCleaned = true;

                    cleanCache();
                } else if (!mAlreadyScanned) {
                    mCleanerService.scanCache();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCleanerService.setOnActionListener(null);
            mCleanerService = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        /*Percentage.setText("10" + "%");*/

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

        getActivity().getApplication().bindService(new Intent(getActivity(), CleanerService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onDestroyOptionsMenu()
    {
        mOptionsMenu = null;
    }

    @Override
    public void onResume() {
        updateStorageUsage();

        /*Percentage.setText("35" + "%");*/

        /*Toast.makeText(getActivity(),"onResume",Toast.LENGTH_SHORT).show();*/

        mTracker.setScreenName("CleanerFragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        super.onResume();
    }

    @Override
    public void onPause() {

        //calculating.setProgress(25);
        /*Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();*/
        if (mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }

        getActivity().finish();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        getActivity().getApplication().unbindService(mServiceConnection);

        super.onDestroy();
    }

    private void updateStorageUsage() {

        /*Toast.makeText(getActivity(),"updateStorageUsage function called",Toast.LENGTH_LONG).show();*/

            StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());

            long totalMemory = (long) stat.getBlockCount() * (long) stat.getBlockSize();
            long medMemory = mCleanerService != null ? mCleanerService.getCacheSize() : 0;
            long lowMemory = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB &&
                    !Environment.isExternalStorageEmulated()) {
                stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

                totalMemory += (long) stat.getBlockCount() * (long) stat.getBlockSize();
                lowMemory += (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }

            long highMemory = totalMemory - medMemory - lowMemory;



            Total_cache = (double) medMemory / 1048576 ;

            High_mem = ((double) highMemory / 1048576)/ 1024 ;
            Low_mem = ((double) lowMemory / 1048576)/ 1024 ;
            Total_mem = ((double) totalMemory / 1048576) / 1024 ;


        if (Total_cache <=  1 )
        {
            Total_Cache.setText("" + Up_to_2.format(Total_cache*1024) + " kb");

            if (Total_cache*1024 <= 1)
            {
                Total_Cache.setText("" + Up_to_2.format(Total_cache*1024*1024) + " b");
            }
        }
        else {
            Total_Cache.setText("" + Up_to_2.format(Total_cache) + " mb");
        }

        /*Total_Cache.setText("" + Up_to_2.format(Total_cache) + " mb");*/

        Total_Memory.setText("" + Up_to_2.format(Total_mem) + " gb");
        Available_Memory.setText("" + Up_to_2.format(Low_mem) + " gb");
        Used_Memory.setText("" + Up_to_2.format(High_mem) + " gb");

    }


    private void cleanCache()
    {
        /*Toast.makeText(getActivity(),"cleanCache function called",Toast.LENGTH_LONG).show();*/
        if (!CleanerService.canCleanExternalCache(getActivity())) {
            if (shouldShowRequestPermissionRationale(PERMISSIONS_STORAGE[0])) {
               // showStorageRationale();
            } else {
                requestPermissions(PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        } else {
            mCleanerService.cleanCache();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {

        /*Toast.makeText(getActivity(),"OnRequestPermissionResult function called",Toast.LENGTH_LONG).show();*/
        if (requestCode == REQUEST_STORAGE)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCleanerService.cleanCache();
            } else
            {
                //showStorageRationale();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onScanStarted(Context context)
    {
        //calculating.setProgress(60);
        /*Toast.makeText(getActivity(),"onScanStarted Function called",Toast.LENGTH_LONG).show();*/

        per_delay = new Handler();
        per_delay.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Percentage.setText("70" + " percent");
            }
        },1000);


        if (isAdded())
        {

        }
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max)
    {
        //Percentage.setText("70" + "%");
        /*Toast.makeText(getActivity(),"onScanProgressUpdated Function called",Toast.LENGTH_LONG).show();*/
        if (isAdded())
        {

        }
    }

    @Override
    public void onScanCompleted(Context context, List<AppsListItem> apps)
    {


        //mAppsListAdapter.setItems(getActivity(), apps, getSortBy(), mSearchQuery);
        /*Toast.makeText(getActivity(),"onScanCompleted Function called",Toast.LENGTH_LONG).show();*/
        if (isAdded())
        {
            updateStorageUsage();
           // Percentage.setText("70" + "%");
           /*Toast.makeText(getActivity(),"onScanCompleted Function isAdded()",Toast.LENGTH_LONG).show();*/
           //showProgressBar(false);
        }
        Percentage.setText("100" + " percent");

        loading.stop();
        loading_delay = new Handler();
        loading_delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                first_layout.setVisibility(View.GONE);
            }
        }, 1000);  // 1 sec

        mAlreadyScanned = true;
    }
    @Override
    public void onCleanStarted(Context context) {

        /*Toast.makeText(getActivity(),"onCleanStarted Function called",Toast.LENGTH_LONG).show();*/
        if (isAdded()) {
          /*  if (isProgressBarVisible()) {
                showProgressBar(false);
            }

            if (!getActivity().isFinishing()) {
                mProgressDialog.show();
            }*/

            /*Toast.makeText(getActivity(),"onCleanStarted Function isAdded()",Toast.LENGTH_LONG).show();*/

        }
    }

    @Override
    public void onCleanCompleted(Context context, boolean succeeded)
    {

        /*Toast.makeText(getActivity(),"onCleanCompleted Function called",Toast.LENGTH_LONG).show();*/

        if (succeeded) {
         //   mAppsListAdapter.trashItems();

            /*Toast.makeText(getActivity(),"onCleanCompleted Function succeeded()",Toast.LENGTH_LONG).show();*/
        }

        if (isAdded())
        {
           // updateStorageUsage();
            /*Toast.makeText(getActivity(),"onCleanCompleted Function isAdded()",Toast.LENGTH_LONG).show();*/
            /*if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }*/
        }

        if (succeeded && getActivity() != null && !mAlreadyCleaned &&
                mSharedPreferences.getBoolean(mExitAfterCleanKey, false))
        {
            /*Toast.makeText(getActivity(),"onCleanCompleted Function finish()",Toast.LENGTH_LONG).show();*/
           // getActivity().finish();
        }

        loading_delay = new Handler();
        loading_delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                progres.dismiss();
                getActivity().finish();
                Toast.makeText(getActivity(), "Cache Cleaned", Toast.LENGTH_SHORT).show();
            }
        }, 5000);

    }

}
