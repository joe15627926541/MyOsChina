package convery.fragment;

import android.view.View;

import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import convery.holder.FriendHolder;
import move.Protocol.HotMoveProtocol;
import move.domain.recentmoveInfo;
import move.fragment.HotMoveFragment;
import move.holder.HotMoveHolder;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class FriendFragment extends BaseFragment {
    ArrayList<recentmoveInfo> mList = null;
    private ArrayList<recentmoveInfo> moreData;
    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        RecentMoveAdapter recentMoveAdapter = new RecentMoveAdapter(mList);
        myListView.setAdapter(recentMoveAdapter);
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotMoveProtocol protocol = new HotMoveProtocol();
        mList = protocol.getData(0);
        return check(mList);
    }
    class RecentMoveAdapter extends MyBaseAdapter<recentmoveInfo> {
        public RecentMoveAdapter(ArrayList<recentmoveInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<recentmoveInfo> getHolder(int position) {
            return new FriendHolder();
        }

        @Override
        public ArrayList<recentmoveInfo> onLoadMore() {

            HotMoveProtocol protocol = new HotMoveProtocol();
            moreData = protocol.getData(getListSize());
            if (moreData == null) {
            }
            return moreData;
        }

    }


}
