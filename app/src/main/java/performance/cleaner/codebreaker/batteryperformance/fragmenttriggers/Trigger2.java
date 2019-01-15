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


import performance.cleaner.codebreaker.batteryperformance.fragments.SlideFragment1;
import performance.cleaner.codebreaker.batteryperformance.fragments.SlideFragment2;
import performance.cleaner.codebreaker.batteryperformance.R;


/**
 * Created by Ratan on 7/27/2015.
 */
public class Trigger2 extends Fragment {

    public static TabLayout tabLayout_2;
    public static ViewPager viewPager_2;
    public static int int_items = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.side_layout, null);
        tabLayout_2 = (TabLayout) x.findViewById(R.id.tabs);
        viewPager_2 = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager_2.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager_2.setOffscreenPageLimit(3);


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */


        tabLayout_2.post(new Runnable() {
            @Override
            public void run() {
                tabLayout_2.setupWithViewPager(viewPager_2);
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
        public Fragment getItem(int position)
        {

            switch (position)
            {
                case 0:
                    return new SlideFragment2();

                case 1:
                    return new SlideFragment1();
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return int_items;
        }
    }
}
