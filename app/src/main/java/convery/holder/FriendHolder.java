package convery.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FriendHolder extends BaseHolder<recentmoveInfo> {
    private CircleImageView profile_image_friend;
    private TextView tv_author_friend;
    private TextView tv_message_friend;
    private TextView tv_time_friend;




    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.item_friend);
        profile_image_friend=(CircleImageView)inflate.findViewById(R.id.profile_image_friend);
        tv_author_friend=(TextView)inflate.findViewById(R.id.tv_author_friend);
        tv_message_friend=(TextView)inflate.findViewById(R.id.tv_message_friend);
        tv_time_friend=(TextView)inflate.findViewById(R.id.tv_time_friend);
        return inflate;
    }

    @Override
    public void refreshView(recentmoveInfo data) {
        if(!TextUtils.isEmpty(data.portrait)){
            Glide.with(UIUtils.getContext()).load(data.portrait).into(profile_image_friend);
        }
        tv_author_friend.setText(data.author);
        tv_message_friend.setText(data.body);
        tv_time_friend.setText(data.pubDate);
    }
}
