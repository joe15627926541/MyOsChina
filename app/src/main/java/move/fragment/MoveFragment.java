package move.fragment;

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

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;


/**
 * Created by asdf on 2017/6/2.
 */

public class MoveFragment extends Fragment {

    private TabPageIndicator Myindicator;
    private ViewPager Mypager;
    private String[] Mypagername = {"最新动弹", "热门动弹", "我的动弹"};
    private RecentMoveFragment recentmovefragment;
    private HotMoveFragment hotmovefragment;
    private MyMoveFragment mymovefragment;
    private ArrayList<BaseFragment> MyFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        * 添加这个代码片段TabPageIndicator样式可以展示
        *
        * */
        Context context = new ContextThemeWrapper(getActivity(),

                R.style.MyPageIndicatorDefaults);


        LayoutInflater localInflater = inflater.cloneInContext(context);

        View view = localInflater.inflate(R.layout.fragment_move, container,

                false);
        Myindicator = (TabPageIndicator) view.findViewById(R.id.myindicator);
        Mypager = (ViewPager) view.findViewById(R.id.mypager);
        init();
        Mypager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        Myindicator.setViewPager(Mypager);
        return view;
    }

    private void init() {
        MyFragments = new ArrayList<>();
        if (recentmovefragment == null) {
            recentmovefragment = new RecentMoveFragment();
        }
        if (hotmovefragment == null) {
            hotmovefragment = new HotMoveFragment();
        }
        if (mymovefragment == null) {
            mymovefragment = new MyMoveFragment();
        }

        MyFragments.add(recentmovefragment);
        MyFragments.add(hotmovefragment);
        MyFragments.add(mymovefragment);

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragments.get(position);
        }

        @Override
        public int getCount() {
            return Mypagername.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Mypagername[position];
        }

    }


}
