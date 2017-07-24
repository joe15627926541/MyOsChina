package comprehensive.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.viewpagerindicator.TabPageIndicator;


import java.util.ArrayList;

import comprehensive.view.MyViewPager;


/**
 * Created by asdf on 2017/6/2.
 */

public class ComPreHenSiveFragment extends Fragment {
    private TabPageIndicator indicator;
    private ViewPager pager;
    private String[] pagername = {"咨询", "热点", "博客", "推荐"};
    private NewsFragment newsfragment;
    private HotFragment hotfragment;
    private BlogFragment blogfragment;
    private RecommendFragment recommendfragment;
    private ArrayList<BaseFragment> Fragments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        * 添加这个代码片段TabPageIndicator样式可以展示
        *
        * */
        Context context = new ContextThemeWrapper(getActivity(),

                R.style.MyPageIndicatorDefaults);

        // clone the inflater using the ContextThemeWrapper

        LayoutInflater localInflater = inflater.cloneInContext(context);

        View view = localInflater.inflate(R.layout.fragment_comprehensive, container,

                false);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        pager = (ViewPager) view.findViewById(R.id.pager);
        init();
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        indicator.setViewPager(pager);


        return view;
    }


    private void init() {
        Fragments = new ArrayList<>();
        if (newsfragment == null) {
            newsfragment = new NewsFragment();
        }
        if (hotfragment == null) {
            hotfragment = new HotFragment();
        }
        if (blogfragment == null) {
            blogfragment = new BlogFragment();
        }
        if (recommendfragment == null) {
            recommendfragment = new RecommendFragment();
        }
        Fragments.add(newsfragment);
        Fragments.add(hotfragment);
        Fragments.add(blogfragment);
        Fragments.add(recommendfragment);


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
            return pagername.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagername[position];
        }

    }


}