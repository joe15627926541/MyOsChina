package com.example.asdf.myoschina.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.domain.SoftWareInfo;
import com.example.asdf.myoschina.domain.info;
import com.example.asdf.myoschina.util.UIUtils;

/**
 * Created by ba0ch3ng on 2017/7/12.
 */

public class SoftHolder extends BaseHolder<SoftWareInfo> {
      private TextView tv_title_search;
      private TextView tv_content_search;
      private TextView tv_author_search;
      private TextView tv_time_search;



    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.fragment_search_item_soft);
        tv_title_search=(TextView)inflate.findViewById(R.id.tv_title_search);
        tv_content_search=(TextView)inflate.findViewById(R.id.tv_content_search);
        tv_author_search=(TextView)inflate.findViewById(R.id.tv_author_search);
        tv_time_search=(TextView)inflate.findViewById(R.id.tv_time_search);
        return inflate;
    }

    @Override
    public void refreshView(SoftWareInfo data) {
        tv_title_search.setText(data.title);
        tv_content_search.setText(data.description);
        tv_author_search.setText(data.author);
        tv_time_search.setText(data.pubDate);
    }

}
