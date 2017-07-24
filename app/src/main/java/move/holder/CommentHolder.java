package move.holder;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import move.domain.CommentInfo;

/**
 * Created by ba0ch3ng on 2017/7/20.
 */

public class CommentHolder extends BaseHolder<CommentInfo> {
    private CircleImageView profile_image_comment;
    private TextView tv_author_comment;
    private TextView tv_content_comment;
    private TextView tv_time_comment;
    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.comment_item);
        profile_image_comment=(CircleImageView)inflate.findViewById(R.id.profile_image_comment);
        tv_author_comment=(TextView)inflate.findViewById(R.id.tv_author_comment);
        tv_content_comment=(TextView)inflate.findViewById(R.id.tv_content_comment);
        tv_time_comment=(TextView)inflate.findViewById(R.id.tv_time_comment);
        return inflate;
    }

    @Override
    public void refreshView(CommentInfo data) {
        Glide.with(UIUtils.getContext()).load(data.portrait).into(profile_image_comment);
        tv_author_comment.setText(data.author);
        tv_content_comment.setText(data.content);
        tv_time_comment.setText(data.pubDate);
    }
}
