package performance.cleaner.codebreaker.batteryperformance.utils;

/**
 * Created by ROHIT on 7/2/2017.
 */

import android.graphics.drawable.Drawable;

public class AppsListItem {

    private long mCacheSize;
    private String mPackageName, mApplicationName;
    private Drawable mIcon;

    public AppsListItem(String packageName, String applicationName, Drawable icon, long cacheSize) {
        mCacheSize = cacheSize;
        mPackageName = packageName;
        mApplicationName = applicationName;
        mIcon = icon;
    }

}
