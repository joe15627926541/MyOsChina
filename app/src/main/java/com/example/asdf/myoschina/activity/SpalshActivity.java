package com.example.asdf.myoschina.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.asdf.myoschina.R;

/**
 * Created by asdf on 2017/6/2.
 */

public class SpalshActivity extends Activity {
    private LinearLayout ll_spalsh;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalsh);
        ll_spalsh=(LinearLayout)findViewById(R.id.ll_spalsh);
        countDownTimer = new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SpalshActivity.this, MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.activity_left_in,R.anim.activity_left_out);
                finish();
            }
        };
        countDownTimer.start();
        ll_spalsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SpalshActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_left_in,R.anim.activity_left_out);
                countDownTimer.cancel();
                finish();
            }
        });


    }
}
