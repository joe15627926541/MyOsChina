package com.example.asdf.myoschina.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * 自定义Application
 * 
 * @author Kevin
 * 
 */
public class BaseApplication extends Application {

	private static Context context;
	private static int mainThreadId;
	private static Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		mainThreadId = android.os.Process.myTid();// 获取当前主线程id
		handler = new Handler();
		PushAgent mPushAgent = PushAgent.getInstance(this);
      //注册推送服务，每次调用register方法都会回调该接口
		mPushAgent.register(new IUmengRegisterCallback() {

			@Override
			public void onSuccess(String deviceToken) {
				//注册成功会返回device token
				System.out.println("----------友盟推送"+deviceToken);
			}

			@Override
			public void onFailure(String s, String s1) {

			}
		});

	}

	public static Context getContext() {
		return context;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}

	public static Handler getHandler() {
		return handler;
	}
}
