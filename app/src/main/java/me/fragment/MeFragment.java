package me.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asdf on 2017/6/2.
 */

public class MeFragment extends BaseFragment {
    private ImageView iv_id;
    private CircleImageView profile_image_me;
    private ImageView iv_sex;
    private TextView tv_user;
    private ListView listview_me;
    private LinearLayout ll_score;
    private LinearLayout ll_collect;
    private LinearLayout ll_care;
    private LinearLayout ll_fans;
    private RelativeLayout rl_user;
    //listview的item
    private TextView tv_message;
    private ImageView iv_message;
    private String[] str = {"消息", "博客", "便签", "团队"};
    private int[] Ids = {R.drawable.icon_my_message, R.drawable.icon_my_blog, R.drawable.icon_my_note, R.drawable.icon_my_team};


    @Override
    public View onCreateSuccessView() {
        View inflate = UIUtils.inflate(R.layout.fragment_me);
        iv_id = (ImageView) inflate.findViewById(R.id.iv_id);
        profile_image_me = (CircleImageView) inflate.findViewById(R.id.profile_image_me);
        iv_sex = (ImageView) inflate.findViewById(R.id.iv_sex);
        tv_user = (TextView) inflate.findViewById(R.id.tv_user);
        ll_score = (LinearLayout) inflate.findViewById(R.id.ll_score);
        ll_collect = (LinearLayout) inflate.findViewById(R.id.ll_collect);
        ll_care = (LinearLayout) inflate.findViewById(R.id.ll_care);
        ll_fans = (LinearLayout) inflate.findViewById(R.id.ll_fans);
        listview_me = (ListView) inflate.findViewById(R.id.listview_me);
        rl_user=(RelativeLayout)inflate.findViewById(R.id.rl_user);
        listview_me.setAdapter(new Myadapter());
        initListener();
        return inflate;
    }


    @Override
    public ResultState onLoad() {
        return ResultState.STATE_SUCCESS;
    }

    private void initListener() {
        iv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //二维码
            }
        });
        rl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //头像
            }
        });
        ll_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //积分
            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏
            }
        });
        ll_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注
            }
        });
        ll_fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //粉丝
            }
        });
        listview_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview的点击事件
            }
        });

    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(UIUtils.getContext(), R.layout.item_me, null);
            tv_message = (TextView) inflate.findViewById(R.id.tv_message);
            iv_message = (ImageView) inflate.findViewById(R.id.iv_message);
            tv_message.setText(str[position]);
            iv_message.setImageResource(Ids[position]);
            return inflate;
        }
    }

}
