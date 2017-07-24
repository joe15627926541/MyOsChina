package comprehensive.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.asdf.myoschina.R;
import com.example.asdf.myoschina.activity.MainActivity;
import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.view.LoadingPage;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import comprehensive.Protocol.NewsProtocol;
import comprehensive.activity.DetailPagerActivity;
import comprehensive.domain.newsInfo;
import comprehensive.holder.NewsHolder;

/**
 * Created by asdf on 2017/6/2.
 */

public class NewsFragment extends BaseFragment {
    ArrayList<newsInfo> mList = null;
    private ArrayList<newsInfo> moreData;

     private MyListView myListView;

    @Override
    public View onCreateSuccessView() {
        if (myListView == null) {
            myListView = new MyListView(UIUtils.getContext());
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) myListView.getParent();
            if (parent != null) {
                parent.removeView(myListView);
            }
        }
        myListView.setAdapter(new ComPreHensiveAdapter(mList));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent((MainActivity)getActivity(),DetailPagerActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("type", constansts.NEWSTYPE);
                intent.putExtra("Info",mList.get(position%20));
                intent.putExtra("Isshake",false);
                startActivity(intent);
            }
        });

        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        NewsProtocol protocol = new NewsProtocol();
        mList= protocol.getData(0);
        return check(mList);

    }

    class ComPreHensiveAdapter extends MyBaseAdapter<newsInfo> {

        public ComPreHensiveAdapter(ArrayList<newsInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<newsInfo> getHolder(int position) {
            return new NewsHolder();

        }

        @Override
        public ArrayList<newsInfo> onLoadMore() {

            NewsProtocol protocol = new NewsProtocol();
            moreData = protocol.getData(getListSize());
            if(moreData==null){
            }
            return moreData;
        }

    }



}
