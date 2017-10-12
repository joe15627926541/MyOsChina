package comprehensive.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.asdf.myoschina.activity.MainActivity;
import com.example.asdf.myoschina.adapter.MyBaseAdapter;
import com.example.asdf.myoschina.db.DbHelper;
import com.example.asdf.myoschina.fragment.BaseFragment;
import com.example.asdf.myoschina.holder.BaseHolder;
import com.example.asdf.myoschina.util.UIUtils;
import com.example.asdf.myoschina.util.constansts;
import com.example.asdf.myoschina.view.LoadingPage.ResultState;
import com.example.asdf.myoschina.view.MyListView;

import java.util.ArrayList;

import comprehensive.Protocol.HotProtocol;
import comprehensive.activity.DetailPagerActivity;
import comprehensive.domain.newsInfo;
import comprehensive.holder.NewsHolder;

/**
 * Created by ba0ch3ng on 2017/6/21.
 */

public class HotFragment extends BaseFragment {
    ArrayList<newsInfo> mList = null;
    private ArrayList<newsInfo> moreData;
    private DbHelper helper;
    private SQLiteDatabase db;

    private MyListView myListView;
    private ComPreHensiveAdapter comPreHensiveAdapter;
    @Override
    public View onCreateSuccessView() {
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();
        if (myListView == null) {
            myListView = new MyListView(UIUtils.getContext());
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) myListView.getParent();
            if (parent != null) {
                parent.removeView(myListView);
            }
        }
        comPreHensiveAdapter=new ComPreHensiveAdapter(mList);
        myListView.setAdapter(comPreHensiveAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helper.add(mList.get(position).getId(),db);
                Intent intent = new Intent((MainActivity)getActivity(),DetailPagerActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("Info",mList.get(position%20));
                intent.putExtra("type", constansts.HOTTYPE);
                intent.putExtra("Isshake",false);
                startActivity(intent);
                comPreHensiveAdapter.notifyDataSetChanged();
            }
        });
        return myListView;
    }

    @Override
    public ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
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

            HotProtocol protocol = new HotProtocol();
            moreData = protocol.getData(getListSize());
            if(moreData==null){
            }
            return moreData;
        }

    }



}
