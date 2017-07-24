package convery.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.AdapterView;

import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import comprehensive.Protocol.HotProtocol;
import comprehensive.activity.DetailPagerActivity;
import comprehensive.domain.newsInfo;
import comprehensive.fragment.HotFragment;
import comprehensive.holder.NewsHolder;
import convery.Protocol.MyActivityProtocol;
import convery.domain.MyActivityInfo;
import convery.holder.MyActivityHolder;

/**
 * Created by ba0ch3ng on 2017/7/23.
 */

public class RecentActivityFragment extends BaseFragment {
    private ArrayList<MyActivityInfo>mList;
    private ArrayList<MyActivityInfo>moreData;
    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new ComPreHensiveAdapter(mList));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailPagerActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("type", constansts.ActivityTYPE);
                intent.putExtra("Info",mList.get(position%20));
                intent.putExtra("Isshake",false);
                startActivity(intent);
            }
        });
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {

        MyActivityProtocol protocol = new MyActivityProtocol();
        mList = protocol.getData(0);
        return check(mList);
    }

    class ComPreHensiveAdapter extends MyBaseAdapter<MyActivityInfo> {

        public ComPreHensiveAdapter(ArrayList<MyActivityInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<MyActivityInfo> getHolder(int position) {
            return new MyActivityHolder();

        }

        @Override
        public ArrayList<MyActivityInfo> onLoadMore() {

            MyActivityProtocol protocol = new MyActivityProtocol();
            moreData = protocol.getData(getListSize());
            if(moreData==null){
            }
            return moreData;
        }

    }


}
