package com.example.asdf.myoschina.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;

import java.util.List;

/**
 * Created by asdf on 2017/6/2.
 */

public abstract class BaseFragment extends Fragment {
    private  LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(getActivity()) {
            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

        };
        return mLoadingPage;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }
    // 由子类实现创建布局的方法
    public abstract View onCreateSuccessView();

    // 由子类实现加载网络数据的逻辑, 该方法运行在子线程
    public abstract LoadingPage.ResultState onLoad();

    // 开始加载网络数据
    public void loadData() {
        if (mLoadingPage != null) {
            System.out.println("-----mLoadingPage" + mLoadingPage);
            mLoadingPage.loadData();
        } else {
            System.out.println("------mLoadingPage为空");
        }
    }

    /**
     * 校验数据的合法性,返回相应的状态
     *
     * @param data
     * @return
     */
    public LoadingPage.ResultState check(Object data) {
        if (data != null) {
            if (data instanceof List) {//判断当前对象是否是一个集合
                List list = (List) data;
                if (!list.isEmpty()) {//数据不为空,访问成功
                    return ResultState.STATE_SUCCESS;
                } else {//空集合
                    return ResultState.STATE_EMPTY;
                }
            }
        }

        return ResultState.STATE_ERROR;
    }

}
