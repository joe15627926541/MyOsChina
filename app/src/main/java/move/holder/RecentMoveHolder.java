package move.holder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.provider.Contacts;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.db.DbHelper;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import java.util.ArrayList;

import comprehensive.domain.newsInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import move.db.ClickHelper;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class RecentMoveHolder extends BaseHolder<recentmoveInfo> {
    private CircleImageView profile_image;
    private TextView tv_author;
    private TextView tv_content;
    private TextView tv_time;
    private TextView tv_count;
    private ImageView iv_like;
    private TextView tv_like;
    private LinearLayout ll_comment;
    private String[] str = {"joef,", "helena,", "john"};
    private boolean isTure;
    private ClickHelper helper;
    private SQLiteDatabase db;



    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.fragment_move_item_listview);
        profile_image = (CircleImageView) inflate.findViewById(R.id.profile_image);
        tv_author = (TextView) inflate.findViewById(R.id.tv_author);
        tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        tv_time = (TextView) inflate.findViewById(R.id.tv_time);
        tv_count = (TextView) inflate.findViewById(R.id.tv_count);
        tv_like = (TextView) inflate.findViewById(R.id.tv_like);
        ll_comment = (LinearLayout) inflate.findViewById(R.id.ll_comment);
        iv_like = (ImageView) inflate.findViewById(R.id.iv_like);
        helper = new ClickHelper(UIUtils.getContext());
        db = helper.getWritableDatabase();
        return inflate;
    }

    @Override
    public void refreshView(final recentmoveInfo data) {
        iv_like.setImageResource(R.drawable.ic_unlike);
        ll_comment.setVisibility(View.VISIBLE);


        //
        if(data.portrait!=null&&!TextUtils.isEmpty(data.portrait)){
            Glide
                    .with(UIUtils.getContext())
                    .load(data.portrait)
                    .into(profile_image);
        }
        tv_author.setText(data.author);
        tv_content.setText(data.body);
        tv_time.setText(data.pubDate);
        tv_count.setText(data.commentCount + "");
        if (data.likeCount > 0) {

            ll_comment.setVisibility(View.VISIBLE);
            tv_like.setText(str[0] + str[1] + str[2]);

        } else {
              ll_comment.setVisibility(View.GONE);
              tv_like.setText("");
        }
         //点击某个赞
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTure){
                    //取消点赞   ------》取消点赞的id
                    iv_like.setImageResource(R.drawable.ic_unlike);
                    helper.delete(data.getId(),db);
                    isTure=false;
                    if(data.likeCount>0){
                        tv_like.setText(str[0] + str[1] + str[2]);
                    }else{
                        ll_comment.setVisibility(View.GONE);
                    }
                }else{
                    //点赞   ------》存储点赞的id
                    iv_like.setImageResource(R.drawable.ic_likeed);
                    helper.add(data.getId(),1,db);

                    ll_comment.setVisibility(View.VISIBLE);
                    isTure=true;
                    AnimatorSet animatorSet = new AnimatorSet();//组合动画
                    ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_like, "scaleX", 1, 1.1f, 1);
                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_like, "scaleY", 1, 1.1f, 1);
                    animatorSet.setDuration(1000);
                    animatorSet.play(animatorX).with(animatorY);//两个动画同时开始
                    animatorSet.start();
                    if(data.likeCount>0){
                        tv_like.setText("joe,"+str[0] + str[1] + str[2]);
                    }else{
                        tv_like.setText("joe");
                    }
                }

            }

        });
        ArrayList<Integer> list = helper.query(db);
        if(list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i);
                if (data.getId() == id) {
                    ll_comment.setVisibility(View.VISIBLE);
                    iv_like.setImageResource(R.drawable.ic_likeed);
                    tv_like.setText("joe");
                }

            }

        }

    }
}
