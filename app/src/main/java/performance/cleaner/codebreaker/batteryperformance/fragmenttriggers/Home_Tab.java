package performance.cleaner.codebreaker.batteryperformance.fragmenttriggers;

/**
 * Created by J!MMY on 12/10/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import performance.cleaner.codebreaker.batteryperformance.fragments.MainHomeFragment;
import performance.cleaner.codebreaker.batteryperformance.R;


public class Home_Tab extends Fragment {

    public static TabLayout tabLayout_1;
    public static ViewPager viewPager_1;
    public static int int_item = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.home_tab, null);
        tabLayout_1 = (TabLayout) x.findViewById(R.id.tabs);
        viewPager_1 = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager_1.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager_1.setOffscreenPageLimit(1);


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */


        tabLayout_1.post(new Runnable() {
            @Override
            public void run()
            {
                tabLayout_1.setupWithViewPager(viewPager_1);
            }
        });

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainHomeFragment();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_item;

        }
    }
}
