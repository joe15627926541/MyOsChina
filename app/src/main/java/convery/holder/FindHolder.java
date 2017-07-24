package convery.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import convery.domain.FindFriendInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FindHolder extends BaseHolder<FindFriendInfo> {
     private CircleImageView profile_image_find;
     private TextView tv_name;
     private ImageView iv_pic;
     private TextView tv_from;


    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.item_find);
        profile_image_find=(CircleImageView)inflate.findViewById(R.id.profile_image_find);
        tv_name=(TextView)inflate.findViewById(R.id.tv_name);
        tv_from=(TextView)inflate.findViewById(R.id.tv_from);
        iv_pic=(ImageView)inflate.findViewById(R.id.iv_pic);
        return inflate;
    }

    @Override
    public void refreshView(FindFriendInfo data) {
        if(!TextUtils.isEmpty(data.portrait)){
            Glide.with(UIUtils.getContext()).load(data.portrait).into(profile_image_find);
        }
        tv_name.setText(data.name);
        tv_from.setText(data.from);
        if(data.gender.equals("男")){
            iv_pic.setImageResource(R.drawable.userinfo_icon_male);
        }
        else {
            iv_pic.setImageResource(R.drawable.userinfo_icon_female);
        }
    }
}
