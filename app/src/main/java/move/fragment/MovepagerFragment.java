package move.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import move.XmlParse.DetailMoveXmlParser;
import move.XmlParse.MovePagerXmlParser;
import move.domain.DetailMoveInfo;

/**
 * Created by ba0ch3ng on 2017/7/19.
 */

public class MovepagerFragment extends BaseFragment {
    private ArrayList<DetailMoveInfo> mList;
    private CircleImageView profile_image_detail;
    private TextView tv_author_detail;
    private TextView tv_time_detail;
    private TextView tv_content_move;
//    private ImageView iv_like_detail;
    private TextView tv_count_detail;
    private LinearLayout ll_comment_detail;
    private TextView tv_like_detail;
    private LinearLayout ll_load;
    private FrameLayout fl_detail;
    private ArrayList<DetailMoveInfo> detailmoveinfos = null;
    private boolean isTure;
    private String[]str={"joe,","helena,","john"};
    private Receiver receiver;


    @Override
    public View onCreateSuccessView() {
        View inflate = UIUtils.inflate(R.layout.fragment_detail);
        profile_image_detail = (CircleImageView) inflate.findViewById(R.id.profile_image_detail);
        tv_author_detail = (TextView) inflate.findViewById(R.id.tv_author_detail);
        tv_time_detail = (TextView) inflate.findViewById(R.id.tv_time_detail);
        tv_content_move = (TextView) inflate.findViewById(R.id.tv_content_move);
//        iv_like_detail = (ImageView) inflate.findViewById(R.id.iv_like_detail);
        tv_count_detail = (TextView) inflate.findViewById(R.id.tv_count_detail);
        ll_comment_detail = (LinearLayout) inflate.findViewById(R.id.ll_comment_detail);
        tv_like_detail = (TextView) inflate.findViewById(R.id.tv_like_detail);
        ll_load = (LinearLayout) inflate.findViewById(R.id.ll_load);
        fl_detail = (FrameLayout) inflate.findViewById(R.id.fl_detail);
        final DetailMoveInfo detailMoveInfo = mList.get(0);
        Glide.with(UIUtils.getContext()).load(detailMoveInfo.getPortrait()).into(profile_image_detail);
        tv_author_detail.setText(detailMoveInfo.getAuthor());
        tv_time_detail.setText(detailMoveInfo.getPubDate());
        tv_content_move.setText(detailMoveInfo.getBody());
        tv_count_detail.setText(detailMoveInfo.getCommentCount() + "");
        ll_load.setVisibility(View.VISIBLE);


        receiver=new Receiver();
        IntentFilter intentfilter=new IntentFilter();
        intentfilter.addAction("BROADCAST_ACTION");
        UIUtils.getContext().registerReceiver(receiver,intentfilter);
//
//        iv_like_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isTure){
//                    isTure=false;
//                    iv_like_detail.setImageResource(R.drawable.ic_unlike);
//                    if(detailMoveInfo.getLikeCount()>0){
//                        tv_like_detail.setText(str[0] + str[1] + str[2]);
//                    }else{
//                        ll_comment_detail.setVisibility(View.GONE);
//                    }
//                }else{
//                    isTure=true;
//                    iv_like_detail.setImageResource(R.drawable.ic_likeed);
//                    AnimatorSet animatorSet = new AnimatorSet();//组合动画
//                    ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_like_detail, "scaleX", 1, 1.1f, 1);
//                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_like_detail, "scaleY", 1, 1.1f, 1);
//                    animatorSet.setDuration(2000);
//                    animatorSet.play(animatorX).with(animatorY);//两个动画同时开始
//                    animatorSet.start();
//                    ll_comment_detail.setVisibility(View.VISIBLE);
//                    if(detailMoveInfo.getLikeCount()>0){
//                        tv_like_detail.setText("joe,"+str[0] + str[1] + str[2]);
//                    }else{
//                        tv_like_detail.setText("joe");
//                    }
//
//                }
//
//
//            }
//        });

        //请求评论的数据
        FragmentManager fm= getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        CommentFragment commentFragment = new CommentFragment();
        fragmentTransaction.replace(R.id.fl_detail,commentFragment).commit();
        /*commentFragment.loadData();*/
        return inflate;
    }


    @Override
    public LoadingPage.ResultState onLoad() {
        //获取activity传入的位置值
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("Detail", getActivity().MODE_PRIVATE);
        int position = sp.getInt("DetailPosition", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(UIUtils.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.oschina.net/action/api/tweet_detail?id=" + (position + 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                InputStream is = new ByteArrayInputStream(s.getBytes());
                mList = MovePagerXmlParser.MovePagerxmlParser(is);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "服务器连接出错，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
        SystemClock.sleep(500);
        return check(mList);
    }
    class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ll_load.setVisibility(View.GONE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            UIUtils.getContext().unregisterReceiver(receiver);
        }
    }


}
