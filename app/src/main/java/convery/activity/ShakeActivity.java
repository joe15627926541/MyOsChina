package convery.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.util.util;

import java.util.ArrayList;

import comprehensive.Protocol.NewsProtocol;
import comprehensive.activity.DetailPagerActivity;
import comprehensive.domain.newsInfo;


/**
 * Created by ba0ch3ng on 2017/6/22.
 */

public class ShakeActivity extends Activity implements SensorEventListener {
    private TextView shake_back;
    private ImageView iv_shake;
    private ProgressBar progressbar;
    private ArrayList<newsInfo> mList = null;
    private  NewsProtocol protocol;
    /*
    * 弹出的信息的弹窗
    *
    * */
    private LinearLayout ll_message;
    private TextView tv_title_shake;
    private TextView tv_content_shake;
    private TextView tv_author_shake;
    private TextView tv_commentCount_shake;
    private TextView tv_time_shake;
    private ImageView iv_show;
    /*
    * 摇晃传感器
    *
    * */
    private SensorManager mSensorManager;
    //传感器
    private Sensor mSensor;
    //速度阀值
    private int mSpeed = 3000;
    //时间间隔
    private int mInterval = 50;
    //上一次摇晃的时间
    private long LastTime;
    //上一次的x、y、z坐标
    private float LastX, LastY, LastZ;
    private int position;
    private newsInfo newsInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        init();
        shake_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // 跳转到详情页
                Intent intent = new Intent(ShakeActivity.this, DetailPagerActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("type", constansts.NEWSTYPE);
                intent.putExtra("Info",mList.get(position));
                intent.putExtra("Isshake",true);
                System.out.println("------position:"+position+"-----type:"+constansts.NEWSTYPE+"------newsInfo"+newsInfo);
                startActivity(intent);
            }
        });

    }

    private void init() {
        shake_back = (TextView) findViewById(R.id.shake_back);
        iv_shake = (ImageView) findViewById(R.id.iv_shake);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        ll_message=(LinearLayout)findViewById(R.id.ll_message);
        tv_title_shake=(TextView)findViewById(R.id.tv_title_shake);
        tv_content_shake=(TextView)findViewById(R.id.tv_content_shake);
        tv_author_shake=(TextView)findViewById(R.id.tv_author_shake);
        tv_commentCount_shake=(TextView)findViewById(R.id.tv_commentCount_shake);
        tv_time_shake=(TextView)findViewById(R.id.tv_time_shake);
        iv_show=(ImageView)findViewById(R.id.iv_show);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long NowTime = System.currentTimeMillis();
        if ((NowTime - LastTime) < mInterval)
            return;
        //将NowTime赋给LastTime
        LastTime = NowTime;
        //获取x,y,z
        float NowX = event.values[0];
        float NowY = event.values[1];
        float NowZ = event.values[2];
        //计算x,y,z变化量
        float DeltaX = NowX - LastX;
        float DeltaY = NowY - LastY;
        float DeltaZ = NowZ - LastZ;
        //赋值
        LastX = NowX;
        LastY = NowY;
        LastZ = NowZ;
        //计算
        double NowSpeed = Math.sqrt(DeltaX * DeltaX + DeltaY * DeltaY + DeltaZ * DeltaZ) / mInterval * 10000;
        //判断
        if (NowSpeed >= mSpeed) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);

            //展示动画
            progressbar.setVisibility(View.VISIBLE);
            //手机来回晃动的动画
            float curTranslationX =iv_shake.getTranslationX();
            ObjectAnimator animator = ObjectAnimator.ofFloat(iv_shake, "translationX", curTranslationX, -80f, 80f,curTranslationX);
            animator.setDuration(1000);
            animator.start();

            //请求数据，并以弹窗格式展示
            if (protocol == null) {
                protocol = new NewsProtocol();
            }
            mList = protocol.getData(4);
            position = (int) (Math.random() * 20);
            if (mList != null && mList.size() > 0) {
                newsInfo = mList.get(position);
                System.out.println("------集合不为空");
                progressbar.setVisibility(View.GONE);
                ll_message.setVisibility(View.VISIBLE);
                iv_show.setVisibility(View.VISIBLE);
                tv_title_shake.setText(newsInfo.title);
                tv_content_shake.setText(newsInfo.body);
                tv_author_shake.setText("作者:"+newsInfo.author);
                tv_commentCount_shake.setText("评论:"+newsInfo.commentCount);
                tv_time_shake.setText("时间:"+newsInfo.pubDate);
            }else{
                Toast.makeText(getApplicationContext(),"请求不到数据，请稍后再试",Toast.LENGTH_SHORT).show();
                return;
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
