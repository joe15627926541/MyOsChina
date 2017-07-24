package comprehensive.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import java.util.ArrayList;

import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class NewsHolder extends BaseHolder<newsInfo> {
    private TextView title;
    private TextView content;
    private TextView author;
    private TextView time;
    private TextView commentCount;
    private ImageView iv_clock;
    private ImageView iv_icon;
    private ImageView iv_today;




    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.fragment_news_item_listview);
        title=(TextView)inflate.findViewById(R.id.tv_title);
        content=(TextView)inflate.findViewById(R.id.tv_content);
        author=(TextView)inflate.findViewById(R.id.tv_author);
        time=(TextView)inflate.findViewById(R.id.tv_time);
        commentCount=(TextView)inflate.findViewById(R.id.tv_commentCount);
        iv_clock=(ImageView)inflate.findViewById(R.id.iv_clock);
        iv_icon=(ImageView)inflate.findViewById(R.id.iv_icon);
        iv_today=(ImageView)inflate.findViewById(R.id.iv_today);
        return inflate;
    }

    @Override
    public void refreshView(newsInfo data) {
        if(data!=null){
            title.setText(data.title);
            content.setText(data.body);
            author.setText(data.author);
            time.setText(data.pubDate);
            commentCount.setText(data.commentCount+"");
            iv_today.setVisibility(View.GONE);
        }
    }

}
