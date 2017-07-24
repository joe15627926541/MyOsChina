package convery.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;

import convery.activity.FindFriendActivity;
import convery.activity.FriendActivity;
import convery.activity.MyActivity;
import convery.activity.NewActivity;
import convery.activity.ShakeActivity;

/**
 * Created by asdf on 2017/6/2.
 */

public class ConVeryFragment extends BaseFragment {
    private LinearLayout ll_friend;
    private LinearLayout ll_find_person;
    private LinearLayout ll_activity;
    private LinearLayout ll_scan;
    private LinearLayout ll_shake;


    @Override
    public View onCreateSuccessView() {
        View inflate = UIUtils.inflate(R.layout.fragment_convery);
        ll_friend=(LinearLayout)inflate.findViewById(R.id.ll_friend);
        ll_find_person=(LinearLayout)inflate.findViewById(R.id.ll_find_person);
        ll_activity=(LinearLayout)inflate.findViewById(R.id.ll_activity);
        ll_scan=(LinearLayout)inflate.findViewById(R.id.ll_scan);
        ll_shake=(LinearLayout)inflate.findViewById(R.id.ll_shake);
        initListener();
        return inflate;
    }

    private void initListener() {
        ll_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FriendActivity.class);
                startActivity(intent);
            }
        });
        ll_find_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FindFriendActivity.class);
                startActivity(intent);
            }
        });
        ll_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);
            }
        });
        ll_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent intent = new Intent(getActivity(), NewActivity.class);
                startActivity(intent);
            }
        });
        ll_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ShakeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public ResultState onLoad() {
        return ResultState.STATE_SUCCESS;
    }



}
