package convery.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.MyListView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import convery.activity.UserActivity;
import convery.domain.FindFriendInfo;
import convery.holder.FindHolder;
import convery.xmlParse.FriendXmlParser;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FindFriendFragment extends BaseFragment {
    ArrayList<FindFriendInfo> findfriendinfos = null;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        RecentMoveAdapter recentMoveAdapter = new RecentMoveAdapter(findfriendinfos);
        myListView.setAdapter(recentMoveAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),UserActivity.class);
                FindFriendInfo findFriendInfo = findfriendinfos.get(position);
                intent.putExtra("Info",findFriendInfo);
                startActivity(intent);

            }
        });

        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("find_content", UIUtils.getContext().MODE_PRIVATE);
        final String content = sp.getString("content", "");
        InputStream is = new ByteArrayInputStream(content.getBytes());
        findfriendinfos = FriendXmlParser.XmlfriendParser(is);
        SystemClock.sleep(100);
        return check(findfriendinfos);
    }

    class RecentMoveAdapter extends MyBaseAdapter<FindFriendInfo> {
        public RecentMoveAdapter(ArrayList<FindFriendInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<FindFriendInfo> getHolder(int position) {
            return new FindHolder();
        }

        @Override
        public ArrayList<FindFriendInfo> onLoadMore() {


            return null;
        }

    }
}
