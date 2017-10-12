package move.holder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import comprehensive.domain.newsInfo;
import de.hdodenhof.circleimageview.CircleImageView;
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
    private int likeCount;


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
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(isTure){
                  isTure=false;
                  iv_like.setImageResource(R.drawable.ic_unlike);
                  if(likeCount>0){
                      tv_like.setText(str[0] + str[1] + str[2]);
                  }else{
                    ll_comment.setVisibility(View.GONE);
                  }
              }else{
                  ll_comment.setVisibility(View.VISIBLE);
                  isTure=true;
                  iv_like.setImageResource(R.drawable.ic_likeed);
                  AnimatorSet animatorSet = new AnimatorSet();//组合动画
                  ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_like, "scaleX", 1, 1.1f, 1);
                  ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_like, "scaleY", 1, 1.1f, 1);
                  animatorSet.setDuration(1000);
                  animatorSet.play(animatorX).with(animatorY);//两个动画同时开始
                  animatorSet.start();
                  if(likeCount>0){
                      tv_like.setText("joe,"+str[0] + str[1] + str[2]);
                  }else{
                      tv_like.setText("joe");
                  }

              }


            }
        });
        return inflate;
    }

    @Override
    public void refreshView(recentmoveInfo data) {
        likeCount = data.likeCount;
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


    }
}
