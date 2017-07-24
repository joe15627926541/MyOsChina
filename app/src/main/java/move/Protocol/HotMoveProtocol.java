package move.Protocol;

import android.widget.Toast;

import com.example.asdf.myoschina.Protocol.BaseProtocol;
import com.example.asdf.myoschina.util.UIUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import comprehensive.XmlParse.NewsXmlParser;
import comprehensive.domain.newsInfo;
import move.XmlParse.HotMoveXmlParser;
import move.XmlParse.MoveXmlParser;
import move.domain.recentmoveInfo;

/**
 * Created by ba0ch3ng on 2017/6/19.
 */

public class HotMoveProtocol extends BaseProtocol<ArrayList<recentmoveInfo>> {

    /*
    * tweet_list?uid=0&pageIndex=0&pageSize=20
    *
    * */
    @Override
    public String getKey() {
        return "tweet_list?uid=-1";
    }


    @Override
    public String getParams() {
        return "&pageSize=20";
    }

    @Override
    public ArrayList<recentmoveInfo> parseJson(String result) {
        ArrayList<recentmoveInfo> recentmoveInfos = null;
        InputStream is = new ByteArrayInputStream(result.getBytes());
        recentmoveInfos= HotMoveXmlParser.HotXmlMoveParser(is);
        return recentmoveInfos;
    }
}
