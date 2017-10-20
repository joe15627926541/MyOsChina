package com.example.asdf.myoschina.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.fragment.BlogFragment;
import com.example.asdf.myoschina.fragment.CommunicationFragment;
import com.example.asdf.myoschina.fragment.InformationFragment;
import com.example.asdf.myoschina.fragment.SoftwareFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.BaseActivity;
import com.example.asdf.myoschina.view.PagerTab;

import java.util.ArrayList;

/**
 * Created by ba0ch3ng on 2017/6/13.
 */

public class SearchPagerActivity extends BaseActivity {
    private EditText edt_search;
    private ImageView search_delete;
    private PagerTab MyPagerTab;
    private ViewPager viewpager;
    private ArrayList<BaseFragment> NullFragments;
    private BaseFragment fragment;
    private MyPagerAdapter myPagerAdapter;
    private String[] mTabNames;// 页签名称集合
    private int PagerPosition;


    /*
    * 声明四个标签
    * */
    private SoftwareFragment softwarefragment;
    private CommunicationFragment communicationfragment;
    private BlogFragment blogfragment;
    private InformationFragment informationfragment;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        init();
        initOnClickListener();
    }

    private void initOnClickListener() {
        //点击监听事件
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = edt_search.getText().toString().trim();
                if (!content.equals("") && content != null) {
                    search_delete.setVisibility(View.VISIBLE);
                } else {
                    search_delete.setVisibility(View.GONE);
                }



            }
        });
         search_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
            }
        });
        edt_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(getApplication(),":点击了完成，并收起了输入面板",Toast.LENGTH_LONG).show();
                    String SearchContent = edt_search.getText().toString().trim();
                    if (!TextUtils.isEmpty(SearchContent)) {
                            edit.putBoolean("IsDefault", false);
                            edit.putString("content", SearchContent);
                            edit.putInt("PagerPosition",PagerPosition);
                            edit.commit();
                            fragment = NullFragments.get(PagerPosition);
                            fragment.loadData();

                    } else {
                        Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }

        });


    }

    private void init() {
        edt_search = (EditText) findViewById(R.id.edt_search);
        search_delete = (ImageView) findViewById(R.id.search_delete);
        MyPagerTab = (PagerTab) findViewById(R.id.MyPagerTab);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        SharedPreferences sp = getSharedPreferences("Default", MODE_PRIVATE);
        edit = sp.edit();
        edit.putBoolean("IsDefault", true).commit();
        if (softwarefragment == null) {
            softwarefragment = new SoftwareFragment();
        }
        if (communicationfragment == null) {
            communicationfragment = new CommunicationFragment();
        }
        if (blogfragment == null) {
            blogfragment = new BlogFragment();
        }
        if (informationfragment == null) {
            informationfragment = new InformationFragment();
        }
        NullFragments = new ArrayList<>();
        NullFragments.add(softwarefragment);
        NullFragments.add(communicationfragment);
        NullFragments.add(blogfragment);
        NullFragments.add(informationfragment);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myPagerAdapter);
        MyPagerTab.setViewPager(viewpager);
        MyPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String SearchContent = edt_search.getText().toString().trim();
                PagerPosition=position;
                if (!TextUtils.isEmpty(SearchContent)) {
                    edit.putBoolean("IsDefault", false);
                    edit.putString("content", SearchContent);
                    edit.putInt("PagerPosition",PagerPosition);
                    edit.commit();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }

        // 加载每个页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        // 返回Fragment对象,将onCreateView方法的返回view填充给ViewPager
        // 此方法类似instantiateItem
        @Override
        public Fragment getItem(int position) {
            return NullFragments.get(position);
        }

        // 返回item个数
        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }

}