package convery.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import convery.domain.MyActivityInfo;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class MyActivityHolder extends BaseHolder<MyActivityInfo> {
     private ImageView iv_bg;
     private TextView tv_title_myactivity;
     private TextView tv_time_myactivity;
     private TextView tv_space_myactivity;



    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.item_myactivity);
        iv_bg=(ImageView)inflate.findViewById(R.id.iv_bg);
        tv_title_myactivity=(TextView)inflate.findViewById(R.id.tv_title_myactivity);
        tv_time_myactivity=(TextView)inflate.findViewById(R.id.tv_time_myactivity);
        tv_space_myactivity=(TextView)inflate.findViewById(R.id.tv_space_myactivity);
        return inflate;
    }

    @Override
    public void refreshView(MyActivityInfo data) {
         if(!TextUtils.isEmpty(data.cover)){
             Glide.with(UIUtils.getContext()).load(data.cover).into(iv_bg);
         }
        tv_title_myactivity.setText(data.title);
        tv_time_myactivity.setText(data.startTime);
        tv_space_myactivity.setText(data.spot);
    }
}
