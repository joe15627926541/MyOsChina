package comprehensive.holder;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.db.DbHelper;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;

import java.util.ArrayList;

import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/20.
 */

public class BlogHolder extends BaseHolder<newsInfo> {
    private TextView title;
    private TextView content;
    private TextView author;
    private TextView time;
    private TextView commentCount;
    private ImageView iv_clock;
    private ImageView iv_icon;
    private ImageView iv_today;
    private DbHelper helper;
    private SQLiteDatabase db;



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
        helper = new DbHelper(UIUtils.getContext());
        db = helper.getWritableDatabase();

        return inflate;
    }

    @Override
    public void refreshView(newsInfo data) {
        title.setTextColor(Color.BLACK);
        ArrayList<Integer> list = helper.query(db);
        if(data!=null){
            if(list.size()>0){
                for(int i=0;i<list.size();i++){
                    int id=list.get(i);
                    if(data.getId()==id){
                        title.setTextColor(Color.GRAY);
                    }
                }
            }
            title.setText(data.title);
            content.setText(data.body);
            author.setText(data.author);
            time.setText(data.pubDate);
            commentCount.setText(data.commentCount+"");
            int documentType = data.getDocumentType();
            if(documentType==0){
                iv_today.setImageResource(R.drawable.widget_repaste_icon);
            }else{
                iv_today.setImageResource(R.drawable.widget_original_icon);
            }

        }
    }

}
