package fiu.com.skillcourt.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fiu.com.skillcourt.R;
import fiu.com.skillcourt.interfaces.FragmentListener;
import fiu.com.skillcourt.ui.statistics.StatisticsFragment;

/**
 * Created by pedrocarrillo on 9/10/16.
 */

public class StatsActivity extends AppCompatActivity implements FragmentListener {

    protected Toolbar toolbar;

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.main_content, fragment, addToBackStack);
    }

    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void setNavigationToolbar() {
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        public StatisticsFragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return StatisticsFragment.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return StatisticsFragment.newInstance();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return StatisticsFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator

        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}

