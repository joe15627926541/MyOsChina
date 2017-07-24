package com.example.asdf.myoschina.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asdf.myoschina.Protocol.CommunicationProtocol;
import com.example.asdf.myoschina.Protocol.SoftWareProtocol;
import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.domain.SoftWareInfo;
import com.example.asdf.myoschina.domain.info;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.holder.SoftHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;
import com.example.asdf.myoschina.view.MyListView;
import com.google.gson.Gson;

import java.util.ArrayList;

import comprehensive.Protocol.BlogProtocol;
import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/13.
 */

public class CommunicationFragment extends BaseFragment {
    private boolean booleanExtra;
    private String content;
    private ArrayList<SoftWareInfo>mList;
    private ArrayList<SoftWareInfo> moreData;

    @Override
    public View onCreateSuccessView() {
        System.out.println("-----加载成功布局");
        MyListView myListView = new MyListView(UIUtils.getContext());
        if (booleanExtra) {
            myListView.setVisibility(View.GONE);
        } else {
            System.out.println("-----VISIBLE");
            myListView.setVisibility(View.VISIBLE);
            myListView.setAdapter(new SoftwareAdapter(mList));
            System.out.println("-----adapter");
//            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if(mList!=null&&mList.size()>0){
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(userBeanList.get(position).getDetailUrl());
//                        intent.setData(content_url);
//                        startActivity(intent);
//                    }else{
//                        return;
//                    }
//
//                }
//            });
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
        content = sp.getString("content", "毛主席");
        System.out.println("-----搜索内容"+content);
        System.out.println("-----"+booleanExtra);
        if (booleanExtra) {
            return ResultState.STATE_SUCCESS;
        } else {
            System.out.println("-----请求数据");
            CommunicationProtocol protocol = new CommunicationProtocol();
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
            CommunicationProtocol protocol = new CommunicationProtocol();
            moreData = protocol.getData(getListSize());
            if(moreData==null){
            }
            return moreData;
        }
    }
}
