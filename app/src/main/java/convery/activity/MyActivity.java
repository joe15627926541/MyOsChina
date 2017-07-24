package convery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import convery.fragment.MyActivityFragment;
import convery.fragment.RecentActivityFragment;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class MyActivity extends FragmentActivity {
    private TextView tv_back_activity;
    private TabPageIndicator indicator_myactivity;
    private ViewPager pager_myactivity;
    private String[] PageName = {"最近活动", "我的活动"};
    private RecentActivityFragment recentactivityfragment;
    private MyActivityFragment myactivityfragment;
    private ArrayList<BaseFragment> Fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        tv_back_activity = (TextView) findViewById(R.id.tv_back_activity);
        indicator_myactivity = (TabPageIndicator) findViewById(R.id.indicator_myactivity);
        pager_myactivity = (ViewPager) findViewById(R.id.pager_myactivity);
        tv_back_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        pager_myactivity.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        indicator_myactivity.setViewPager(pager_myactivity);


    }

    private void init() {
        Fragments = new ArrayList<>();
        if (recentactivityfragment == null) {
            recentactivityfragment = new RecentActivityFragment();
        }
        if (myactivityfragment == null) {
            myactivityfragment = new MyActivityFragment();
        }
        Fragments.add(recentactivityfragment);
        Fragments.add(myactivityfragment);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragments.get(position);
        }

        @Override
        public int getCount() {
            return PageName.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PageName[position];
        }

    }
}
