package com.example.asdf.myoschina.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.asdf.myoschina.util.UIUtils;

/**
 * 自定义listview
 * 
 * @author Kevin
 * 
 */
public class MyListView extends ListView {
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyListView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		this.setCacheColorHint(Color.TRANSPARENT);// 去掉滑动时偶现的黑色背景
		this.setSelector(new ColorDrawable());// item自带的点击效果改为透明色,相当于去掉了默认点击的背景色
	}


}
