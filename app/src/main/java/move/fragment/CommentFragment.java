package move.fragment;

import android.content.Intent;
import android.view.View;

import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import move.Protocol.CommentProtocol;
import move.domain.CommentInfo;
import move.holder.CommentHolder;

/**
 * Created by ba0ch3ng on 2017/7/20.
 */

public class CommentFragment extends BaseFragment {

    ArrayList<CommentInfo> mList = null;
    private ArrayList<CommentInfo> moreData;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        CommentAdapter commentMoveAdapter = new CommentAdapter(mList);
        myListView.setAdapter(commentMoveAdapter);

        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CommentProtocol protocol = new CommentProtocol();
        mList = protocol.getData(0);

        Intent intent=new Intent();
        intent.setAction("BROADCAST_ACTION");
        UIUtils.getContext().sendBroadcast(intent);
        return check(mList);
    }
    class CommentAdapter extends MyBaseAdapter<CommentInfo> {
        public CommentAdapter(ArrayList<CommentInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<CommentInfo> getHolder(int position) {
            return new CommentHolder();
        }

        @Override
        public ArrayList<CommentInfo> onLoadMore() {

            CommentProtocol protocol = new CommentProtocol();
            moreData = protocol.getData(getListSize());
            if (moreData == null) {
            }
            return moreData;
        }

    }
}
