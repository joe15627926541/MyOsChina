package com.example.asdf.myoschina.fragment;

import android.content.SharedPreferences;
import android.view.View;

import com.example.asdf.myoschina.Protocol.SoftWareProtocol;
import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.domain.SoftWareInfo;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.holder.SoftHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;


/**
 * Created by ba0ch3ng on 2017/6/13.
 */

public class SoftwareFragment extends BaseFragment {
    private boolean booleanExtra;
    private String content;
    private ArrayList<SoftWareInfo>mList;
    private ArrayList<SoftWareInfo> moreData;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        if (!booleanExtra) {
            System.out.println("++++加载布局"+booleanExtra);
            myListView.setAdapter(new SoftwareAdapter(mList));
        }else{
            System.out.println("++++未加载布局"+booleanExtra);
        }


        return myListView;


    }

    @Override
    public ResultState onLoad() {
       /*
       * 获取activity传入的值
       *
       * */
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("Default", UIUtils.getContext().MODE_PRIVATE);
        booleanExtra = sp.getBoolean("IsDefault", true);
        content = sp.getString("content", "c语言");
        if (booleanExtra) {
            return ResultState.STATE_SUCCESS;
        } else {
            SoftWareProtocol protocol = new SoftWareProtocol();
            mList= protocol.getData(0);
            return check(mList);
        }
    }

    class SoftwareAdapter extends MyBaseAdapter<SoftWareInfo> {


        public SoftwareAdapter(ArrayList<SoftWareInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<SoftWareInfo> getHolder(int position) {
            return new SoftHolder();
        }

        @Override
        public ArrayList<SoftWareInfo> onLoadMore() {
            SoftWareProtocol protocol = new SoftWareProtocol();
            moreData = protocol.getData(getListSize());
            if(moreData==null){
            }
            return moreData;
        }
    }
}
