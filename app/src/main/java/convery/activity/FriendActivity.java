package convery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.asdf.myoschina.R;

import convery.fragment.FriendFragment;

/**
 * Created by ba0ch3ng on 2017/7/22.
 */

public class FriendActivity extends FragmentActivity {
      private TextView tv_back_friend;
      private FrameLayout fl_friend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        tv_back_friend=(TextView)findViewById(R.id.tv_back_friend);
        fl_friend=(FrameLayout)findViewById(R.id.fl_friend);
        tv_back_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FriendFragment friendFragment = new FriendFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_friend,friendFragment).commit();

    }
}
