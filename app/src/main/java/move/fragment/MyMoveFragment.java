package move.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import move.Protocol.HotMoveProtocol;
import move.Protocol.MyMoveProtocol;
import move.activity.MoveDetailActivity;
import move.domain.recentmoveInfo;
import move.holder.MyMoveHolder;
import move.holder.RecentMoveHolder;

/**
 * Created by ba0ch3ng on 2017/7/14.
 */

public class MyMoveFragment extends BaseFragment {
    ArrayList<recentmoveInfo> mList = null;
    private ArrayList<recentmoveInfo> moreData;


    @Override
    public View onCreateSuccessView() {

        MyListView myListView = new MyListView(UIUtils.getContext());
        RecentMoveAdapter recentMoveAdapter = new RecentMoveAdapter(mList);
        myListView.setAdapter(recentMoveAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MoveDetailActivity.class);

                SharedPreferences sp = UIUtils.getContext().getSharedPreferences("Detail", getActivity().MODE_PRIVATE);
                sp.edit().putInt("DetailPosition",position%20).commit();
                startActivity(intent);

            }
        });
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        MyMoveProtocol protocol = new MyMoveProtocol();
        mList = protocol.getData(0);
        return check(mList);
    }

    class RecentMoveAdapter extends MyBaseAdapter<recentmoveInfo> {
        public RecentMoveAdapter(ArrayList<recentmoveInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<recentmoveInfo> getHolder(int position) {
            return new MyMoveHolder();
        }

        @Override
        public ArrayList<recentmoveInfo> onLoadMore() {
            MyMoveProtocol protocol = new MyMoveProtocol();
            moreData = protocol.getData(getListSize());
            if (moreData == null) {
            }
            return moreData;
        }

    }


}
