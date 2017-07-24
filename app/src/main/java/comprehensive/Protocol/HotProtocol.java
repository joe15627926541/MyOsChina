package comprehensive.Protocol;

import com.example.asdf.myoschina.Protocol.BaseProtocol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import comprehensive.XmlParse.HotXmlParser;
import comprehensive.XmlParse.NewsXmlParser;
import comprehensive.domain.newsInfo;

/**
 * Created by ba0ch3ng on 2017/6/19.
 */

public class HotProtocol extends BaseProtocol<ArrayList<newsInfo>> {

    /*
    * 咨询的接口news_list?pageIndex=0&catalog=1&pageSize=20
    * */
    @Override
    public String getKey() {
        return "news_list";
    }


    @Override
    public String getParams() {
        return "&catalog=4&show=week&pageSize=20";
    }

    @Override
    public ArrayList<newsInfo> parseJson(String result) {
        ArrayList<newsInfo> newsInfos = null;
        InputStream is = new ByteArrayInputStream(result.getBytes());
        newsInfos = HotXmlParser.HotxmlParser(is);
        return newsInfos;
    }
}
