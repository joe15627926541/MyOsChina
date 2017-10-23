package com.example.asdf.myoschina.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ba0ch3ng on 2017/6/13.
 */
public class MyDrawerLayout extends android.support.v4.widget.DrawerLayout {
    public MyDrawerLayout(Context context) {
        super(context);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return false;
//    }

}
