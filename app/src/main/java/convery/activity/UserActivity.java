package convery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;

import comprehensive.domain.newsInfo;
import convery.domain.FindFriendInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class UserActivity extends Activity {
    private FindFriendInfo info;
    private CircleImageView profile_image_me;
    private ImageView iv_sex;
    private TextView tv_user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        info = (FindFriendInfo) intent.getSerializableExtra("Info");
        profile_image_me=(CircleImageView)findViewById(R.id.profile_image_me);
        iv_sex=(ImageView)findViewById(R.id.iv_sex);
        tv_user=(TextView)findViewById(R.id.tv_user);
        if(!TextUtils.isEmpty(info.getPortrait())){
            Glide.with(getApplicationContext()).load(info.getPortrait()).into(profile_image_me);
        }
        if(info.getGender().equals("女")){
            iv_sex.setImageResource(R.drawable.userinfo_icon_female);
        }else{
            iv_sex.setImageResource(R.drawable.userinfo_icon_male);
        }
        tv_user.setText(info.getName());


    }
}
